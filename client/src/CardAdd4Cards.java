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

/**
 * classe che gestisce una Card che aggiunge 4 carte e che cambia color
 */
public class CardAdd4Cards implements Card
{
    private final static String CARD_ADD_4_CARDS = "CardAdd4Cards";

    //attributi della CardAdd4Cards
    public String newColor;
    public String type;


    /**
     * costruttore non parametrico della CardAdd4Cards
     * 
     * crea una CardAdd4Cards "vuota"
     */
    public CardAdd4Cards()
    {
        //assegno valori di default agli attributi della CardAdd4Cards
        newColor = "";
        this.type = CARD_ADD_4_CARDS ;
    }

    public CardAdd4Cards(String newColor)
    {
        //assegno valori di default agli attributi della CardAdd4Cards
        this.newColor = newColor;
        this.type = CARD_ADD_4_CARDS ;
    }

    @Override
    public void setColor(String color)
    {
        //this.color = color;
    }

    @Override
    public String getColor()
    {
        return ""; //ritorno un valore di default perchè la CardCambiacolor non ha un color
    }
    @Override
    public String getType()
    {
        return this.type;
    }

    @Override
    public void setNumber(int number)
    {
        //this.number = number;
    }

    @Override
    public int getNumber()
    {
        return -1;  //ritorno un valore di default perchè la CardCambiacolor non ha un number
    }

    /**
     * metodo per controllare se una carta è giocabile oppure no
     * @param color colore in cima al mazzo degli scarti
     * @param number numero in cima al mazzo degli scarti
     * @return true: carta giocabile --- false: carta non giocabile
     */
    public boolean isPlayable(String color, int number)
    {
        /*
         * la carta è una carta speciale e quindi è sempre giocabile
         */
        return true;
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


    // unserializzo la carta passata
    public void unserialize(Node item) {
        Element element = (Element) item;

        NamedNodeMap attributes = element.getAttributes();

        this.type = attributes.item(0).getTextContent();
        // assegno il valore al colore
        this.newColor = attributes.item(1).getTextContent();
    }
    
}