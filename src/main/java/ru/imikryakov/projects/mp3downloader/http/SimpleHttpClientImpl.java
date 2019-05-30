package ru.imikryakov.projects.mp3downloader.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleHttpClientImpl /*implements HttpClient*/ {
//    private static Logger logger = LoggerFactory.getLogger(SimpleHttpClientImpl.class);
//
//    public HttpResponse get(String urlString, String contentType) {
//        logger.debug("url string {}", urlString);
//        HttpURLConnection con;
//        try {
//            URL url = new URL(urlString);
//            con = (HttpURLConnection) url.openConnection();
//
//            con.setRequestMethod("GET");
//            con.setConnectTimeout(5000);
//            con.setReadTimeout(5000);
//            con.setRequestProperty("User-Agent", "Mozilla/5.0 Firefox/26.0");
//            if (contentType != null) {
//                con.setRequestProperty("Content-Type", contentType);
//            }
//
//            int status = con.getResponseCode();
//
//            logger.debug("HTTP Response Status is {}", status);
//
//            if (status == 302) {
//                String location = con.getHeaderField("Location");
////                if (con.getInputStream() != null) {
////                    con.getInputStream().close();
////                }
////                if (con.getErrorStream() != null) {
////                    con.getErrorStream().close();
////                }
//                con.disconnect();
//                return get(location);
//            }
//
//            final HttpURLConnection finalCon = con;
//            HttpURLConnection finalCon1 = con;
//            return new HttpResponse() {
//                @Override
//                public int getCode() {
//                    return status;
//                }
//
//                @Override
//                public InputStream getData() throws IOException {
//                    return status > 299 ? finalCon.getErrorStream() : finalCon.getInputStream();
//                }
//
//                @Override
//                public String getUrl() {
//                    return urlString;
//                }
//
//                @Override
//                public void closeConnection() {
//                    finalCon1.disconnect();
//                }
//            };
//        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage(), e);
//            return null;
//        }
//    }
}
