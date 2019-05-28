package ru.imikryakov.projects.mp3downloader.parsers;

public interface Filler<T> {
    T fill(String url);
}
