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
     * metodo per confrontare questa Card con una CardChangeTurn
     * 
     * @param card CardChangeTurn da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean compareCard(CardChangeTurn card)
    {
        //se la Card è null --> ERRORE
        if (card == null)
            return false; //la Card non può essere null

        //confronto il color e ritorno true o false
        //return (this.color.equals(Card.color));

        //confronto questa Card con la Card passata come parametro
        return this == card;
    }

    /**
     * metodo per confrontare il color di questa Card con il color di una CardChangeTurn
     * 
     * @param card CardChangeTurn di cui confontare il color
     * @return true: color uguale --- false: color diverso
     */
    public boolean compareColorCard(CardChangeTurn card)
    {
        //se la Card è null --> ERRORE
        if (card == null)
            return false; //la Card non può essere null

        //confronto il color di questa Card con il color della Card passata come parametro
        return this.color.equals(card.color);
    }

    public boolean isPlayable()
    {
        return false;
    }
}