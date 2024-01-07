package koslin.jan.projekt;

import koslin.jan.projekt.server.Server;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for reading server configurations and rules from config file
 */
public class ConfigReader {

    /**
     * Gets port number from config file
     * @return port number
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static int getPort() throws ParserConfigurationException, IOException, SAXException {
        String str = null;
        try {
            str = getConfigValue("port");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Failed to load data from config.xml");
            System.exit(1);
        }
        return Integer.parseInt(str);
    }

    /**
     * Gets ip number from config file
     * @return ip
     */
    public static String getIp() {
        String str = null;
        try {
            str = getConfigValue("ip");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Failed to load data from config.xml");
            System.exit(1);
        }
        return str;
    }

    /**
     * Gets amount of players needed to start a game from config file
     * @return amount of players
     */
    public static  int getNumberOfPlayers() {
        String str = null;
        try {
            str = getConfigValue("number_of_players");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Failed to load data from config.xml");
            System.exit(1);
        }
        return Integer.parseInt(str);
    }


    /**
     * Gets rules for different rounds in game from config file
     * @return Map of rules where keys are round numbers
     */
    public static Map<Integer, Rule> getRules() {
        Map<Integer, Rule> rulesForRounds = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document document = null;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(ConfigReader.class.getResource("config.xml").openStream());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Failed to load data from config.xml");
            System.exit(1);
        }

        Element rulesElement = (Element) document.getElementsByTagName("rules").item(0);
        NodeList ruleNodes = rulesElement.getElementsByTagName("rule");

        for (int i = 0; i < ruleNodes.getLength(); i++) {
            Node ruleNode = ruleNodes.item(i);
            if (ruleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element ruleElement = (Element) ruleNode;

                int roundNumber = Integer.parseInt(ruleElement.getAttribute("roundNumber"));
                int points = Integer.parseInt(ruleElement.getAttribute("points"));

                String color = ruleElement.hasAttribute("color") ? ruleElement.getAttribute("color") : "";

                List<String> type = new ArrayList<>();
                NodeList typeNodes = ruleElement.getElementsByTagName("item");
                for (int j = 0; j < typeNodes.getLength(); j++) {
                    Node typeNode = typeNodes.item(j);
                    if (typeNode.getNodeType() == Node.ELEMENT_NODE) {
                        type.add(typeNode.getTextContent());
                    }
                }

                boolean regardsEveryCard = ruleElement.hasAttribute("regardsEveryCard") && Boolean.parseBoolean(ruleElement.getAttribute("regardsEveryCard"));

                List<Integer> lewas = new ArrayList<>();
                NodeList lewasNodes = ruleElement.getElementsByTagName("lewas");
                if (lewasNodes.getLength() > 0) {
                    NodeList lewaItems = ((Element) lewasNodes.item(0)).getElementsByTagName("item");
                    for (int k = 0; k < lewaItems.getLength(); k++) {
                        Node lewaNode = lewaItems.item(k);
                        if (lewaNode.getNodeType() == Node.ELEMENT_NODE) {
                            lewas.add(Integer.parseInt(lewaNode.getTextContent()));
                        }
                    }
                }

                Rule rule = new Rule(points, color, type);
                rule.setRegardsEveryCard(regardsEveryCard);
                rule.setLewas(lewas);

                rulesForRounds.put(roundNumber, rule);
            }
        }

        return rulesForRounds;
    }

    /**
     * Helper function to get specific properties from config file
     * @param elementName name of property we are looking for
     * @return String value of specific property
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private static String getConfigValue(String elementName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(ConfigReader.class.getResource("config.xml").openStream());

        // Get the root element
        Element root = document.getDocumentElement();

        // Get the server element
        Element serverElement = (Element) root.getElementsByTagName("server").item(0);

        // Get the specified element
        Element configElement = (Element) serverElement.getElementsByTagName(elementName).item(0);

        // Get the element content as a string
        return configElement.getTextContent();
    }

}
