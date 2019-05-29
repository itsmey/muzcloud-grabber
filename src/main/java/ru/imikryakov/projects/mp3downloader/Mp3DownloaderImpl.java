package ru.imikryakov.projects.mp3downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.api.*;
import ru.imikryakov.projects.mp3downloader.parsers.*;

import java.io.File;

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
    public void downloadAlbum(Album album, String parentDir) {
        String folderName = String.format("%s %s - %s", album.getArtistName(), album.getYear() == 0 ? "" : " - (" + album.getYear() + ")", album.getTitle());

        File albumDir;
        if (parentDir == null || parentDir.trim().isEmpty()) {
            albumDir = new File(folderName);
        } else {
            albumDir = new File(parentDir, folderName);
        }

        if (!albumDir.exists()) {
            if (!albumDir.mkdir()) {
                logger.error("cannot create directory " + albumDir);
                throw new IllegalStateException("cannot create directory " + albumDir);
            }
        }

        logger.debug("album folder is {}", albumDir.getPath());

        SongFiller songFiller = new SongFiller();
        for (String songUrl : album.getSongUrls()) {
            Song song = songFiller.fill(songUrl);
            logger.debug("downloading song {}", song.getTitle());
            song.download(albumDir.getPath());
            logger.debug("downloaded");
        }
    }
}
