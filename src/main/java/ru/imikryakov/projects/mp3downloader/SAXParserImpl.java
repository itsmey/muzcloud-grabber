package ru.imikryakov.projects.mp3downloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;

public class SAXParserImpl extends DefaultHandler {
    private static Logger logger = LoggerFactory.getLogger(SAXParserImpl.class);

    public void parse(CharSequence data) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SAXParserImpl saxp = new SAXParserImpl();

            InputSource is = new InputSource(new StringReader(data.toString()));

            parser.parse(is, saxp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        logger.debug("start document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        logger.debug("start element");
        logger.debug("uri {} localName {} qName {} attributes {}", uri, localName, qName, attributes);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        logger.debug("characters {}", new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        logger.debug("end element");
        logger.debug("uri {} localName {} qName {}", uri, localName, qName);
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        logger.debug("end document");
    }
}
