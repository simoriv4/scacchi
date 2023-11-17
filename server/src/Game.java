import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

/**
 * classe che gestisce tutto il gioco (mazzo, scarti e giocatori)
 */
public class Game {
    // COSTANTI
    private final static int NUMBER_CARDS_FOR_USER = 10;

    // messaggi di risposta
    private final static String CORRECT = "200";
    private final static String ERROR_USERNAME = "400";
    private final static String ERROR_CARD_PALYED = "406";
    private final static String WINNER = "201";
    private final static String ERROR_SKIP = "409";
    private final static String ERROR_EXIT = "500";

    // attributi
    public Deck<Card> deck;
    public Deck<Card> discardedCards;
    private ArrayList<User> users;
    private ServerSocket serverSocket;
    private Message message;

    /**
     * costruttore non parametrico del Game
     * 
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public Game() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        // creo il mazzo
        deck = new Deck<Card>();

        // riempio il mazzo
        deck.fillDeck();

        // crea gli utenti
        users = new ArrayList<>();

        // creo il mazzo degli scarti
        discardedCards = new Deck<Card>();

        // inizializzo la socket del server
        serverSocket = new ServerSocket(666);
        // metto in ascolto il server
        Socket clientSocket = serverSocket.accept(); // Bloccante, aspetta una connessione
        // System.out.println("Nuova connessione da: " +
        // clientSocket.getInetAddress().getHostAddress());

        // Crea un oggetto di comunicazione per gestire la connessione con il client
        Communication communication = new Communication(clientSocket);
        String message_client = communication.listening();
        System.out.println(message_client);
        this.message = new Message();
        this.message.InitMessageFromStringXML(message_client);
        Boolean isAvailable = true;
        // controllo che non ci siano utenti con quel nome
        for (User u : this.users) {
            if(u.userName.equals(this.message.message))
            {
                isAvailable = false;
                // mando messaggio di errore--> esiste già
                // inizializzo l'oggetto messaggio
                this.message = new Message(false, ERROR_USERNAME, "","Username non disponibile" );
            }
        }
        if(isAvailable)
        {
            // creo l'utente e lo aggiungo alla lista
            User u = new User(clientSocket.getPort(), "127.0.0.1", false, false, false, this.message.message);
            this.users.add(u);   
            // inizializzo messaggio di risposta     
            this.message = new Message(u.isUno, CORRECT, u.userName,"Username disponibile");
            // avvio il thread per quel client
            // ThreadClient tc =new ThreadClient(u);
            // tc.start();
        }
        //  invio messaggio di risposta
        communication.sendMessage(this.message);
    }

    /**
     * metodo per distribuire le carte all'inizio del gioco
     */
    public void dealCards() {
        // per il numero di carte che devo dare a ogni User
        for (int i = 0; i < NUMBER_CARDS_FOR_USER; i++) {
            // per ogni User
            for (User user : users) {
                // aggiungo alle carte dell'utente la prima carta del mazzo
                user.addCard(deck.getCard());
            }
        }

        // ho finito di dare le carte agli utenti
        // metto una carta negli scarti
        discardedCards.addCard(deck.getCard());
    }

    /**
     * metodo per pescare una carta dal mazzo
     * 
     * @param user user che ha pescato la carta
     */
    public void drawCard(User user) {
        // aggiungo alle carte dell'utente la prima carta del mazzo
        user.addCard(deck.getCard());
    }

    /**
     * metodo per controllare se la carta giocata è corretta oppure no
     * 
     * @param cardPlayed carta giocata dall'utente
     * @return true: carta giocabile --- false: carta non giocabile
     */
    public boolean checkCardPlayed(Card cardPlayed) {
        return cardPlayed.isPlayable(getColorCardOnTopOfDiscardedCards(), getNumberCardOnTopOfDiscardedCards());
    }

    /**
     * metodo per ottenere il colore della carta in cima al mazzo degli scarti
     * 
     * @return colore della carta in cima al mazzo degli scarti
     */
    public String getColorCardOnTopOfDiscardedCards() {
        return discardedCards.getColorCardOnTop();
    }

    /**
     * metodo per ottenere il numero della carta in cima al mazzo degli scarti
     * 
     * @return numero della carta in cima al mazzo degli scarti
     */
    public int getNumberCardOnTopOfDiscardedCards() {
        return discardedCards.getNumberCardOnTop();
    }
}