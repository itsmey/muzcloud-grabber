package ru.imikryakov.projects.mp3downloader.data;

import java.util.HashMap;

public class Search extends HtmlEntity {
    private HashMap<String, String> results = new HashMap<>();

    public Search(String url, CharSequence htmlPage) {
        super(url, htmlPage);
    }

    public HashMap<String, String> getResults() {
        return results;
    }

    public void setResults(HashMap<String, String> results) {
        this.results = results;
    }
}
