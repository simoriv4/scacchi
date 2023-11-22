import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

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
     * metodo per ottenere il tipo della carta
     * @return
     */
    String getType();

    
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

    // void unserialize(Node node);

    // void unserializeFromString(String card) throws SAXException, IOException, ParserConfigurationException;
    String serializeToString() throws TransformerConfigurationException, TransformerException, ParserConfigurationException;


}
