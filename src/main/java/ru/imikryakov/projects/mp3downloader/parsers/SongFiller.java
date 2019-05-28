package ru.imikryakov.projects.mp3downloader.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.UrlConstructor;
import ru.imikryakov.projects.mp3downloader.data.Song;
import ru.imikryakov.projects.mp3downloader.http.HttpClientManager;
import ru.imikryakov.projects.mp3downloader.http.HttpResponse;

public class SongFiller implements Filler<Song> {
    private static Logger logger = LoggerFactory.getLogger(SongFiller.class);

    @Override
    public Song fill(String relativeUrlPath) {
        String url = UrlConstructor.getSongPageUrl(relativeUrlPath);

        HttpResponse response = HttpClientManager.getHttpClient().get(url);
        if (!response.isOk()) {
            return null;
        }

        Song song = new Song(url, response.getData());

        Grabber<String> titleGrabber = new RegexSingleValueGrabber(RegexLibrary.SONG_TITLE_REGEX, 1);
        String title = titleGrabber.grab(response.getData());
        logger.debug("grabbed song title: {}", title);

        Grabber<String> artistNameGrabber = new RegexSingleValueGrabber(RegexLibrary.SONG_ARTIST_REGEX, 2);
        String artistName = artistNameGrabber.grab(response.getData());
        logger.debug("grabbed song artist: {}", artistName);

        Grabber<String> albumNameGrabber = new RegexSingleValueGrabber(RegexLibrary.SONG_ALBUM_REGEX, 2);
        String albumName = albumNameGrabber.grab(response.getData());
        logger.debug("grabbed song album: {}", albumName);

        Grabber<String> linkGrabber = new RegexSingleValueGrabber(RegexLibrary.SONG_DOWNLOAD_LINK_REGEX, 1);
        String link = linkGrabber.grab(response.getData());
        logger.debug("grabbed song download link: {}", link);

        song.setTitle(title);
        song.setArtistName(artistName);
        song.setAlbumName(albumName);
        song.setDownloadLink(link);

        song.setFilled(true);
        return song;
    }
}