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

    public boolean isPlayable(String color, int number)
    {
        return false;
    }
}