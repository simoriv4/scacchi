/**
 * classe che gestisce tutto il gioco (mazzo, scarti e giocatori)
 */
public class Game
{
    //attributi
    public Deck<Card> deck;
    public Deck<Card> discardedCards;

    /**
     * costruttore non parametrico del Game
     * 
     * crea il mazzo delle carte e il mazzo degli scarti
     */
    public Game()
    {
        //creo il mazzo
        deck = new Deck<Card>();

        //riempio il mazzo
        deck.fillDeck();

        //creo il mazzo degli scarti
        discardedCards = new Deck<Card>();
    }

    //distribuisci carte (agli utente e 1 sul tavolo nel mazzo degli scarti)
    //.....

    //pesca carta (leva la prima carta nel deck)
    //....

    //gioca carta (confornta con l'ultima carta scartata e vede se si può giocare quella carta)
    //....

    /**
     * metodo per ottenere il colore della carta in cima al mazzo degli scarti
     * @return  colore della carta in cima al mazzo degli scarti
     */
    public String getColorCardOnTopOfDiscardedCards()
    {
        return discardedCards.getColorCardOnTop();
    }

    /**
     * metodo per ottenere il numero della carta in cima al mazzo degli scarti
     * @return  numero della carta in cima al mazzo degli scarti
     */
    public int getNumberCardOnTopOfDiscardedCards()
    {
        return discardedCards.getNumberCardOnTop();
    }
}
