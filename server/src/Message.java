import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class Message {
    // comando
    // username
    // messsaggio
    String command;
    String username;
    String message;
    String numberCardsRivals; // lista serializzata <u1><numberCard>3</numberCard></u1>
    String discarderdCard;
    Boolean isUno;

    /**
     * costruttore parametrico
     * @param isUno
     * @param command
     * @param username
     * @param message
     * @param discaredCard
     */
    public Message(Boolean isUno,String command, String username, String message, String discaredCard) {
        this.isUno = isUno;
        this.command = command;
        this.username = username;
        this.message = message;
        this.discarderdCard = discarderdCard;
    }

    /**
     * costruttore non parametrico
     */
    public Message() {
        this.isUno = false;
        this.command = "";
        this.message = "";
        this.username = "username";
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
        root.appendChild(serializeIsUno(d));
        root.appendChild(serializeDiscardedCard(d));



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
    public Node serializeIsUno(Document d) {
        Node isUno = d.createElement("isUno");
        isUno.setTextContent(this.isUno.toString());
        return isUno;
    }
    /**
     * creo il nodo da aggiungere alla root
     * @param d
     * @return il nodo creato
     */
    public Node serializeDiscardedCard(Document d) {
        Node discardedCard = d.createElement("discardedCard");
        discardedCard.setTextContent(this.isUno.toString());
        return discardedCard;
    }
    /**
     * creo il nodo da aggiungere alla root
     * @param d
     * @return il nodo creato
     */
    public Node serializeMessage(Document d) {
        Node message = d.createElement("message");
        message.setTextContent(this.message);
        return message;
    }
    /**
     * creo il nodo da aggiungere alla root
     * @param d
     * @return il nodo creato
     */
    public Node serializeUsername(Document d) {
        Node username = d.createElement("username");
        username.setTextContent(this.username);
        return username;
    }

    /**
     * creo il nodo da aggiungere alla root
     * @param d
     * @return il nodo creato
     */
    public Node serializeCommand(Document d) {
        Node command = d.createElement("command");
        command.setTextContent(this.command);
        return command;
    }

    /**
     * funzione che da una stringa xml estrae i le informazioni degli attributi del messaggio
     * @param message
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public void InitMessageFromStringXML(String message) throws ParserConfigurationException, SAXException, IOException
    {
        // istanzio il documento per creare la stringa XML
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        // creo un documento XML dalla stringa in formato XML passata
        Document d = b.parse(new InputSource(new StringReader(message)));

        // inizializzo gli attributi dell'oggetto Messaggio
        this.command = this.unserializeCommand(d);
        this.message = this.unserializeMessage(d);
        this.username = this.unserializeUsername(d);
        this.isUno = this.unserializeIsUno(d);

    }
    /**
     * funzione che converte il tag XML command in stringa
     * @param d
     * @return il contenuto del tag XML command
     */
    public Boolean unserializeIsUno(Document d) {
        NodeList nl = d.getElementsByTagName("command");
        return Boolean.parseBoolean(nl.item(0).getTextContent());
    }
    /**
     * funzione che converte il tag XML command in stringa
     * @param d
     * @return il contenuto del tag XML command
     */
    public String   unserializeCommand(Document d) {
        NodeList nl = d.getElementsByTagName("command");
        return nl.item(0).getTextContent();
    }
    

    /**
     * funzione che converte il tag XML message in stringa
     * @param d
     * @return il contenuto del tag XML message
     */
    public String unserializeMessage(Document d) {
        NodeList nl = d.getElementsByTagName("message");
        return nl.item(0).getTextContent();
    }
    /**
     * funzione che converte il tag XML username in stringa
     * @param d
     * @return il contenuto del tag XML username
     */
    public String unserializeUsername(Document d) {
        NodeList nl = d.getElementsByTagName("username");
        return nl.item(0).getTextContent();
    }
    

}