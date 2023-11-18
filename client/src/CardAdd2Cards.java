import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * classe che gestisce una Card che aggiunge 2 carte (color)
 */
public class CardAdd2Cards implements Card
{
    private final static String CARD_ADD_2_CARDS = "CardAdd2Cards";

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
        this.type = CARD_ADD_2_CARDS;
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
        return -1;  //ritorno un valore di default perchè la CardCambiaGiro non ha un number
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

    // unserializzo la carta passata
    @Override
    public void unserialize(Node item) {
        // istanzio il documento per creare la stringa XML
        // DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        // DocumentBuilder b = f.newDocumentBuilder();
        // // creo un documento XML dalla stringa in formato XML passata
        // Document d = b.parse(new InputSource(new StringReader(message)));

        // // inizializzo gli attributi dell'oggetto Messaggio
        // this.command = this.unserializeCommand(d);
        // this.message = this.unserializeMessage(d);
        // this.username = this.unserializeUsername(d);
        // this.isUno = this.unserializeIsUno(d);
    }
}