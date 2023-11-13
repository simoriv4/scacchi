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

    public boolean isPlayable(String color, int number)
    {
        return false;
    }
}