package ru.imikryakov.projects.mp3downloader.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.UrlConstructor;
import ru.imikryakov.projects.mp3downloader.api.Song;
import ru.imikryakov.projects.mp3downloader.data.SongImpl;
import ru.imikryakov.projects.mp3downloader.http.HttpClientManager;
import ru.imikryakov.projects.mp3downloader.http.HttpResponse;

public class SongFiller implements Filler<Song> {
    private static Logger logger = LoggerFactory.getLogger(SongFiller.class);

    @Override
    public Song fill(String relativeUrlPath) {
        String url = UrlConstructor.getSongPageUrl(relativeUrlPath);

        HttpResponse response = HttpClientManager.getHttpClient().getHtmlPage(url);
        if (!response.isOk()) {
            return null;
        }

        StringBuilder html = response.asHtmlPage();

        SongImpl song = new SongImpl(url, html);

        Grabber<String> titleGrabber = new RegexSingleValueGrabber(RegexLibrary.SONG_TITLE_REGEX, 1);
        String title = titleGrabber.grab(html);
        logger.debug("grabbed song title: {}", title);

        Grabber<String> artistNameGrabber = new RegexSingleValueGrabber(RegexLibrary.SONG_ARTIST_REGEX, 2);
        String artistName = artistNameGrabber.grab(html);
        logger.debug("grabbed song artist: {}", artistName);

        Grabber<String> albumNameGrabber = new RegexSingleValueGrabber(RegexLibrary.SONG_ALBUM_REGEX, 2);
        String albumName = albumNameGrabber.grab(html);
        logger.debug("grabbed song album: {}", albumName);

        Grabber<String> linkGrabber = new RegexSingleValueGrabber(RegexLibrary.SONG_DOWNLOAD_LINK_REGEX, 1);
        String link = linkGrabber.grab(html);
        logger.debug("grabbed song download link: {}", link);

        song.setTitle(title);
        song.setArtistName(artistName);
        song.setAlbumName(albumName);
        song.setDownloadLink(link);

        song.setFilled(true);
        return song;
    }
}
