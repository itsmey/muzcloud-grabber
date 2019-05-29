package ru.imikryakov.projects.mp3downloader.api;

import java.util.Collection;

public interface Artist {
    String getTitle();
    String getCountryName();
    String getCountryCode();
    Collection<String> getAlbumUrls();
}
