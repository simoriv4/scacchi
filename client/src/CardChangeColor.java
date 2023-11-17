import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * classe che gestisce una Card cambia color
 */
public class CardChangeColor implements Card {
    // attributi della CardChangeColor
    public String newColor;
    public String type;


    /**
     * costruttore non parametrico della CardChangeColor
     * 
     * crea una CardChangeColor "vuota"
     */
    public CardChangeColor() {
        // assegno valori di default agli attributi della CardChangeColor
        newColor = "";
        this.type = "";
    }

    @Override
    public void setColor(String color) {
        // this.color = color;
    }

    @Override
    public String getColor() {
        return ""; // ritorno un valore di default perchè la CardChangeColor non ha un color
    }

    @Override
    public void setNumber(int number) {
        // this.number = number;
    }

    @Override
    public int getNumber() {
        return -1; // ritorno un valore di default perchè la CardChangeColor non ha un number
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

    // unserializzo la carta passata
    public void unserialize(Node item) {
        Element element = (Element) item;

        NamedNodeMap attributes = element.getAttributes();

        this.type = attributes.item(0).getTextContent();
        // assegno il valore al colore
        this.newColor = attributes.item(1).getTextContent();
    }
}