package ru.imikryakov.projects.mp3downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.http.HttpClientManager;


public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        CharSequence data = HttpClientManager.getHttpClient().get(UrlConstructor.getArtistAlbumsPageUrl("/artist/115732/sparks")).getData();

        new SAXParserImpl().parse(data);
    }
}
