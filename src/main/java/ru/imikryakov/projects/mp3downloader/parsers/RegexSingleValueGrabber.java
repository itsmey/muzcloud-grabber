package ru.imikryakov.projects.mp3downloader.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSingleValueGrabber implements Grabber<String> {
    private String regex;
    private int groupNo;

    public RegexSingleValueGrabber(String regex, int groupNo) {
        this.regex = regex;
        this.groupNo = groupNo;
    }

    @Override
    public String grab(CharSequence input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        if (m.find()) {
            return m.group(groupNo);
        }

        return null;
    }
}
