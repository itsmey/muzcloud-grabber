package ru.imikryakov.projects.mp3downloader.http;

public interface HttpClient {
    HttpResponse get(String url, String contentType);

    default HttpResponse getHtmlPage(String url) {
        return get(url, "text/html; charset=UTF-8");
    }

    default HttpResponse get(String url) {
        return get(url, null);
    }
}
