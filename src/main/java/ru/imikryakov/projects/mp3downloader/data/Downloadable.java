package ru.imikryakov.projects.mp3downloader.data;

import ru.imikryakov.projects.mp3downloader.http.HttpClientManager;

public interface Downloadable {
    String getDownloadLink();

    default void download(String directory) {
        HttpClientManager.getHttpClient().get(getDownloadLink()).asFile(directory);
    }
}
