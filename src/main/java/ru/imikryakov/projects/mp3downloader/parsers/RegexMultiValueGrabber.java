package ru.imikryakov.projects.mp3downloader.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMultiValueGrabber implements Grabber<Collection<String>> {
    private String regex;
    private int groupNo;

    public RegexMultiValueGrabber(String regex, int groupNo) {
        this.regex = regex;
        this.groupNo = groupNo;
    }

    @Override
    public Collection<String> grab(CharSequence input) {
        Collection<String> result = new ArrayList<>();

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        while (m.find()) {
            result.add(m.group(groupNo));
        }

        return result;
    }
}
