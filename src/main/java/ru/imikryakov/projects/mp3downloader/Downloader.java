package ru.imikryakov.projects.mp3downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.data.Album;
import ru.imikryakov.projects.mp3downloader.data.Song;
import ru.imikryakov.projects.mp3downloader.parsers.SongFiller;

import java.io.File;

public class Downloader {
    private static Logger logger = LoggerFactory.getLogger(Downloader.class);

    public static void downloadAlbum(Album album, String parentDir) {
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
