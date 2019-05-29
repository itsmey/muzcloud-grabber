package ru.imikryakov.projects.mp3downloader.data;

import ru.imikryakov.projects.mp3downloader.api.Search;

import java.util.HashMap;

public class SearchImpl extends HtmlEntity implements Search {
    private HashMap<String, String> results = new HashMap<>();

    public SearchImpl(String url, CharSequence htmlPage) {
        super(url, htmlPage);
    }

    public HashMap<String, String> getResults() {
        return results;
    }

    public void setResults(HashMap<String, String> results) {
        this.results = results;
    }
}
