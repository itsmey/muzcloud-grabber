package ru.imikryakov.projects.mp3downloader.parsers.ru.imikryakov.projects.mp3downloader;

import ru.imikryakov.projects.mp3downloader.helpers.UrlConstructor;

import java.util.HashMap;

public class TestData {
    private static HashMap<String, String> artistRelativePaths = new HashMap<>();
    private static HashMap<String, String> albumsRelativePaths = new HashMap<>();
    private static HashMap<String, String> songsRelativePaths = new HashMap<>();

    static {
        artistRelativePaths.put("Sparks", "/artist/115732/sparks");
        albumsRelativePaths.put("Sparks (Halfnelson)", "/album/250357/sparks-sparks-halfnelson-1971");
        songsRelativePaths.put("Wonder Girl", "/song/3574620/sparks-wonder-girl");
        /*
         * Внимание! Поскольку ссылки на скачивания песни непостоянны, они отсюда удалены. Для получения актуально ссылки на скачивание
         * песни нужно иметь ссылку на страницу песни и оттуда уже извлечь ссылку на фаил песни
         */
    }

    public static String getArtistPageRelativeUrl() {
        return artistRelativePaths.values().iterator().next();
    }

    public static String getArtistPageUrl() {
        String relativePath = artistRelativePaths.values().iterator().next();
        return UrlConstructor.getArtistPageUrl(relativePath);
    }

    public static String getAlbumPageRelativeUrl() {
        return albumsRelativePaths.values().iterator().next();
    }

    public static String getAlbumPageUrl() {
        String relativePath = albumsRelativePaths.values().iterator().next();
        return UrlConstructor.getAlbumPageUrl(relativePath);
    }

    public static String getSongPageRelativeUrl() {
        return songsRelativePaths.values().iterator().next();
    }

    public static String getSongPageUrl() {
        String relativePath = songsRelativePaths.values().iterator().next();
        return UrlConstructor.getSongPageUrl(relativePath);
    }
}
