import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * classe che gestisce una Card che aggiunge 2 carte (color)
 */
public class CardAdd2Cards implements Card {
    private final static String CARD_ADD_2_CARDS = "CardAdd2Cards";
    private final static String CARD_ADD_4_CARDS = "CardAdd4Cards";
    private final static String CARD_BLOCK = "CardBlock";
    private final static String CARD_CHANGE_COLOR = "CardChangeColor";
    private final static String CARD_CHANGE_TURN = "CardChangeTurn";
    private final static String CARD_NUMBER = "CardNumber";

    // attributi della CardAdd2Cards
    public String color;

    public String type;

    /**
     * costruttore parametrico della CardAdd2Cards
     * 
     * @param color color della Card
     */
    public CardAdd2Cards(String color) {
        // assegno i valori passati come parametro agli attributi della CardAdd2Cards
        this.color = color;
        this.type = CARD_ADD_2_CARDS;
    }

    /**
     * costruttore non parametrico della CardAdd2Cards
     * 
     * crea una CardAdd2Cards "vuota"
     */
    public CardAdd2Cards() {
        // assegno valori di default agli attributi della CardAdd2Cards
        color = "";
        this.type = "CardAdd2Cards";
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setNumber(int number) {
        // this.number = number;
    }

    @Override
    public int getNumber() {
        return -1; // ritorno un valore di default perchè la CardCambiaGiro non ha un number
    }

    /**
     * metodo per confrontare il colore di quest carta con un colore
     * 
     * @param color colore con cui confrontare
     * @return true: colori uguali --- false: colori diversi
     */
    public boolean compareColor(String color) {
        return this.color.equals(color);
    }

    /**
     * metodo per controllare se una carta è giocabile oppure no
     * 
     * @param color  colore in cima al mazzo degli scarti
     * @param number numero in cima al mazzo degli scarti
     * @return true: carta giocabile --- false: carta non giocabile
     */
    public boolean isPlayable(String color, int number) {
        /*
         * la carta ha solo il colore e quindi devo confrontare solo quello
         */
        return compareColor(color);
    }
/**
     * metodo per serializzare in stringa la carta
     * 
     * @throws ParserConfigurationException
     */
    @Override
    public String serializeToString()
            throws TransformerConfigurationException, TransformerException, ParserConfigurationException {
        // istanzio il documento per creare la stringa XML
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document d = b.newDocument();

        Element root = d.createElement("card");
        root.appendChild(this.serializeType(d));
        root.appendChild(this.serializeColor(d));

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
     * funzione che serializza in XML l'attributo type
     * 
     * @param d
     * @return il nodo
     */
    public Node serializeType(Document d) {
        Node type = d.createElement("type");
        type.setTextContent(this.type);
        return type;
    }

    /**
     * funzione che serializza in XML l'attributo type
     * 
     * @param d
     * @return il nodo
     */
    public Node serializeColor(Document d) {
        Node color = d.createElement("color");
        color.setTextContent(this.color);
        return color;
    }
    // // unserializzo la carta passata
    // @Override
    // public void unserializeFromString(String card) throws SAXException, IOException, ParserConfigurationException {
    //     // istanzio il documento per creare la stringa XML
    //     DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
    //     DocumentBuilder b = f.newDocumentBuilder();
    //     // creo un documento XML dalla stringa in formato XML passata
    //     Document d = b.parse(new InputSource(new StringReader(card)));

    //     // prendo tutte gli elementi con il tag "card"
    //     NodeList cards = d.getElementsByTagName("card");

    //     Node cardNode = cards.item(0);

    //     Element cardElement = (Element) cardNode;

    //     // ottengo il tipo di carta
    //     String cardType = cardElement.getElementsByTagName("type").item(0).getTextContent();

    //     if (cardType.equals(CARD_ADD_2_CARDS)) {
    //         // Ottieni il colore
    //         this.color = cardElement.getElementsByTagName("color").item(0).getTextContent();
    //         this.type = cardType;
    //     }
    // }
}