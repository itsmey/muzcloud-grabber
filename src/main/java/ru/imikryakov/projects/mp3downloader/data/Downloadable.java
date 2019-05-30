package ru.imikryakov.projects.mp3downloader.data;

import ru.imikryakov.projects.mp3downloader.http.HttpClientManager;

public interface Downloadable {
    String getDownloadLink();

    default String download(String directory) {
        return HttpClientManager.getHttpClient().getFile(getDownloadLink(), directory);
    }
}
