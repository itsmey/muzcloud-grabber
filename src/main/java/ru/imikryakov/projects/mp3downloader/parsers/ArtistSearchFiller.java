package ru.imikryakov.projects.mp3downloader.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.api.Search;
import ru.imikryakov.projects.mp3downloader.data.SearchImpl;
import ru.imikryakov.projects.mp3downloader.http.HttpClientManager;
import ru.imikryakov.projects.mp3downloader.http.HttpResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ArtistSearchFiller implements Filler<Search> {
    private static Logger logger = LoggerFactory.getLogger(ArtistSearchFiller.class);

    @Override
    public Search fill(String url) {
        HttpResponse response = HttpClientManager.getHttpClient().getHtmlPage(url);
        if (!response.isOk()) {
            return null;
        }

        StringBuilder html = response.asHtmlPage();

        SearchImpl search = new SearchImpl(url, html);

        Grabber<Collection<String>> artistsNamesGrabber = new RegexMultiValueGrabber(RegexLibrary.SEARCH_ARTISTS_REGEX, 2);
        Grabber<Collection<String>> artistsUrlsGrabber = new RegexMultiValueGrabber(RegexLibrary.SEARCH_ARTISTS_REGEX, 1);

        List<String> artistsNames = new ArrayList<>(artistsNamesGrabber.grab(html));
        List<String> artistsUrls = new ArrayList<>(artistsUrlsGrabber.grab(html));

        HashMap<String, String> results = new HashMap<>();

        for (int i = 0; i < artistsNames.size(); i++) {
            if (!results.containsKey(artistsUrls.get(i))) {
                logger.debug("grabbed artist found: name {}, url {}", artistsNames.get(i), artistsUrls.get(i));
            }
            results.put(artistsUrls.get(i), artistsNames.get(i));
        }

        logger.debug("found {} artists", results.size());

        search.setResults(results);

        search.setFilled(true);
        return search;
    }
}
