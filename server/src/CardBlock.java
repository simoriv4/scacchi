/**
 * classe che gestisce una Card blocca (color)
 */
public class CardBlock implements Card
{
    //attributi della CardBlock
    public String color;

    /**
     * costruttore parametrico della CardBlock
     * 
     * @param color color della Card
     */
    public CardBlock(String color)
    {
        //assegno i valori passati come parametro agli attributi della CardBlock
        this.color = color;
    }

    /**
     * costruttore non parametrico della CardBlock
     * 
     * crea una CardBlock "vuota"
     */
    public CardBlock()
    {
        //assegno valori di default agli attributi della CardBlock
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
     * metodo per confrontare questa Card con una CardBlock
     * 
     * @param card CardBlock da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean confrontaCardBlock(CardBlock card)
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
     * metodo per confrontare il color di questa Card con il color di una CardBlock
     * 
     * @param card CardBlock di cui confontare il color
     * @return true: color uguale --- false: color diverso
     */
    public boolean confrontacolorCard(CardBlock card)
    {
        //se la Card è null --> ERRORE
        if (card == null)
            return false; //la Card non può essere null

        //confronto il color di questa Card con il color della Card passata come parametro
        return this.color.equals(card.color);
    }
}