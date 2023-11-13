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
}