package ru.imikryakov.projects.mp3downloader.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.helpers.Utils;

import java.io.*;
import java.net.URI;
import java.nio.file.Paths;

public interface HttpResponse {
    Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    int getCode();
    InputStream getData() throws IOException;
    String getUrl();
    void closeConnection();

    default boolean isOk() {
        return getCode() < 299;
    }

    default StringBuilder asHtmlPage() {
        logger.debug("saving HTML Response data to StringBuilder: {}", getUrl());
        StringBuilder contents = new StringBuilder();
        try (InputStream is = getData()) {
            long length = 0;

            try (Reader streamReader = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(streamReader)) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    length += inputLine.length();
                    contents.append(inputLine).append("\n");
                }
            }

            logger.debug("content length is {}", length);

            return contents;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            return null;
        } finally {
            closeConnection();
        }
    }

    default void asFile(String directory) {
        logger.debug("saving HTML Response data to file: {}", getUrl());
        try (InputStream is = getData()) {
            String filename = Paths.get(new URI(getUrl()).getPath()).getFileName().toString();
            logger.debug("file name for saving file is {}", filename);

            File outputFile = Utils.combinePaths(directory, filename);
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                int read;
                byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            }

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        } finally {
            closeConnection();
        }
    }
}
