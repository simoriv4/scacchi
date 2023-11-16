import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * classe che gestisce una Card cambia giro (color)
 */
public class CardChangeTurn implements Card
{
    //attributi della CardChangeTurn
    public String color;

    /**
     * costruttore parametrico della CardChangeTurn
     * 
     * @param color color della Card
     */
    public CardChangeTurn(String color)
    {
        //assegno i valori passati come parametro agli attributi della CardChangeTurn
        this.color = color;
    }

    /**
     * costruttore non parametrico della CardChangeTurn
     * 
     * crea una CardChangeTurn "vuota"
     */
    public CardChangeTurn()
    {
        //assegno valori di default agli attributi della CardChangeTurn
        color = "";
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
        return -1;  //ritorno un valore di default perchè la CardChangeTurn non ha un number
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
    public void unserialize(Node item) {
        Element element = (Element) item;

        NamedNodeMap attributes = element.getAttributes();
        // assegno il valore al colore
        this.color = attributes.item(0).getTextContent();
    }
}