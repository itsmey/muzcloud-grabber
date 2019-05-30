package ru.imikryakov.projects.mp3downloader;

public class Mp3DownloaderException extends RuntimeException {
    public Mp3DownloaderException(String message) {
        super(message);
    }

    public Mp3DownloaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
