package ru.imikryakov.projects.mp3downloader.http;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Consumer;

public interface HttpClient {
    StringBuilder getHtmlPage(String url);

    String getFile(String url, String parentDirPath);

    void get(String urlString, Map<String, String> headers, Consumer<InputStream> inputStreamConsumer);
}
