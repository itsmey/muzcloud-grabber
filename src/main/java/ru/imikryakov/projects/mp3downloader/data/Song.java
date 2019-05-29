package ru.imikryakov.projects.mp3downloader.data;

import ru.imikryakov.projects.mp3downloader.UrlConstructor;

public class Song extends HtmlEntity implements Downloadable {
    private String artistName;
    private String albumName;
    private String length;
    private String size;
    private int bitrate;
    private String downloadLink;

    public Song(String url, CharSequence htmlPage) {
        super(url, htmlPage);
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @Override
    public String getDownloadLink() {
        return UrlConstructor.getSongDownloadUrl(downloadLink);
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }
}
