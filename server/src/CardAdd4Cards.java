/**
 * classe che gestisce una Card che aggiunge 4 carte e che cambia color
 */
public class CardAdd4Cards implements Card
{
    //attributi della CardAdd4Cards
    public String newColor;

    /**
     * costruttore non parametrico della CardAdd4Cards
     * 
     * crea una CardAdd4Cards "vuota"
     */
    public CardAdd4Cards()
    {
        //assegno valori di default agli attributi della CardAdd4Cards
        newColor = "";
    }

    @Override
    public void setColor(String color)
    {
        //this.color = color;
    }

    @Override
    public String getColor()
    {
        return ""; //ritorno un valore di default perchè la CardCambiacolor non ha un color
    }

    @Override
    public void setNumber(int number)
    {
        //this.number = number;
    }

    @Override
    public int getNumber()
    {
        return -1;  //ritorno un valore di default perchè la CardCambiacolor non ha un number
    }
    
    /**
     * metodo per confrontare questa Card con una CardAdd4Cards
     * 
     * @param card CardAdd4Cards da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean compareCard(CardAdd4Cards card)
    {
        //se la Card è null --> ERRORE
        if (card == null)
            return false; //la Card non può essere null

        //confronto il nuovo color e ritorno true o false
        //return this.newColor.equals(Card.newColor);

        //confronto questa Card con la Card passata come parametro
        return this == card;
    }

    /**
     * metodo per confrontare il nuovo color di questa Card con il nuovo color di una CardAdd4Cards
     * 
     * @param card CardAdd4Cards di cui confontare il nuovo color
     * @return true: nuovo color uguale --- false: nuovo color diverso
     */
    public boolean compareNewColorCard(CardAdd4Cards card)
    {
        //se la Card è null --> ERRORE
        if (card == null)
            return false; //la Card non può essere null

        //confronto il color di questa Card con il color della Card passata come parametro
        return this.newColor.equals(card.newColor);
    }

    public boolean isPlayable()
    {
        return false;
    }
}