package ru.imikryakov.projects.mp3downloader.http;

public interface HttpResponse {
    int getCode();
    StringBuilder getData();

    default boolean isOk() {
        return getCode() < 299;
    }
}
