package ru.imikryakov.projects.mp3downloader.http;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Consumer;

public interface HttpClient {
    StringBuilder getHtmlPage(String url);

    String getFile(String url, String parentDirPath);

    void get(String urlString, Map<String, String> headers, Consumer<InputStream> inputStreamConsumer);

//    default HttpResponse getHtmlPage(String url) {
//        return get(url, "text/html; charset=UTF-8");
//    }

//    default HttpResponse get(String url) {
//        return get(url, null);
//    }
}
