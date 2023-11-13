/**
 * classe che gestisce una Card cambia color
 */
public class CardChangeColor implements Card {
    // attributi della CardChangeColor
    public String newColor;

    /**
     * costruttore non parametrico della CardChangeColor
     * 
     * crea una CardChangeColor "vuota"
     */
    public CardChangeColor() {
        // assegno valori di default agli attributi della CardChangeColor
        newColor = "";
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
     * metodo per confrontare questa Card con una CardChangeColor
     * 
     * @param card CardChangeColor da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean compareCard(CardChangeColor card) {
        // se la Card è null --> ERRORE
        if (card == null)
            return false; // la Card non può essere null

        // confronto il nuovo color e ritorno true o false
        // return this.newColor.equals(Card.newColor);

        // confronto questa Card con la Card passata come parametro
        return this == card;
    }

    /**
     * metodo per confrontare il nuovo color di questa Card con il nuovo color di
     * una CardChangeColor
     * 
     * @param card CardChangeColor di cui confontare il nuovo color
     * @return true: nuovo color uguale --- false: nuovo color diverso
     */
    public boolean compareNewColorCard(CardChangeColor card)
    {
        // se la Card è null --> ERRORE
        if (card == null)
            return false; // la Card non può essere null

        // confronto il color di questa Card con il color della Card passata come
        // parametro
        return this.newColor.equals(card.newColor);
    }

    public boolean isPlayable()
    {
        return false;
    }
}