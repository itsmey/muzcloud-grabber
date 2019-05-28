package ru.imikryakov.projects.mp3downloader.data;

public abstract class HtmlEntity {
    protected String url;
    protected CharSequence htmlPage;
    protected String title;
    protected boolean isFilled;

    protected HtmlEntity(String url, CharSequence htmlPage) {
        this.url = url;
        this.htmlPage = htmlPage;
    }

    public String getUrl() {
        return url;
    }

    public CharSequence getHtmlPage() {
        return htmlPage;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHtmlPage(CharSequence htmlPage) {
        this.htmlPage = htmlPage;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
