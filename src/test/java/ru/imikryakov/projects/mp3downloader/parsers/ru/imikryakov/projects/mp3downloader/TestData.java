package ru.imikryakov.projects.mp3downloader.parsers.ru.imikryakov.projects.mp3downloader;

import ru.imikryakov.projects.mp3downloader.UrlConstructor;

import java.util.HashMap;

public class TestData {
    private static HashMap<String, String> artistRelativePaths = new HashMap<>();
    private static HashMap<String, String> albumsRelativePaths = new HashMap<>();
    private static HashMap<String, String> songsRelativePaths = new HashMap<>();
    private static HashMap<String, String> songsDownloadRelativeLinks = new HashMap<>();

    static {
        artistRelativePaths.put("Sparks", "/artist/115732/sparks");
        albumsRelativePaths.put("Sparks (Halfnelson)", "/album/250357/sparks-sparks-halfnelson-1971");
        songsRelativePaths.put("Wonder Girl", "/song/3574620/sparks-wonder-girl");
        songsDownloadRelativeLinks.put("Wonder Girl", "/song/dl/636947189271877677/488760153c30276c9f085d0258ccb1f8/3574620");
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

    public static String getSongDownloadRelativeLink() {
        return songsDownloadRelativeLinks.values().iterator().next();
    }

    public static String getSongDownloadLink() {
        String relativePath = songsDownloadRelativeLinks.values().iterator().next();
        return UrlConstructor.getSongDownloadUrl(relativePath);
    }
}
