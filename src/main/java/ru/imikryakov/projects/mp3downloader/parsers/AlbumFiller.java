package ru.imikryakov.projects.mp3downloader.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.UrlConstructor;
import ru.imikryakov.projects.mp3downloader.data.Album;
import ru.imikryakov.projects.mp3downloader.http.HttpClientManager;
import ru.imikryakov.projects.mp3downloader.http.HttpResponse;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AlbumFiller implements Filler<Album> {
    private static Logger logger = LoggerFactory.getLogger(AlbumFiller.class);

    @Override
    public Album fill(String relativeUrlPath) {
        String url = UrlConstructor.getAlbumPageUrl(relativeUrlPath);

        HttpResponse response = HttpClientManager.getHttpClient().get(url);
        if (!response.isOk()) {
            return null;
        }

        Album album = new Album(url, response.getData());

        Grabber<String> titleGrabber = new RegexSingleValueGrabber(RegexLibrary.ALBUM_TITLE_REGEX, 1);
        String title = titleGrabber.grab(response.getData());
        logger.debug("grabbed album title: {}", title);

        Grabber<String> artistNameGrabber = new RegexSingleValueGrabber(RegexLibrary.ALBUM_ARTIST_REGEX, 2);
        String artistName = artistNameGrabber.grab(response.getData());
        logger.debug("grabbed album artist: {}", artistName);

        Grabber<String> yearGrabber = new RegexSingleValueGrabber(RegexLibrary.ALBUM_YEAR_REGEX, 2);
        String yearStr = yearGrabber.grab(response.getData());
        logger.debug("grabbed album year: {}", yearStr);

        album.setTitle(title);
        album.setArtistName(artistName);
        try {
            album.setYear(Integer.valueOf(yearStr));
        } catch (NumberFormatException e) {
            logger.warn("can't convert year: {}", yearStr);
        }

        Grabber<Collection<String>> songsGrabber = new RegexMultiValueGrabber(RegexLibrary.ALBUM_SONGS_REGEX, 1);
        Set<String> songUrls = new HashSet<>(songsGrabber .grab(response.getData()));
        logger.debug(songUrls.toString());
        logger.debug("grabbed {} song urls", songUrls.size());
        album.setSongUrls(songUrls);

        album.setFilled(true);
        return album;
    }
}