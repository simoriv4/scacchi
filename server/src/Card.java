import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
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
    static Card unserialize(String cardPlayed) throws ParserConfigurationException, SAXException, IOException;
}
