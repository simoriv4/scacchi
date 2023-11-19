import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    // attributi
    public Deck<Card> deck;
    public Deck<Card> discardedCards;
    private Users users;
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
        users = new Users();

        // creo il mazzo degli scarti
        discardedCards = new Deck<Card>();

        // inizializzo la socket del server
        serverSocket = new ServerSocket(666);
        while(true){
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
                        this.message = new Message(false, ERROR_USERNAME, "","Username non disponibile" );
                    }
                    if(isAvailable)
                    {
                        // // creo l'utente e lo aggiungo alla lista
                        User u2 = new User(clientSocket.getPort(), "127.0.0.1", false, false, false, this.message.username);
                        this.users.addUser(u2);   
                        // inizializzo messaggio di risposta     
                        this.message = new Message(u2.isUno, CORRECT, u2.userName,"Username disponibile");
                        // avvio il thread per quel client
                        // ThreadClient tc =new ThreadClient(u);
                        // tc.start();
                    }
                    //  invio messaggio di risposta
                    communication.sendMessage(this.message);
                    
                    break;
                case QUIT:
                    // // prendo l'utente con quel username
                    // User u = this.users.findUserByUsername(this.message.username);
                    if(u != null)
                        this.message = new Message(u.isUno, CORRECT, u.userName,"Rimozione dalla partita" );
                    else
                        this.message = new Message(false, ERROR_EXIT, "","Errore rimozione dalla partita" );

                    communication.sendMessage(this.message);
                    break;
                case SKIP:
                    //prendo l'utente dopo l'utente che ha chiesto lo skip
                    User user = users.getProxUser(u);

                    //invio il messaggio
                    this.message = new Message(user.isUno, CORRECT, user.userName,"E' il tuo turno" );
                    communication.sendMessage(this.message);
                case UNO:
                    //controllo se l'utente è a una carta
                    if(u.cards.getSizeDeck() == 1)
                    {
                        this.message = new Message(u.isUno, CORRECT, "", "");
                    }
                    else
                    {
                        //l'utente pesca due carte
                        drawCard(u);
                        drawCard(u);

                        //messaggio con le nuove carte dell'utente
                        this.message = new Message(u.isUno, ERROR_UNO, "", u.cards.toString());
                    }

                    //invio il messaggio
                    communication.sendMessage(this.message);
                case PLAY:
                    /*
                     * DA GUARDARE
                     */

                    //prendo la carta giocata dall'utente
                    Card cardPlayed = new Card(this.message.message);

                    //controllo se la carta giocata è valida
                    if(this.checkCardPlayed(cardPlayed))
                    {
                        //controllo se l'utente ha finito le carte
                        if(u.cards.getSizeDeck() == 0)
                            this.message = new Message(u.isUno, WINNER, u.userName, "Complimenti! Hai vinto");
                        else
                            this.message = new Message(u.isUno, CORRECT, "", "Carta giocata correttamente");
                    }
                    else
                    {
                        this.message = new Message(u.isUno, ERROR_CARD_PALYED, "","Carta giocata in modo errato" );
                    }

                    //invio il messaggio
                    communication.sendMessage(this.message);
                case DRAW:
                    //l'utente pesca la carta
                    drawCard(u);

                    //se il mazzo è finito
                    if(deck.getSizeDeck() == 0)
                    {
                        //rimescolo il mazzo degli scarti
                        deck.repopulateDeck(discardedCards.deck);
                    }

                    //invio le carte all'utente
                    this.message = new Message(u.isUno, CORRECT, "", u.cards.toString());   //RIGUARDARE..........
                    communication.sendMessage(this.message);

                //case INIT_DECK:
                    //break;
                case SORT_BY_COLOR:
                    //ordino le carte dell'utente per numero
                    u.sortCardsByColor();

                    //invio le carte all'utente
                    this.message = new Message(u.isUno, CORRECT, "", u.cards.toString());   //RIGUARDARE..........
                    communication.sendMessage(this.message);       
                case SORT_BY_NUMBER:
                    //ordino le carte dell'utente per numero
                    u.sortCardsByNumber();

                    //invio le carte all'utente
                    this.message = new Message(u.isUno, CORRECT, "", u.cards.toString());   //RIGUARDARE..........
                    communication.sendMessage(this.message);                                              
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