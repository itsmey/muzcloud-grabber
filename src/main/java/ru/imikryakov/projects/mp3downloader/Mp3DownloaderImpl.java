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

    private int songDownloadRetryCount = Integer.valueOf(Config.getConfigProperty("download.retry.count"));
    private final int WAIT_TIME_BEFORE_RETRY = Integer.valueOf(Config.getConfigProperty("download.retry.wait.time"));

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
    public Collection<DownloadReport> downloadAlbum(Album album, String parentDir, boolean canRenameFiles, boolean exitAtFirstFail) {
        String folderName = String.format("%s %s - %s", album.getArtistName(), album.getYear() == 0 ? "" : " - (" + album.getYear() + ")", album.getTitle());

        File albumDir = Utils.combinePaths(parentDir, folderName);

        if (!albumDir.exists()) {
            if (!albumDir.mkdir()) {
                logger.error("cannot create directory " + albumDir);
                throw new IllegalStateException("cannot create directory " + albumDir);
            }
        }

        logger.debug("album folder is {}", albumDir.getPath());

        Map<String, DownloadReport> reports = new HashMap<>();
        for (String songUrl : album.getSongUrls()) {
            reports.put(songUrl, new DownloadReport(songUrl));
        }

        Collection<String> fileNames = new ArrayList<>();

        SongFiller songFiller = new SongFiller();
        for (String songUrl : album.getSongUrls()) {
            Song song = songFiller.fill(songUrl);
            reports.get(songUrl).setFileName(song.getTitle());
            logger.debug("downloading song {}", song.getTitle());
            try {
                fileNames.add(downloadSong(song, albumDir.getPath()));
                reports.get(songUrl).setStatus(DownloadReport.Status.SUCCESS);
                logger.debug("downloaded");
            } catch (Mp3DownloaderException e) {
                reports.get(songUrl).setStatus(DownloadReport.Status.ERROR);
                reports.get(songUrl).setErrorMsg(e.getMessage());
                if (exitAtFirstFail) {
                    logger.debug("exiting at first song download fail");
                    return reports.values();
                }
            }

            songDownloadRetryCount = Integer.valueOf(Config.getConfigProperty("download.retry.count"));
        }

        if (canRenameFiles) {
             renameFiles(fileNames);
        }

        return reports.values();
    }

    private String downloadSong(Song song, String path) {
        try {
            return song.download(path);
        } catch (Mp3DownloaderException e) {
            logger.debug("can't download song: exception {}", e.getMessage());
            if (songDownloadRetryCount > 0) {
                songDownloadRetryCount--;
                logger.debug("retry download. {} retries remains", songDownloadRetryCount);
                if (WAIT_TIME_BEFORE_RETRY > 0) {
                    logger.debug("wait {} milliseconds before retry", WAIT_TIME_BEFORE_RETRY);
                    sleep(WAIT_TIME_BEFORE_RETRY);
                }
                return downloadSong(song, path);
            } else {
                throw new Mp3DownloaderException(e.getMessage(), e);
            }
        }
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.debug("interrupted while waiting for retry");
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
                newName = Utils.prepareFileName(newName);
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
