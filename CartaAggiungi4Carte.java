/**
 * classe che gestisce una carta che aggiunge 4 carte e che cambia colore
 */
public class CartaAggiungi4Carte implements Carta
{
    //attributi della CartaAggiungi4Carte
    public String nuovoColore;

    /**
     * costruttore non parametrico della CartaAggiungi4Carte
     * 
     * crea una CartaAggiungi4Carte "vuota"
     */
    public CartaAggiungi4Carte()
    {
        //assegno valori di default agli attributi della CartaAggiungi4Carte
        nuovoColore = "";
    }

    @Override
    public void setColore(String colore)
    {
        //this.colore = colore;
    }

    @Override
    public String getColore()
    {
        return ""; //ritorno un valore di default perchè la CartaCambiaColore non ha un colore
    }

    @Override
    public void setNumero(int numero)
    {
        //this.numero = numero;
    }

    @Override
    public int getNumero()
    {
        return -1;  //ritorno un valore di default perchè la CartaCambiaColore non ha un numero
    }
    
    /**
     * metodo per confrontare questa carta con una CartaAggiungi4Carte
     * 
     * @param carta CartaAggiungi4Carte da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean confrontaCartaAggiungi4Carte(CartaAggiungi4Carte carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il nuovo colore e ritorno true o false
        //return this.nuovoColore.equals(carta.nuovoColore);

        //confronto questa carta con la carta passata come parametro
        return this == carta;
    }

    /**
     * metodo per confrontare il nuovo colore di questa carta con il nuovo colore di una CartaAggiungi4Carte
     * 
     * @param carta CartaAggiungi4Carte di cui confontare il nuovo colore
     * @return true: nuovo colore uguale --- false: nuovo colore diverso
     */
    public boolean confrontaNuovoColoreCarta(CartaAggiungi4Carte carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il colore di questa carta con il colore della carta passata come parametro
        return this.nuovoColore.equals(carta.nuovoColore);
    }
}