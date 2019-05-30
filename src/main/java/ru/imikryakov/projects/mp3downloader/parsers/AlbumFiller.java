package ru.imikryakov.projects.mp3downloader.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.helpers.UrlConstructor;
import ru.imikryakov.projects.mp3downloader.api.Album;
import ru.imikryakov.projects.mp3downloader.data.AlbumImpl;
import ru.imikryakov.projects.mp3downloader.http.HttpClientManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AlbumFiller implements Filler<Album> {
    private static Logger logger = LoggerFactory.getLogger(AlbumFiller.class);

    @Override
    public Album fill(String relativeUrlPath) {
        String url = UrlConstructor.getAlbumPageUrl(relativeUrlPath);

        StringBuilder html = HttpClientManager.getHttpClient().getHtmlPage(url);

        AlbumImpl album = new AlbumImpl(url, html);

        Grabber<String> titleGrabber = new RegexSingleValueGrabber(RegexLibrary.ALBUM_TITLE_REGEX, 1);
        String title = titleGrabber.grab(html);
        logger.debug("grabbed album title: {}", title);

        Grabber<String> artistNameGrabber = new RegexSingleValueGrabber(RegexLibrary.ALBUM_ARTIST_REGEX, 2);
        String artistName = artistNameGrabber.grab(html);
        logger.debug("grabbed album artist: {}", artistName);

        Grabber<String> yearGrabber = new RegexSingleValueGrabber(RegexLibrary.ALBUM_YEAR_REGEX, 2);
        String yearStr = yearGrabber.grab(html);
        logger.debug("grabbed album year: {}", yearStr);

        album.setTitle(title);
        album.setArtistName(artistName);
        try {
            album.setYear(Integer.valueOf(yearStr));
        } catch (NumberFormatException e) {
            logger.warn("can't convert year: {}", yearStr);
        }

        Grabber<Collection<String>> songsGrabber = new RegexMultiValueGrabber(RegexLibrary.ALBUM_SONGS_REGEX, 1);
        Set<String> songUrls = new HashSet<>(songsGrabber.grab(html));
        logger.debug(songUrls.toString());
        logger.debug("grabbed {} song urls", songUrls.size());
        album.setSongUrls(songUrls);

        album.setFilled(true);
        return album;
    }
}
