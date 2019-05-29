package ru.imikryakov.projects.mp3downloader;

import ru.imikryakov.projects.mp3downloader.data.Search;
import ru.imikryakov.projects.mp3downloader.parsers.ArtistSearchFiller;

public class SearchEngine {
    public static Search searchArtist(String searchText) {
        return new ArtistSearchFiller().fill(UrlConstructor.getSearchUrl(searchText));
    }
}