package ru.imikryakov.projects.mp3downloader.http;

public class HttpClientManager {
    public static HttpClient getHttpClient() {
        return new SimpleHttpClientImpl();
    }
}
