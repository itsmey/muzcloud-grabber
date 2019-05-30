package ru.imikryakov.projects.mp3downloader.parsers;

import org.junit.Test;
import ru.imikryakov.projects.mp3downloader.Mp3DownloaderImpl;
import ru.imikryakov.projects.mp3downloader.SearchEngine;
import ru.imikryakov.projects.mp3downloader.api.Album;
import ru.imikryakov.projects.mp3downloader.api.Artist;
import ru.imikryakov.projects.mp3downloader.api.Search;
import ru.imikryakov.projects.mp3downloader.api.Song;
import ru.imikryakov.projects.mp3downloader.http.HttpResponse;
import ru.imikryakov.projects.mp3downloader.http.SimpleHttpClientImpl;
import ru.imikryakov.projects.mp3downloader.parsers.ru.imikryakov.projects.mp3downloader.TestData;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Mp3DownloaderTest {

    @Test
    public void testSiteAccessibility() {
        HttpResponse response = new SimpleHttpClientImpl().getHtmlPage(TestData.getArtistPageUrl());
        assertNotNull(response);
    }

    @Test
    public void testArtistFiller() {
        ArtistFiller filler = new ArtistFiller();
        Artist artist = filler.fill(TestData.getArtistPageRelativeUrl());
        assertNotNull(artist);
//        assertTrue(artist.isFilled());
    }

    @Test
    public void testAlbumFiller() {
        AlbumFiller filler = new AlbumFiller();
        Album album = filler.fill(TestData.getAlbumPageRelativeUrl());
        assertNotNull(album);
//        assertTrue(album.isFilled());
    }

    @Test
    public void testSongFiller() {
        SongFiller filler = new SongFiller();
        Song song = filler.fill(TestData.getSongPageRelativeUrl());
        assertNotNull(song);
//        assertTrue(song.isFilled());
    }

    @Test
    public void testDownloadSong() {
        SongFiller filler = new SongFiller();
        Song song = filler.fill(TestData.getSongPageRelativeUrl());
        assertNotNull(song);
//        assertTrue(song.isFilled());
        assertNotNull(song.getDownloadLink());
        new SimpleHttpClientImpl().getHtmlPage(song.getDownloadLink()).asFile("");
    }

    @Test
    public void testArtistSearch() {
        Search search = SearchEngine.searchArtist("john denver");
        assertNotNull(search);
    }

    @Test
    public void testDownloadAlbum() {
        Album album = new AlbumFiller().fill(TestData.getAlbumPageRelativeUrl());
        assertNotNull(album);
        new Mp3DownloaderImpl().downloadAlbum(album, "", true);
    }
}
