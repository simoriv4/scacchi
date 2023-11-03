/**
 * classe che gestisce una carta cambia colore
 */
public class CartaCambiaColore implements Carta
{
    //attributi della CartaCambiaColore
    public String nuovoColore;

    /**
     * costruttore non parametrico della CartaCambiaColore
     * 
     * crea una CartaCambiaColore "vuota"
     */
    public CartaCambiaColore()
    {
        //assegno valori di default agli attributi della CartaCambiaColore
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
     * metodo per confrontare questa carta con una CartaCambiaColore
     * 
     * @param carta CartaCambiaColore da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean confrontaCartaCambiaColore(CartaCambiaColore carta)
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
     * metodo per confrontare il nuovo colore di questa carta con il nuovo colore di una CartaCambiaColore
     * 
     * @param carta CartaCambiaColore di cui confontare il nuovo colore
     * @return true: nuovo colore uguale --- false: nuovo colore diverso
     */
    public boolean confrontaNuovoColoreCarta(CartaCambiaColore carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il colore di questa carta con il colore della carta passata come parametro
        return this.nuovoColore.equals(carta.nuovoColore);
    }
}