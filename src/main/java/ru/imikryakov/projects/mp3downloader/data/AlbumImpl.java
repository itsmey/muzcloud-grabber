package ru.imikryakov.projects.mp3downloader.data;

import ru.imikryakov.projects.mp3downloader.api.Album;

import java.util.Collection;

public class AlbumImpl extends HtmlEntity implements Album {
    private String artistName;
    private int year;
    private String type;
    private int bitrate;
    private byte[] photo;
    private Collection<SongImpl> songs;
    private Collection<String> songUrls;

    public AlbumImpl(String url, CharSequence htmlPage) {
        super(url, htmlPage);
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Collection<SongImpl> getSongs() {
        return songs;
    }

    public void setSongs(Collection<SongImpl> songs) {
        this.songs = songs;
    }

    public Collection<String> getSongUrls() {
        return songUrls;
    }

    public void setSongUrls(Collection<String> songUrls) {
        this.songUrls = songUrls;
    }
}
