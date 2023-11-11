/**
 * classe che gestisce una Card numerata (color e number)
 */
public class CardNumber implements Card
{
    //attributi della CardNumber
    public String color;
    public int number;

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
    
    /**
     * metodo per confrontare questa Card con una CardNumber
     * 
     * @param card CardNumber da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean confrontaCardNumber(CardNumber card)
    {
        //se la Card è null --> ERRORE
        if (card == null)
            return false; //la Card non può essere null

        //confronto il color e il number e ritorno true o false
        //return (this.color.equals(Card.color) && this.number == Card.number);

        //confronto questa Card con la Card passata come parametro
        return this == card;
    }

    /**
     * metodo per confrontare il number di questa Card con il number di una CardNumber
     * 
     * @param card CardNumber di cui confontare il number
     * @return true: number uguale --- false: number diverso
     */
    public boolean confrontanumberCard(CardNumber card)
    {
        //se la Card è null --> ERRORE
        if (card == null)
            return false; //la Card non può essere null

        //confronto il number di questa Card con il number dellla Card passata come parametro
        return this.number == card.number;
    }

    /**
     * metodo per confrontare il color di questa Card con il color di una CardNumber
     * 
     * @param card CardNumber di cui confontare il color
     * @return true: color uguale --- false: color diverso
     */
    public boolean confrontacolorCard(CardNumber card)
    {
        //se la Card è null --> ERRORE
        if (card == null)
            return false; //la Card non può essere null

        //confronto il color di questa Card con il color della Card passata come parametro
        return this.color.equals(card.color);
    }
}