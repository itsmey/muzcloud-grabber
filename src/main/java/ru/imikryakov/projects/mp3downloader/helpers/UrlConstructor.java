package ru.imikryakov.projects.mp3downloader.helpers;

import ru.imikryakov.projects.mp3downloader.Config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlConstructor {
    private static final String SITE_URL = Config.getConfigProperty("site.url");

    public static String getArtistAlbumsPageUrl(String artistRelativePath) {
        return SITE_URL + artistRelativePath + "/albums";
    }

    public static String getArtistPageUrl(String artistRelativePath) {
        return SITE_URL + artistRelativePath;
    }

    public static String getAlbumPageUrl(String albumRelativePath) {
        return SITE_URL + albumRelativePath;
    }

    public static String getSongPageUrl(String songRelativePath) {
        return SITE_URL + songRelativePath;
    }

    public static String getSongDownloadUrl(String linkRelativePath) {
        return SITE_URL + linkRelativePath;
    }

    public static String getSearchUrl(String searchText) {
        try {
            return SITE_URL + "/search?searchText=" + URLEncoder.encode(searchText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("unsupported encoding", e);
        }
    }
}
