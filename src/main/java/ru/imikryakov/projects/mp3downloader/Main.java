package ru.imikryakov.projects.mp3downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.api.Album;
import ru.imikryakov.projects.mp3downloader.api.DownloadReport;
import ru.imikryakov.projects.mp3downloader.api.Mp3Downloader;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Mp3Downloader downloader = new Mp3DownloaderImpl();
        Album album = downloader.getAlbum("/album/3155365/the-shirelles-25-all-time-greatest-hits-60s-1999");
        logger.debug(DownloadReport.formReportText(new Mp3DownloaderImpl().downloadAlbum(album, "", true, true)).toString());
    }

}
