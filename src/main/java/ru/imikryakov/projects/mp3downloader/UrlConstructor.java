package ru.imikryakov.projects.mp3downloader;

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
}
