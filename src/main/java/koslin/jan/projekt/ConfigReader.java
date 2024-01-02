package koslin.jan.projekt;

import koslin.jan.projekt.server.Server;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ConfigReader {

    public static int getPort() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(ConfigReader.class.getResource("config.xml").openStream());

        // Get the root element
        Element root = document.getDocumentElement();

        // Get the server element
        Element serverElement = (Element) root.getElementsByTagName("server").item(0);

        // Get the port element
        Element portElement = (Element) serverElement.getElementsByTagName("port").item(0);

        // Get the port number as a string
        String portNumber = portElement.getTextContent();

        // Convert the string to an integer
        return Integer.parseInt(portNumber);
    }

    public static String getIp() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(ConfigReader.class.getResource("config.xml").openStream());

        // Get the root element
        Element root = document.getDocumentElement();

        // Get the server element
        Element serverElement = (Element) root.getElementsByTagName("server").item(0);

        // Get the port element
        Element ipElement = (Element) serverElement.getElementsByTagName("ip").item(0);

        // Get the port number as a string
        return ipElement.getTextContent();
    }
}
