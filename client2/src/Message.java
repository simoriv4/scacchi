import java.io.StringWriter;

import javax.xml.crypto.NodeSetData;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class Message {
    // comando
    // username
    // messsaggio
    String command;
    String username;
    String message;

    /**
     * costruttore parametrico
     * 
     * @param command
     * @param message
     */
    public Message(String command, String username, String message) {
        this.command = command;
        this.username = username;
        this.message = message;
    }

    /**
     * costruttore non parametrico
     */
    public Message() {
        this.command = "";
        this.message = "";
    }

    /**
     * converto l'oggetto in una stringa XML
     * 
     * @return stringa xml del messaggio
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public String serialize() throws ParserConfigurationException, TransformerException {
        // istanzio il documento per creare la stringa XML
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document d = b.newDocument();

        // creo la root dell'XML
        Element root = d.createElement("root_message");
        // aggiungo alla root i nodi 
        root.appendChild(serializeCommand(d));
        root.appendChild(serializeUsername(d));
        root.appendChild(serializeMessage(d));

        // aggiungo la root al documento
        d.appendChild(root);

        // SALVA SU STRINGA
        // Creare un oggetto Transformer per la trasformazione in stringa
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(d), new StreamResult(writer));
        String xmlString = writer.toString();

        return xmlString;
    }

    /**
     * creo il nodo da aggiungere alla root
     * @param d
     * @return il nodo creato
     */
    public Node serializeMessage(Document d) {
        Node message = d.createElement("message");
        return message;
    }
    /**
     * creo il nodo da aggiungere alla root
     * @param d
     * @return il nodo creato
     */
    public Node serializeUsername(Document d) {
        Node username = d.createElement("username");
        return username;
    }

    /**
     * creo il nodo da aggiungere alla root
     * @param d
     * @return il nodo creato
     */
    public Node serializeCommand(Document d) {
        Node command = d.createElement("command");
        return command;
    }

}