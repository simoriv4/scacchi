/**
 * classe che gestisce una carta cambia giro (colore)
 */
public class CartaCambiaGiro implements Carta
{
    //attributi della CartaCambiaGiro
    public String colore;

    /**
     * costruttore parametrico della CartaCambiaGiro
     * 
     * @param colore colore della carta
     */
    public CartaCambiaGiro(String colore)
    {
        //assegno i valori passati come parametro agli attributi della CartaCambiaGiro
        this.colore = colore;
    }

    /**
     * costruttore non parametrico della CartaCambiaGiro
     * 
     * crea una CartaCambiaGiro "vuota"
     */
    public CartaCambiaGiro()
    {
        //assegno valori di default agli attributi della CartaCambiaGiro
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
     * metodo per confrontare questa carta con una CartaCambiaGiro
     * 
     * @param carta CartaCambiaGiro da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean confrontaCartaCambiaGiro(CartaCambiaGiro carta)
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
     * metodo per confrontare il colore di questa carta con il colore di una CartaCambiaGiro
     * 
     * @param carta CartaCambiaGiro di cui confontare il colore
     * @return true: colore uguale --- false: colore diverso
     */
    public boolean confrontaColoreCarta(CartaCambiaGiro carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il colore di questa carta con il colore della carta passata come parametro
        return this.colore.equals(carta.colore);
    }
}