import java.io.IOException;
import java.io.StringReader;

import javax.swing.event.CaretListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * classe che gestisce una Card che aggiunge 2 carte (color)
 */
public class CardAdd2Cards implements Card
{
    //attributi della CardAdd2Cards
    public String color;
    public String type;


    /**
     * costruttore parametrico della CardAdd2Cards
     * 
     * @param color color della Card
     */
    public CardAdd2Cards(String color)
    {
        //assegno i valori passati come parametro agli attributi della CardAdd2Cards
        this.color = color;
        this.type = "CardAdd2Cards";
    }

    /**
     * costruttore non parametrico della CardAdd2Cards
     * 
     * crea una CardAdd2Cards "vuota"
     */
    public CardAdd2Cards()
    {
        //assegno valori di default agli attributi della CardAdd2Cards
        color = "";
        this.type = "CardAdd2Cards";
    }

    @Override
    public void setColor(String color)
    {
        this.color = color;
    }

    @Override
    public String getColor()
    {
        return color;
    }

    @Override
    public void setNumber(int number)
    {
        //this.number = number;
    }

    @Override
    public int getNumber()
    {
        return -1;  //ritorno un valore di default perchè la CardCambiaGiro non ha un number
    }

    @Override
    public Node serialize(Document d)
    {
        Element root = d.createElement("card");
        root.appendChild(this.serializeType(d));
        root.appendChild(this.serializeColor(d));
        return root;
    }

    /**
     * funzione che serializza in XML l'attributo type
     * @param d
     * @return il nodo
     */
    public Node serializeType(Document d)
    {
        Node type = d.createElement("type");
        type.setTextContent(this.type);
        return type;
    }
    /**
     * funzione che serializza in XML l'attributo type
     * @param d
     * @return il nodo
     */
    public Node serializeColor(Document d)
    {
        Node color = d.createElement("color");
        color.setTextContent(this.color);
        return color;
    }
    
    /**
     * metodo per confrontare il colore di quest carta con un colore
     * @param color colore con cui confrontare
     * @return true: colori uguali --- false: colori diversi
     */
    public boolean compareColor(String color)
    {
        return this.color.equals(color);
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
         * la carta ha solo il colore e quindi devo confrontare solo quello
         */
        return compareColor(color);
    }

    /**
     * metodo per unserializzare una carta CardsAdd2Cards da un Element
     * 
     * @param cardPlayed elemento da cui prendere la carta
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    static public Card unserialize(String cardPlayed) throws ParserConfigurationException, SAXException, IOException
    {
        // istanzio il documento per creare la stringa XML
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        // creo un documento XML dalla stringa in formato XML passata
        Document d = b.parse(new InputSource(new StringReader(cardPlayed)));

        // prendo tutte le carte del mazzo
        NodeList cards = d.getElementsByTagName("card");

        Element e = (Element) cards.item(0);

        CardAdd2Cards c = new CardAdd2Cards();

        //prendo il colore della carta dall'elemento
        c.color = e.getElementsByTagName("color").item(0).getTextContent();

        return c;
    }
}