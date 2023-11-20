import java.io.IOException;
import java.io.StringReader;

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
 * classe generica che contiene i metodi che deve avere ogni Card
 */
public interface Card
{
    /**
     * metodo per impostare il colore alla carta
     * @param color colore da impostare
     */
    void setColor(String color);

    /**
     * metodo per ottenere il colore della carta
     * @return colore della carta
     */
    String getColor();
    
    /**
     * metodo per impostare il numero della carta
     * @param number numero da impostare
     */
    void setNumber(int number);

    /**
     * metodo per ottenere il numero della carta
     * @return numero della carta
     */
    int getNumber();

    /**
     * metodo per controllare se una carta è giocabile oppure no
     * @param color colore in cima al mazzo degli scarti
     * @param number numero in cima al mazzo degli scarti
     * @return true: carta giocabile --- false: carta non giocabile
     */
    boolean isPlayable(String color, int number);

    /**
     * metodo per serializzare una Card in xml
     * @param d documento in cui serializzare la Card
     * @return Node contenente la Card serializzata
     */
    Node serialize(org.w3c.dom.Document d);

    /**
     * metodo per unserializzare una Card da una String xml
     * 
     * @param cardPlayed stringa xml da cui prendere la carta
     * @return Card letta dalla stringa xml
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    static Card unserialize(String cardPlayed) throws ParserConfigurationException, SAXException, IOException
    {
        // istanzio il documento per creare la stringa XML
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        // creo un documento XML dalla stringa in formato XML passata
        Document d = b.parse(new InputSource(new StringReader(cardPlayed)));

        //prendo la carta giocata dall'utente
        NodeList card = d.getElementsByTagName("card");

        Element e = (Element) card.item(0);

        //controllo che tipo di carta è
        if(e.getElementsByTagName("type").item(0).getTextContent().equals("CardAdd4Cards"))
        {
            return new CardAdd4Cards();
        }

        if(e.getElementsByTagName("type").item(0).getTextContent().equals("CardChangeColor"))
        {
            return new CardChangeColor();
        }

        if(e.getElementsByTagName("type").item(0).getTextContent().equals("CardAdd2Cards"))
        {
            return new CardAdd2Cards(e.getElementsByTagName("color").item(0).getTextContent());
        }

        if(e.getElementsByTagName("type").item(0).getTextContent().equals("CardBlock"))
        {
            return new CardBlock(e.getElementsByTagName("color").item(0).getTextContent());
        }

        if(e.getElementsByTagName("type").item(0).getTextContent().equals("CardChangeTurn"))
        {
            return new CardChangeTurn(e.getElementsByTagName("color").item(0).getTextContent());
        }

        if(e.getElementsByTagName("type").item(0).getTextContent().equals("CardNumber"))
        {
            return new CardNumber(e.getElementsByTagName("color").item(0).getTextContent(), Integer.parseInt(e.getElementsByTagName("number").item(0).getTextContent()));
        }

        return null;
    }
}
