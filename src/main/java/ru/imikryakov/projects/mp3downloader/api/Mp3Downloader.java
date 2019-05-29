package ru.imikryakov.projects.mp3downloader.api;

public interface Mp3Downloader {
    Search searchArtist(String query);
    Artist getArtist(String relativeUrl);
    Album getAlbum(String relativeUrl);
    Song getSong(String relativeUrl);
    void downloadAlbum(Album album, String parentDir);
}
