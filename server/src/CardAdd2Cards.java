/**
 * classe che gestisce una Card che aggiunge 2 carte (color)
 */
public class CardAdd2Cards implements Card
{
    //attributi della CardAdd2Cards
    public String color;

    /**
     * costruttore parametrico della CardAdd2Cards
     * 
     * @param color color della Card
     */
    public CardAdd2Cards(String color)
    {
        //assegno i valori passati come parametro agli attributi della CardAdd2Cards
        this.color = color;
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
    
    /**
     * metodo per confrontare questa Card con una CardAdd2Cards
     * 
     * @param card CardAdd2Cards da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean compareCard(CardAdd2Cards card)
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
     * metodo per confrontare il color di questa Card con il color di una CardAdd2Cards
     * 
     * @param card CardAdd2Cards di cui confontare il color
     * @return true: color uguale --- false: color diverso
     */
    public boolean compareColorCard(CardAdd2Cards card)
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