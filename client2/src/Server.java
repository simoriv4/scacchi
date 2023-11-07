import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Server {
    // classe che contiene le informazioni del server--> caricate dal file xml
    // costante nome file
    final static String fileName = "configServer.xml";
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
     */
    public Server() {
        this.SaveFromXML(); // richiamo la funzione per popolare gli attributi
    }

    /**
     * funzione che popola le informazioni del server prendendo i dati dal file xml
     */
    public void SaveFromXML()
    {
        try {
            // Crea un oggetto DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Crea un oggetto DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parsa il file XML e restituisce un oggetto Document
            Document document = builder.parse("esempio.xml");

            // Ottieni l'elemento radice
            Element root = document.getDocumentElement();

            // Leggi i nodi figli dell'elemento radice
            NodeList nodeList = root.getChildNodes();

            // scorro la lista dei nodi dell'xml
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element element = (Element) nodeList.item(i);
                    String nodeName = element.getNodeName();
                    // controllo quale attributo sto analizzando
                    switch (nodeName) {
                        case "IP":
                            // salvo il valore dell'attributo
                            this.IP = element.getTextContent();
                            break;
                        case "port":
                            // salvo il valore dell'attributo
                            this.port = Integer.parseInt(element.getTextContent());
                            break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
