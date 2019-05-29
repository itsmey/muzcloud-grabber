package ru.imikryakov.projects.mp3downloader.api;

import ru.imikryakov.projects.mp3downloader.data.Downloadable;

public interface Song extends Downloadable {
    String getArtistName();
    String getAlbumName();
    String getTitle();
}
