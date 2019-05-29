package ru.imikryakov.projects.mp3downloader.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.UrlConstructor;
import ru.imikryakov.projects.mp3downloader.api.Artist;
import ru.imikryakov.projects.mp3downloader.data.ArtistImpl;
import ru.imikryakov.projects.mp3downloader.http.HttpClientManager;
import ru.imikryakov.projects.mp3downloader.http.HttpResponse;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ArtistFiller implements Filler<Artist> {
    private static Logger logger = LoggerFactory.getLogger(ArtistFiller.class);

    @Override
    public Artist fill(String relativeUrlPath) {
        String url = UrlConstructor.getAlbumPageUrl(relativeUrlPath);

        HttpResponse response = HttpClientManager.getHttpClient().getHtmlPage(url);
        if (!response.isOk()) {
            return null;
        }

        StringBuilder html = response.asHtmlPage();

        ArtistImpl artist = new ArtistImpl(url, html);

        Grabber<String> titleGrabber = new RegexSingleValueGrabber(RegexLibrary.ARTIST_TITLE_REGEX, 1);
        String title = titleGrabber.grab(html);
        logger.debug("grabbed artist title: {}", title);

        Grabber<String> contryCodeGrabber = new RegexSingleValueGrabber(RegexLibrary.ARTIST_COUNTRY_CODE_REGEX, 1);
        String countryCode = contryCodeGrabber.grab(html);
        logger.debug("grabbed artist country code: {}", countryCode);

        Grabber<String> countryNameGrabber = new RegexSingleValueGrabber(RegexLibrary.ARTIST_COUNTRY_NAME_REGEX, 2);
        String countryName = countryNameGrabber.grab(html);
        logger.debug("grabbed artist country name: {}", countryName);

        artist.setTitle(title);
        artist.setCountryCode(countryCode);
        artist.setCountryName(countryName);

        String albumsUrl = UrlConstructor.getArtistAlbumsPageUrl(relativeUrlPath);
        response = HttpClientManager.getHttpClient().getHtmlPage(albumsUrl);
        html = response.asHtmlPage();
        if (response.isOk()) {
            Grabber<Collection<String>> albumsGrabber = new RegexMultiValueGrabber(RegexLibrary.ARTIST_ALBUMS_REGEX, 1);
            Set<String> albumUrls = new HashSet<>(albumsGrabber.grab(html));
            logger.debug(albumUrls.toString());
            logger.debug("grabbed {} album urls", albumUrls.size());
            artist.setAlbumUrls(albumUrls);
        }

        artist.setFilled(true);
        return artist;
    }
}
