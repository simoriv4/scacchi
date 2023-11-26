import java.io.IOException;
import java.net.InetSocketAddress;
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
    private final static String LOSE = "lose";
    private final static String DRAW_USER = "draw_user";
    

    // attributi
    public Deck<Card> deck;
    public Deck<Card> discardedCards;
    public Users users;
    private ServerSocket serverSocket;
    private Message message;
    private Card discardedCard;

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
        int index =0;
        Socket clientSocket = new Socket();
        while(true){
            // azzero l'indice degli utenti
            if(index == (this.users.users.size()-1))
                index = 0;
            if(this.users.users.size() != 0)
                // prendo la socket con cui comunicare
                clientSocket = this.users.users.get(index).socket;
            else
                // metto in ascolto il server
                clientSocket = serverSocket.accept(); // bloccante--> aspetta una connessione            

            // Crea un oggetto di comunicazione per gestire la connessione con il client
            Communication communication = new Communication(clientSocket);
            do{
                // metto in ascolto il server

                // Crea un oggetto di comunicazione per gestire la connessione con il client
                String message_client = communication.listening();
                System.out.println(message_client);

                this.message = new Message();
                this.message.InitMessageFromStringXML(message_client);

                // prendo l'utente con quel username
                int pos_user = this.users.findUserByUsername(this.message.username);

                // controllo se nel mazzo degli scarti ci sono già delle carte
                if(discardedCards.getSizeDeck() > 0)
                // salvo la carta in cima al mazzo degli scarti
                    discardedCard = discardedCards.getCardOnTop();
                else
                {
                    // se non ci sono carte vuol dire che è appena 
                    discardedCards.addCard(this.deck.getCard());
                }
                Boolean isAvailable = true;      
                switch (this.message.command)
                {
                    case START:
                        // isAvailable = true;
                        // controllo che non ci siano utenti con quel nome
                        if(pos_user != -1)
                        {
                            isAvailable = false;
                            // mando messaggio di errore--> esiste già
                            // inizializzo l'oggetto messaggio
                            this.message = new Message(false, ERROR_USERNAME, "","Username non disponibile" , discardedCard.serializeToString());
                            //  invio messaggio di risposta
                            communication.sendMessage(this.message);
                        }
                        if(isAvailable)
                        {
                            // // creo l'utente e lo aggiungo alla lista
                            //ROUND TRUE PER DEBUG
                            User user = new User(clientSocket, true, false, false, this.message.username);
                            this.users.addUser(user);   
                            // controllo che sia connesso almeno un altro client
                            StartGameThread sgt = new StartGameThread(user, communication, this);
                            sgt.start();
                            // this.message = new Message(user.isUno, CORRECT, user.userName,"Username disponibile", "");
                        }
                        //  invio messaggio di risposta
                        // communication.sendMessage(this.message);
                        
                        break;
                    case QUIT:
                        // // prendo l'utente con quel username
                        // User u = this.users.findUserByUsername(this.message.username);
                        if(pos_user != -1)
                            this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT, this.users.users.get(pos_user).userName,"Rimozione dalla partita", "" );
                        else
                            this.message = new Message(false, ERROR_EXIT, "","Errore rimozione dalla partita", "" );

                        communication.sendMessage(this.message);
                        break;
                    case SKIP:
                        //prendo l'utente dopo l'utente che ha chiesto lo skip
                        User user = users.getProxUser(this.users.users.get(pos_user));

                        //invio il messaggio
                        this.message = new Message(user.isUno, CORRECT, user.userName,"E' il tuo turno", "");
                        // cambio la socket a cui comunicare
                        communication = new Communication(new Socket(user.IP, user.port));
                        communication.sendMessage(this.message);
                        break;
                    case UNO:
                        //controllo se l'utente è a una carta
                        if(this.users.users.get(pos_user).cards.getSizeDeck() == 1)
                        {
                            this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT, this.users.users.get(pos_user).userName, "Complimenti! Sei a una carta", "");
                        }
                        else
                        {
                            //l'utente pesca due carte
                            drawCard(this.users.users.get(pos_user));
                            drawCard(this.users.users.get(pos_user));

                            //messaggio con le nuove carte dell'utente
                            String serialized_deck = this.users.users.get(pos_user).cards.serializeDeck();
                            // inizializzo il messaggio
                            this.message = new Message(this.users.users.get(pos_user).isUno, ERROR_UNO, this.users.users.get(pos_user).userName, serialized_deck, "");
                        }

                        //invio il messaggio
                        communication.sendMessage(this.message);
                        break;
                    case PLAY:
                        //prendo la carta giocata dall'utente
                        Card cardPlayed = Card.unserialize(this.message.message);

                        // String cardSerialized = cardPlayed.serializeToString();
                        // User tmp = this.users.findUserByUsername(this.message.username);
                        // if(tmp.round)
                        // {

                        // }
                        //controllo se la carta giocata è valida
                        if(this.checkCardPlayed(cardPlayed))
                        {
                            
                            //rimuovo la carta all'utente
                            this.users.users.get(pos_user).removeCard(cardPlayed);

                            //aggiungo la carta agli scarti
                            discardedCards.addCard(cardPlayed);
                                //controllo se l'utente ha finito le carte
                            if(this.users.users.get(pos_user).cards.getSizeDeck() == 0)
                            {
                                // mado il messaggio agli altri che hanno perso
                                this.message = new Message(this.users.users.get(pos_user).isUno, LOSE, this.users.users.get(pos_user).userName, "Hai perso." + this.users.users.get(pos_user).userName + " ha vinto.","" );
                                this.users.sendToAllClient(this.message);
                                this.message = new Message(this.users.users.get(pos_user).isUno, WINNER, this.users.users.get(pos_user).userName, "Complimenti! Hai vinto",  cardPlayed.serializeToString());
                            }
                            else
                            {                                
                                // invio a tutti i client la carta scartata
                                this.message = new Message(this.users.users.get(pos_user).isUno, DISCARDED_CARD, this.users.users.get(pos_user).userName, "", cardPlayed.serializeToString());
                                this.users.sendToAllClient(this.message);

                                // serializzo il mazzo dell'utente per passarglielo nel messaggio
                                String serialized_deck = this.users.users.get(pos_user).cards.serializeDeck();

                                // inizializzo il messaggio
                                this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT, this.users.users.get(pos_user).userName, serialized_deck, cardPlayed.serializeToString());
                            }
                        }
                        else
                        {
                            this.message = new Message(this.users.users.get(pos_user).isUno, ERROR_CARD_PALYED, "","Carta giocata in modo errato", "");
                        }

                        //invio il messaggio
                        communication.sendMessage(this.message);

                        break;
                    case DRAW:
                        //l'utente pesca la carta
                        drawCard(this.users.users.get(pos_user));

                        //se il mazzo è finito
                        if(deck.getSizeDeck() == 0)
                        {
                            //rimescolo il mazzo degli scarti
                            deck.repopulateDeck(discardedCards.deck);
                        }
                        String serialized_deck4 = this.users.users.get(pos_user).cards.serializeDeck();
                        // inizializzo il messaggio
                        this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT, this.users.users.get(pos_user).userName, serialized_deck4, "");
                        //invio le carte all'utente
                        communication.sendMessage(this.message);
                        // invio a tutti gli altri utenti il numero di carte modificato di questo utente e il suo username
                        this.message = new Message(this.users.users.get(pos_user).isUno, DRAW_USER, this.users.users.get(pos_user).userName, String.valueOf(this.users.users.get(pos_user).cards.deck.size()), "");
                        this.users.sendToAllClient(this.message);
                        break;
                    case INIT_DECK:
                        //se devo mettere la prima carta degli scarti
                        if(discardedCards.getSizeDeck() == 0)
                        {
                            discardedCards.addCard(deck.getCard()); //metto la carta
                        }
                        this.users.users.get(pos_user).cards =
                        // creo il mazzo di carte da dare all'utente
                        this.users.users.get(pos_user).cards = this.deck.initUserDeck();
                        // serializzo il mazzo
                        String serialized_deck = this.users.users.get(pos_user).cards.serializeDeck();
                        // inizializzo il messaggio
                        this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT, this.users.users.get(pos_user).userName, serialized_deck, "");
                        communication.sendMessage(this.message);
                        break;
                    case SORT_BY_COLOR:
                        //ordino le carte dell'utente per colore
                        this.users.users.get(pos_user).sortCardsByColor();

                        //invio le carte all'utente
                        String serialized_deck2 = this.users.users.get(pos_user).cards.serializeDeck();
                        this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT, this.users.users.get(pos_user).userName, serialized_deck2, "");
                        communication.sendMessage(this.message);
                        break;   
                    case SORT_BY_NUMBER:
                        //ordino le carte dell'utente per numero
                        this.users.users.get(pos_user).sortCardsByColor();

                        //invio le carte all'utente
                        String serialized_deck3 = this.users.users.get(pos_user).cards.serializeDeck();
                        this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT, this.users.users.get(pos_user).userName, serialized_deck3, "");
                        communication.sendMessage(this.message);
                        break;                                         
                } 
                if(isAvailable)
                    break;
            }while(this.users.users.get(index).round); // scorro fino a quando finisce il suo turno          
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