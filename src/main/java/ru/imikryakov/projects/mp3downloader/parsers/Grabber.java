package ru.imikryakov.projects.mp3downloader.parsers;

import java.util.Collection;

public interface Grabber<T> {
    T grab(CharSequence input);
}
