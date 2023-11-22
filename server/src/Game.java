import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
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
    private final static String ERROR_UNO = "409";
    private final static String ERROR_EXIT = "500";

    // lista comandi
    private final String SKIP = "skip";
    private final String UNO = "uno";
    private final String PLAY = "play";
    private final String DRAW = "draw";
    private final String QUIT = "quit";
    private final String INIT_DECK = "init";
    private final String START = "start";
    private final static String SORT_BY_NUMBER = "sortByNumber";
    private final static String SORT_BY_COLOR = "sortByColor"; 
    private final static String DISCARDED_CARD = "discarded_card";

    // attributi
    public Deck<Card> deck;
    public Deck<Card> discardedCards;
    public Users users;
    private ServerSocket serverSocket;
    private Message message;
    private Card discaredCard;

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
        users = new Users();

        // creo il mazzo degli scarti
        discardedCards = new Deck<Card>();

        // inizializzo la socket del server
        serverSocket = new ServerSocket(666);
        while(true){
            // metto in ascolto il server
            Socket clientSocket = serverSocket.accept(); // bloccante--> aspetta una connessione

            // Crea un oggetto di comunicazione per gestire la connessione con il client
            Communication communication = new Communication(clientSocket);
            String message_client = communication.listening();
            System.out.println(message_client);
            this.message = new Message();
            this.message.InitMessageFromStringXML(message_client);

            // prendo l'utente con quel username
            User u = this.users.findUserByUsername(this.message.username);
                    
            switch (this.message.command)
            {
                case START:
                    Boolean isAvailable = true;
                    // controllo che non ci siano utenti con quel nome
                    if(this.users.findUserByUsername(this.message.username)!= null)
                    {
                        isAvailable = false;
                        // mando messaggio di errore--> esiste già
                        // inizializzo l'oggetto messaggio
                        this.message = new Message(false, ERROR_USERNAME, "","Username non disponibile" , "");
                        //  invio messaggio di risposta
                        communication.sendMessage(this.message);
                    }
                    if(isAvailable)
                    {
                        // // creo l'utente e lo aggiungo alla lista
                        User u2 = new User(clientSocket.getPort(), clientSocket.getRemoteSocketAddress().toString(), false, false, false, this.message.username);
                        this.users.addUser(u2);   
                        // controllo che sia connesso almeno un altro client
                        StartGameThread sgt = new StartGameThread(u2, communication, this);
                        sgt.start(); 
                    }
                    //  invio messaggio di risposta
                    // communication.sendMessage(this.message);
                    
                    break;
                case QUIT:
                    // // prendo l'utente con quel username
                    // User u = this.users.findUserByUsername(this.message.username);
                    if(u != null)
                        this.message = new Message(u.isUno, CORRECT, u.userName,"Rimozione dalla partita", "" );
                    else
                        this.message = new Message(false, ERROR_EXIT, "","Errore rimozione dalla partita", "" );

                    communication.sendMessage(this.message);
                    break;
                case SKIP:
                    //prendo l'utente dopo l'utente che ha chiesto lo skip
                    User user = users.getProxUser(u);

                    //invio il messaggio
                    this.message = new Message(user.isUno, CORRECT, user.userName,"E' il tuo turno", "");
                    // cambio la socket a cui comunicare
                    communication = new Communication(new Socket(user.IP, user.port));
                    communication.sendMessage(this.message);
                    break;
                case UNO:
                    //controllo se l'utente è a una carta
                    if(u.cards.getSizeDeck() == 1)
                    {
                        this.message = new Message(u.isUno, CORRECT, u.userName, "Complimenti! Sei a una carta", "");
                    }
                    else
                    {
                        //l'utente pesca due carte
                        drawCard(u);
                        drawCard(u);

                        //messaggio con le nuove carte dell'utente
                        String serialized_deck = u.cards.serializeDeck();
                        // inizializzo il messaggio
                        this.message = new Message(u.isUno, ERROR_UNO, u.userName, serialized_deck, "");
                    }

                    //invio il messaggio
                    communication.sendMessage(this.message);
                    break;
                case PLAY:
                    //prendo la carta giocata dall'utente
                    Card cardPlayed = Card.unserialize(this.message.message);

                    Deck deck_tmp = new Deck<>();
                    deck_tmp.addCard(cardPlayed);

                    // String cardSerialized = cardPlayed.serializeToString();
                    // User tmp = this.users.findUserByUsername(this.message.username);
                    // if(tmp.round)
                    // {

                    // }
                    //controllo se la carta giocata è valida
                    if(this.checkCardPlayed(cardPlayed))
                    {
                        //controllo se l'utente ha finito le carte
                        if(u.cards.getSizeDeck() == 0)
                            this.message = new Message(u.isUno, WINNER, u.userName, "Complimenti! Hai vinto",  deck_tmp.serializeDeck());
                        else
                        {
                            //rimuovo la carta all'utente
                            u.removeCard(cardPlayed);

                            //aggiungo la carta agli scarti
                            discardedCards.addCard(cardPlayed);

                            String serialized_deck = u.cards.serializeDeck();
                            // inizializzo il messaggio
                            this.message = new Message(u.isUno, CORRECT, u.userName, serialized_deck, deck_tmp.serializeDeck());
                        }
                        // salvo la carta scartata
                        this.discaredCard = cardPlayed;
                        // invio a tutti i client la carta scartata
                        this.message = new Message(u.isUno, DISCARDED_CARD, u.userName, "", deck_tmp.serializeDeck());
                        this.users.sendToAllClient(this.message);
                    }
                    else
                    {
                        this.message = new Message(u.isUno, ERROR_CARD_PALYED, "","Carta giocata in modo errato", "");
                    }

                    //invio il messaggio
                    communication.sendMessage(this.message);

                    break;
                case DRAW:
                    //l'utente pesca la carta
                    drawCard(u);

                    //se il mazzo è finito
                    if(deck.getSizeDeck() == 0)
                    {
                        //rimescolo il mazzo degli scarti
                        deck.repopulateDeck(discardedCards.deck);
                    }

                    String serialized_deck4 = u.cards.serializeDeck();
                    // inizializzo il messaggio
                    this.message = new Message(u.isUno, CORRECT, u.userName, serialized_deck4, "");
                    //invio le carte all'utente
                    communication.sendMessage(this.message);
                    break;
                case INIT_DECK:

                    //se devo mettere la prima carta degli scarti
                    if(discardedCards.getSizeDeck() == 0)
                    {
                        discardedCards.addCard(deck.getCard()); //metto la carta
                    }

                    // creo il mazzo di carte da dare all'utente
                    Deck<Card> userDeck = this.deck.initUserDeck();
                    // serializzo il mazzo
                    String serialized_deck = userDeck.serializeDeck();
                    // inizializzo il messaggio
                    this.message = new Message(u.isUno, CORRECT, u.userName, serialized_deck, "");
                    communication.sendMessage(this.message);
                    break;
                case SORT_BY_COLOR:
                    //ordino le carte dell'utente per colore
                    u.sortCardsByColor();

                    //invio le carte all'utente
                    String serialized_deck2 = u.cards.serializeDeck();
                    this.message = new Message(u.isUno, CORRECT, u.userName, serialized_deck2, "");
                    communication.sendMessage(this.message);
                    break;   
                case SORT_BY_NUMBER:
                    //ordino le carte dell'utente per numero
                    u.sortCardsByColor();

                    //invio le carte all'utente
                    String serialized_deck3 = u.cards.serializeDeck();
                    this.message = new Message(u.isUno, CORRECT, u.userName, serialized_deck3, "");
                    communication.sendMessage(this.message);
                    break;                                         
            }            
        }
    }

    

    /**
     * metodo per distribuire le carte all'inizio del gioco
     */
    public void dealCards() {
        // per il numero di carte che devo dare a ogni User
        for (int i = 0; i < NUMBER_CARDS_FOR_USER; i++) {
            // per ogni User
            for (User user : users.users) {
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