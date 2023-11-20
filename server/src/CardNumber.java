import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * classe che gestisce una Card numerata (color e number)
 */
public class CardNumber implements Card
{
    //attributi della CardNumber
    public String color;
    public int number;
    public String type;

    /**
     * costruttore parametrico della CardNumber
     * 
     * @param color color della Card
     * @param number number della Card
     */
    public CardNumber(String color, int number)
    {
        //assegno i valori passati come parametro agli attributi della CardNumber
        this.color = color;
        this.number = number;
    }

    /**
     * costruttore non parametrico della CardNumber
     * 
     * crea una CardNumber "vuota"
     */
    public CardNumber()
    {
        //assegno valori di default agli attributi della CardNumber
        color = "";
        number = -1;
        this.type = "CardNumber";
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
        this.number = number;
    }

    @Override
    public int getNumber()
    {
        return number;
    }
    @Override
    public Node serialize(Document d)
    {
        Element root = d.createElement("card");
        root.appendChild(this.serializeType(d));
        root.appendChild(this.serializeColor(d));
        root.appendChild(this.serializeNumber(d));
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
     * funzione che serializza in XML l'attributo color
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
     * funzione che serializza in XML l'attributo color
     * @param d
     * @return il nodo
     */
    public Node serializeNumber(Document d)
    {
        Node number = d.createElement("number");
        number.setTextContent(String.valueOf(this.number));
        return number;
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
     * metodo per confrontare il numerodi quest carta con un numero
     * @param number numero con cui confrontare
     * @return true: numeri uguali --- false: numeri diversi
     */
    public boolean compareNumber(int number)
    {
        return this.number == number;
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
         * una carta numerata è giocabile se una delle due (o tutte e due) le situazioni sono vere:
         * - il colore è uguale a quello in cima al mazzo
         * - il numero è uguale a quello in cima al mazzo
         */

        //confronto il colore
        if(compareColor(color) == true)
            return true;    //carta giocabile

        //confronto il numero
        if(compareNumber(number) == true)
            return true;    //carta giocabile

        //nessuno dei due confronti è risultato vero
        return false;   //carta non giocabile
    }
}