import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * classe che gestisce tutto il gioco (mazzo, scarti e giocatori)
 */
public class Game
{
    //COSTANTI
    private final static int NUMBER_CARDS_FOR_USER = 10;

    //attributi
    public Deck<Card> deck;
    public Deck<Card> discardedCards;
    private ArrayList<User> users;
    private ServerSocket serverSocket;

    /**
     * costruttore non parametrico del Game

     * @throws IOException
     */
    public Game() throws IOException
    {
        //creo il mazzo
        deck = new Deck<Card>();

        //riempio il mazzo
        deck.fillDeck();

        //crea gli utenti
        users = new ArrayList<>();

        //creo il mazzo degli scarti
        discardedCards = new Deck<Card>();

        //     // inizializzo la socket del server
        //     serverSocket = new ServerSocket(666);
        //     // metto in ascolto il server
        //     // while (true) {
        //         Socket clientSocket = serverSocket.accept(); // Bloccante, aspetta una connessione
        //         //System.out.println("Nuova connessione da: " + clientSocket.getInetAddress().getHostAddress());

        //         // Crea un oggetto di comunicazione per gestire la connessione con il client
        //         // Communication communication = new Communication(clientSocket);

        //         // UTENTE TEST!!!
        //         User tmp = new User();
        //         tmp.port = 666;

        //         ThreadClient client1= new ThreadClient(tmp);  // NEW USER() E' TEMPORANEO--> BISOGNA PASSARE IL CLIENT CORRISPONDENTE
        //          // avvio il thread per la gestione della comunicazione con il client
        //         client1.start();
            
        // // }
        
    }

    /**
     * metodo per distribuire le carte all'inizio del gioco
     */
    public void dealCards()
    {
        //per il numero di carte che devo dare a ogni User
        for(int i = 0; i < NUMBER_CARDS_FOR_USER; i++)
        {
            //per ogni User
            for (User user : users)
            {
                //aggiungo alle carte dell'utente la prima carta del mazzo
                user.addCard(deck.getCard());
            }
        }

        //ho finito di dare le carte agli utenti
        //metto una carta negli scarti
        discardedCards.addCard(deck.getCard());
    }

    /**
     * metodo per pescare una carta dal mazzo
     * 
     * @param user user che ha pescato la carta
     */
    public void drawCard(User user)
    {
        //aggiungo alle carte dell'utente la prima carta del mazzo
        user.addCard(deck.getCard());
    }

    /**
     * metodo per controllare se la carta giocata è corretta oppure no
     * @param cardPlayed carta giocata dall'utente
     * @return true: carta giocabile --- false: carta non giocabile
     */
    public boolean checkCardPlayed(Card cardPlayed)
    {
        return cardPlayed.isPlayable(getColorCardOnTopOfDiscardedCards(), getNumberCardOnTopOfDiscardedCards());
    }

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