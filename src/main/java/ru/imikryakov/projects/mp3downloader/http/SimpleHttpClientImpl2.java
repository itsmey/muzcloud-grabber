package ru.imikryakov.projects.mp3downloader.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imikryakov.projects.mp3downloader.Mp3DownloaderException;
import ru.imikryakov.projects.mp3downloader.helpers.Utils;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SimpleHttpClientImpl2 implements HttpClient {
    private static Logger logger = LoggerFactory.getLogger(SimpleHttpClientImpl2.class);

    private static final int HTTP_STATUS_OK = 200;
    private static final int HTTP_STATUS_MOVED = 302;

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;

    private int reconnectionsCount = 3;

    private HttpURLConnection connection;
    private String urlString;
    private Map<String, String> headers;

    @Override
    public void get(String urlString, Map<String, String> headers, Consumer<InputStream> inputStreamConsumer) {
        logger.debug("GET {}", urlString);

        if (headers != null) {
            this.headers = new HashMap<>(headers);
        }
        this.urlString = urlString;
        try {
            openConnection();
            checkStatus();
            inputStreamConsumer.accept(connection.getInputStream());
        } catch (IOException e) {
            throwMp3DownloaderException("can't get input stream of http response", e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public StringBuilder getHtmlPage(String url) {
        logger.debug("GET as html page {}", url);
        final StringBuilder contents = new StringBuilder();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html; charset=UTF-8");

        get(url, headers, inputStream -> {
            try  {
                long length = 0;
                try (Reader streamReader = new InputStreamReader(inputStream);
                     BufferedReader in = new BufferedReader(streamReader)) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        length += inputLine.length();
                        contents.append(inputLine).append("\n");
                    }
                }

                logger.debug("getHtmlPage: content length is {} symbols", length);
            } catch (Exception e) {
                throwMp3DownloaderException("IO error at reading http response stream and writing data to file", e);
            }
        });

        return contents;
    }

    @Override
    public String getFile(String url, String parentDirPath) {
        logger.debug("GET as File: download {} to {}", url, parentDirPath);

        String filename = null;
        try {
            filename = Paths.get(new URI(url).getPath()).getFileName().toString();
        } catch (URISyntaxException e) {
            throwMp3DownloaderException("incorrect URL", e);
        }
        logger.debug("getFile: file name is {}", filename);

        final File outputFile = Utils.combinePaths(parentDirPath, filename);

        get(url, null, inputStream -> {
            try {
                try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                    int read;
                    byte[] bytes = new byte[1024];
                    int n = 0;
                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                        if (n++ == 100) {
                            logger.debug("-- wrote 100 kb --");
                            n = 0;
                        }
                    }
                    logger.debug("-- wrote " + n + " kb --");
                }
            } catch (IOException e) {
                throwMp3DownloaderException("IO error at reading http response stream and writing data to file", e);
            }
        });

        return outputFile.getPath();
    }

    private void openConnection() {
        closeConnection();
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 Firefox/26.0");
            if (headers != null) {
                for (Map.Entry<String, String> e : headers.entrySet()) {
                    connection.setRequestProperty(e.getKey(), e.getValue());
                }
            }
        } catch (IOException e) {
            throwMp3DownloaderException("can't open connection", e);
        }
    }

    private void reconnect() {
        reconnectionsCount--;
        logger.debug("do reconnection. remains {} reconnections", reconnectionsCount);
        closeConnection();
        openConnection();
    }

    private int getResponse() {
        try {
            return connection.getResponseCode();
        } catch (SocketTimeoutException e) {
            logger.debug("caught read timeout exception. check if we can reconnect");
            if (reconnectionsCount == 0) {
                logger.debug("no reconnections available! let is crash");
                throwMp3DownloaderException("read timeout and no reconnections!", e);
            } else {
                reconnect();
                return getResponse();
            }
        } catch (IOException e) {
            throwMp3DownloaderException("can't get http response status", e);
        }
        //unreachable
        return -1;
    }

    private void checkStatus() {
        int httpStatusCode = getResponse();
        logger.debug("got html response code {}", httpStatusCode);
        switch (httpStatusCode) {
            case HTTP_STATUS_OK: break;
            case HTTP_STATUS_MOVED: followMovedLocation(); break;
            case -1:
            default:
                throwMp3DownloaderException("unknown http response status: " + httpStatusCode, null);
        }
    }

    private void followMovedLocation() {
        String location = connection.getHeaderField("Location");
        if (location == null || location.trim().isEmpty()) {
            throwMp3DownloaderException("incorrect Location header", null);
        }
        logger.debug("following location url {}", location);
        closeConnection();
        headers = null;
        urlString = location;
        openConnection();
    }

    private void closeConnection() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }

    private void throwMp3DownloaderException(String msg, Exception cause) throws Mp3DownloaderException {
        closeConnection();
        Mp3DownloaderException ex;
        if (cause == null) {
            ex = new Mp3DownloaderException(msg);
        } else {
            ex = new Mp3DownloaderException(msg, cause);
        }
        logger.error("Mp3 Downloader exception", ex);
        throw ex;
    }
}
