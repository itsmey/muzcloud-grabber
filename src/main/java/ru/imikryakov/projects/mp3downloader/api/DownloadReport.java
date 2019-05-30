package ru.imikryakov.projects.mp3downloader.api;

import java.util.Collection;

public class DownloadReport {
    public enum Status {SUCCESS, ERROR, NO_ACTION}

    private String url;
    private String fileName;
    private Status status;
    private String errorMsg;

    public DownloadReport(String url) {
        this.url = url;
        status = Status.NO_ACTION;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static StringBuilder formReportText(Collection<DownloadReport> reports) {
        StringBuilder sb = new StringBuilder();
        sb.append("Download Report\n");
        for (DownloadReport r : reports) {
            sb.append(String.format("%s %s ", r.status, r.url));
            switch (r.status) {
                case NO_ACTION: sb.append("\n"); break;
                case SUCCESS: sb.append(String.format("to %s\n", r.fileName)); break;
                case ERROR: sb.append(String.format("to %s: %s\n", r.fileName, r.errorMsg)); break;
            }
        }
        return sb;
    }
}
