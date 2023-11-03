/**
 * classe che gestisce una carta ceh aggiunge 2 carte (colore)
 */
public class CartaAggiungi2Carte implements Carta
{
    //attributi della CartaAggiungi2Carte
    public String colore;

    /**
     * costruttore parametrico della CartaAggiungi2Carte
     * 
     * @param colore colore della carta
     */
    public CartaAggiungi2Carte(String colore)
    {
        //assegno i valori passati come parametro agli attributi della CartaAggiungi2Carte
        this.colore = colore;
    }

    /**
     * costruttore non parametrico della CartaAggiungi2Carte
     * 
     * crea una CartaAggiungi2Carte "vuota"
     */
    public CartaAggiungi2Carte()
    {
        //assegno valori di default agli attributi della CartaAggiungi2Carte
        colore = "";
    }

    @Override
    public void setColore(String colore)
    {
        this.colore = colore;
    }

    @Override
    public String getColore()
    {
        return colore;
    }

    @Override
    public void setNumero(int numero)
    {
        //this.numero = numero;
    }

    @Override
    public int getNumero()
    {
        return -1;  //ritorno un valore di default perchè la CartaCambiaGiro non ha un numero
    }
    
    /**
     * metodo per confrontare questa carta con una CartaAggiungi2Carte
     * 
     * @param carta CartaAggiungi2Carte da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean confrontaCartaAggiungi2Carte(CartaAggiungi2Carte carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il colore e ritorno true o false
        //return (this.colore.equals(carta.colore));

        //confronto questa carta con la carta passata come parametro
        return this == carta;
    }

    /**
     * metodo per confrontare il colore di questa carta con il colore di una CartaAggiungi2Carte
     * 
     * @param carta CartaAggiungi2Carte di cui confontare il colore
     * @return true: colore uguale --- false: colore diverso
     */
    public boolean confrontaColoreCarta(CartaAggiungi2Carte carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il colore di questa carta con il colore della carta passata come parametro
        return this.colore.equals(carta.colore);
    }
}