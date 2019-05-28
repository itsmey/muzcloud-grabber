package ru.imikryakov.projects.mp3downloader.data;

import java.util.Collection;

public class Artist extends HtmlEntity {
    private String countryCode;
    private String countryName;
    private String webPage;
    private String facebook;
    private String instagram;
    private String twitter;
    private String wikipedia;
    private byte[] photo;
    private Collection<Album> albums;
    private Collection<String> albumUrls;

    public Artist(String url, CharSequence htmlPage) {
        super(url, htmlPage);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Collection<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Collection<Album> albums) {
        this.albums = albums;
    }

    public Collection<String> getAlbumUrls() {
        return albumUrls;
    }

    public void setAlbumUrls(Collection<String> albumUrls) {
        this.albumUrls = albumUrls;
    }
}
