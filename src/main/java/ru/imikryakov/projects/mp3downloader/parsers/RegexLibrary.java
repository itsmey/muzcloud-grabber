package ru.imikryakov.projects.mp3downloader.parsers;

public interface RegexLibrary {
    String ARTIST_TITLE_REGEX = "<header class=\\\"content__title\\\">\n<h1>(.*)<\\/h1>";
    String ARTIST_COUNTRY_CODE_REGEX = "<li><i class=\"zmdi zmdi-globe zmdi-hc-fw\" title=\"Страна\"><\\/i> <img class=\"flag-icon (.*) shadow\" \\/> (.*)<\\/li>";
    String ARTIST_COUNTRY_NAME_REGEX = ARTIST_COUNTRY_CODE_REGEX;
    String ARTIST_ALBUMS_REGEX = "<a href=\"(\\/album\\/.*)\">";

    String ALBUM_TITLE_REGEX = ARTIST_TITLE_REGEX;
    String ALBUM_ARTIST_REGEX = "<meta content=\"(.*)\" itemprop=\"name\" \\/>\n(.*)\n<\\/a>";
    String ALBUM_YEAR_REGEX = "<a href=\"\\/albums\\/([0-9]{4})\">([0-9]{4})<\\/a> <\\/li>";
    String ALBUM_SONGS_REGEX = "<a Class=\"strong\" href=\"(\\/song\\/.*?)\"";

    String SONG_TITLE_REGEX = ARTIST_TITLE_REGEX;
    String SONG_ARTIST_REGEX = "<meta content=\"\\/artist\\/.*\" itemprop=\"url\" \\/>\n<meta content=\"(.*)\" itemprop=\"name\" \\/>\\n(.*)";
    String SONG_ALBUM_REGEX = "<meta content=\"\\/album\\/.*\" itemprop=\"url\" \\/>\n<meta content=\"(.*)\" itemprop=\"name\" \\/>\n(.*)\n<\\/a>";
    String SONG_DOWNLOAD_LINK_REGEX = "<a itemprop=\"audio\" download=\".*\" href=\"(.*?)\"";
}
