/**
 * classe che gestisce una carta blocca (colore)
 */
public class CartaBlocca implements Carta
{
    //attributi della CartaBlocca
    public String colore;

    /**
     * costruttore parametrico della CartaBlocca
     * 
     * @param colore colore della carta
     */
    public CartaBlocca(String colore)
    {
        //assegno i valori passati come parametro agli attributi della CartaBlocca
        this.colore = colore;
    }

    /**
     * costruttore non parametrico della CartaBlocca
     * 
     * crea una CartaBlocca "vuota"
     */
    public CartaBlocca()
    {
        //assegno valori di default agli attributi della CartaBlocca
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
     * metodo per confrontare questa carta con una CartaBlocca
     * 
     * @param carta CartaBlocca da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean confrontaCartaBlocca(CartaBlocca carta)
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
     * metodo per confrontare il colore di questa carta con il colore di una CartaBlocca
     * 
     * @param carta CartaBlocca di cui confontare il colore
     * @return true: colore uguale --- false: colore diverso
     */
    public boolean confrontaColoreCarta(CartaBlocca carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il colore di questa carta con il colore della carta passata come parametro
        return this.colore.equals(carta.colore);
    }
}