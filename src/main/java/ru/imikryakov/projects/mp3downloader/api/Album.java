package ru.imikryakov.projects.mp3downloader.api;

import java.util.Collection;

public interface Album {
    String getArtistName();
    int getYear();
    Collection<String> getSongUrls();
    String getTitle();
}
