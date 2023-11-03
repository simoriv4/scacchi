/**
 * classe che gestisce una carta numerata (colore e numero)
 */
public class CartaNumerata implements Carta
{
    //attributi della CartaNumerata
    public String colore;
    public int numero;

    /**
     * costruttore parametrico della CartaNumerata
     * 
     * @param colore colore della carta
     * @param numero numero della carta
     */
    public CartaNumerata(String colore, int numero)
    {
        //assegno i valori passati come parametro agli attributi della CartaNumerata
        this.colore = colore;
        this.numero = numero;
    }

    /**
     * costruttore non parametrico della CartaNumerata
     * 
     * crea una CartaNumerata "vuota"
     */
    public CartaNumerata()
    {
        //assegno valori di default agli attributi della CartaNumerata
        colore = "";
        numero = -1;
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
        this.numero = numero;
    }

    @Override
    public int getNumero()
    {
        return numero;
    }
    
    /**
     * metodo per confrontare questa carta con una CartaNumerata
     * 
     * @param carta CartaNumerata da confrontare
     * @return true: carte uguali --- false: carte diverse
     */
    public boolean confrontaCartaNumerata(CartaNumerata carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il colore e il numero e ritorno true o false
        //return (this.colore.equals(carta.colore) && this.numero == carta.numero);

        //confronto questa carta con la carta passata come parametro
        return this == carta;
    }

    /**
     * metodo per confrontare il numero di questa carta con il numero di una CartaNumerata
     * 
     * @param carta CartaNumerata di cui confontare il numero
     * @return true: numero uguale --- false: numero diverso
     */
    public boolean confrontaNumeroCarta(CartaNumerata carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il numero di questa carta con il numero dellla carta passata come parametro
        return this.numero == carta.numero;
    }

    /**
     * metodo per confrontare il colore di questa carta con il colore di una CartaNumerata
     * 
     * @param carta CartaNumerata di cui confontare il colore
     * @return true: colore uguale --- false: colore diverso
     */
    public boolean confrontaColoreCarta(CartaNumerata carta)
    {
        //se la carta è null --> ERRORE
        if (carta == null)
            return false; //la carta non può essere null

        //confronto il colore di questa carta con il colore della carta passata come parametro
        return this.colore.equals(carta.colore);
    }
}