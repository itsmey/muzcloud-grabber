package ru.imikryakov.projects.mp3downloader.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleHttpClientImpl implements HttpClient {
    private static Logger logger = LoggerFactory.getLogger(SimpleHttpClientImpl.class);

    public HttpResponse get(String urlString) {
        logger.debug("");

        StringBuilder contents = new StringBuilder();
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestProperty("User-Agent", "Mozilla/5.0 Firefox/26.0");
            con.setRequestProperty("Content-Type", "text/html; charset=UTF-8");

            int status = con.getResponseCode();

            logger.debug("HTTP Response Status is {}", status);

            long length = 0;

            try (Reader streamReader = new InputStreamReader(status > 299 ? con.getErrorStream() : con.getInputStream());
                 BufferedReader in = new BufferedReader(streamReader)) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    length += inputLine.length();
                    contents.append(inputLine).append("\n");
                }
            }

            logger.debug("content length is {}", length);

            return new HttpResponse() {
                @Override
                public int getCode() {
                    return status;
                }

                @Override
                public StringBuilder getData() {
                    return contents;
                }
            };
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            return null;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }
}
