import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Server {
    // classe che contiene le informazioni del server--> caricate dal file xml
    private final String rootName = "client";

    // costante nome file
    private final String XML_PATH = rootName + "\\configServer.xml";
    
    // nomi elementi del file XML con i dati del server
    private final String IP_ATTRIBUTE = "IP";
    private final String PORT_ATTRIBUTE = "port";

    // informazioni server
    String IP;
    Integer port;
    /**
     * costruttore parametrico
     * @param iP
     * @param port
     */
    public Server(String iP, Integer port) {
        IP = iP;
        this.port = port;
    }

    /**
     * costruttore non parametrico
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public Server() throws ParserConfigurationException, SAXException, IOException {
        this.initServerInfo(); // richiamo la funzione per popolare gli attributi
    }

    /**
     * funzione che popola le informazioni del server prendendo i dati dal file xml
     */
    public void initServerInfo() throws ParserConfigurationException, SAXException, IOException {
        // leggo dal file xml le informazioni del server e le salvo
        // istanzio il documento per creare l'oggetto XML
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document d = b.parse(XML_PATH);

        Element root = d.getDocumentElement();

        // salvo le informazioni relative al server
        this.IP = getValue(root, IP_ATTRIBUTE);
        this.port = Integer.parseInt(getValue(root, PORT_ATTRIBUTE));
    }

    private String getValue(Element e, String attribute) {
        NodeList nodeList = e.getElementsByTagName(attribute);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }
}
