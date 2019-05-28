package ru.imikryakov.projects.mp3downloader.http;

public interface HttpClient {
    HttpResponse get(String url);
}
