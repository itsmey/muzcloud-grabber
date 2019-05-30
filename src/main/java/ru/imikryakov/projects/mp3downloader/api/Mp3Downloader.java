package ru.imikryakov.projects.mp3downloader.api;

import java.util.Collection;

public interface Mp3Downloader {
    Search searchArtist(String query);
    Artist getArtist(String relativeUrl);
    Album getAlbum(String relativeUrl);
    Song getSong(String relativeUrl);
    Collection<DownloadReport> downloadAlbum(Album album, String parentDir, boolean canRenameFiles, boolean exitAtFirstFail);
}
