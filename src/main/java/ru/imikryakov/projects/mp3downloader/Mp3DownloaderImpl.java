package ru.imikryakov.projects.mp3downloader;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.api.*;
import ru.imikryakov.projects.mp3downloader.helpers.Utils;
import ru.imikryakov.projects.mp3downloader.parsers.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Mp3DownloaderImpl implements Mp3Downloader {
    private static Logger logger = LoggerFactory.getLogger(Mp3DownloaderImpl.class);

    private Filler<Album> albumFiller = new AlbumFiller();
    private Filler<Artist> artistFiller = new ArtistFiller();
    private Filler<Song> songFiller = new SongFiller();
    private Filler<Search> artistSearchFiller = new ArtistSearchFiller();

    @Override
    public Search searchArtist(String query) {
        return artistSearchFiller.fill(query);
    }

    @Override
    public Artist getArtist(String relativeUrl) {
        return artistFiller.fill(relativeUrl);
    }

    @Override
    public Album getAlbum(String relativeUrl) {
        return albumFiller.fill(relativeUrl);
    }

    @Override
    public Song getSong(String relativeUrl) {
        return songFiller.fill(relativeUrl);
    }

    @Override
    public void downloadAlbum(Album album, String parentDir, boolean canRenameFiles) {
        String folderName = String.format("%s %s - %s", album.getArtistName(), album.getYear() == 0 ? "" : " - (" + album.getYear() + ")", album.getTitle());

        File albumDir = Utils.combinePaths(parentDir, folderName);

        if (!albumDir.exists()) {
            if (!albumDir.mkdir()) {
                logger.error("cannot create directory " + albumDir);
                throw new IllegalStateException("cannot create directory " + albumDir);
            }
        }

        logger.debug("album folder is {}", albumDir.getPath());

        Collection<String> fileNames = new ArrayList<>();

        SongFiller songFiller = new SongFiller();
        for (String songUrl : album.getSongUrls()) {
            Song song = songFiller.fill(songUrl);
            logger.debug("downloading song {}", song.getTitle());
            fileNames.add(song.download(albumDir.getPath()));
            logger.debug("downloaded");
        }

        if (canRenameFiles) {
             renameFiles(fileNames);
        }
    }

    private void renameFiles(Collection<String> fileNames) {
        logger.debug("rename files {}", fileNames);

        Map<String, String> tracks = new HashMap<>();
        Map<String, String> titles = new HashMap<>();

        try {
            for (String fileName : fileNames) {
                logger.debug("processing file {}", fileName);

                Mp3File mp3 = new Mp3File(fileName);
                String track = extractTrack(mp3);
                logger.debug("extracted track: {}", track);
                String title = extractTitle(mp3);
                logger.debug("extracted title: {}", title);

                if (track == null || title == null) {
                        logger.debug("track or title is null. cancel postprocessing");
                        return;
                    }
                    tracks.put(fileName, track);
                    titles.put(fileName, title);
            }

            //successfully extracted all info needed to rename all mp3 files
            for (String fileName : fileNames) {
                Path path = Paths.get(fileName);
                String newName = String.format("%02d-%s.mp3", Integer.valueOf(tracks.get(fileName)), titles.get(fileName));
                logger.debug("renaming {} to {}", path.getFileName().toString(), newName);
                Files.move(path, path.resolveSibling(newName));
            }

        } catch (Exception e) {
            logger.error("exception occurred. postprocessing cancelled.", e);
        }
    }

    private String extractTrack(Mp3File mp3) {
        String track;
        if (mp3.hasId3v2Tag()) {
            ID3v2 tag = mp3.getId3v2Tag();
            track = tag.getTrack();
            if (track != null && !track.trim().isEmpty()) {
                try {
                    Integer.valueOf(track);
                    return track;
                } catch (NumberFormatException e) {
                    logger.debug("idv2 incorrect track number {}", track);
                }
            }
        }
        if (mp3.hasId3v1Tag()) {
            ID3v1 tag = mp3.getId3v1Tag();
            track = tag.getTrack();
            if (track != null && !track.trim().isEmpty()) {
                try {
                    Integer.valueOf(track);
                    return track;
                } catch (NumberFormatException e) {
                    logger.debug("idv1 incorrect track number {}", track);
                }
            }
        }
        return null;
    }

    private String extractTitle(Mp3File mp3) {
        String title;
        if (mp3.hasId3v2Tag()) {
            ID3v2 tag = mp3.getId3v2Tag();
            title = tag.getTitle();
            if (title != null && !title.trim().isEmpty()) {
                return title;
            }
        }
        if (mp3.hasId3v1Tag()) {
            ID3v1 tag = mp3.getId3v1Tag();
            title = tag.getTitle();
            if (title != null && !title.trim().isEmpty()) {
                return title;
            }
        }
        return null;
    }
}
