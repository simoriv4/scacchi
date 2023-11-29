import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

/**
 * classe che gestisce tutto il gioco (mazzo, scarti e giocatori)
 */
public class Game extends Constants {
    // attributi
    public Deck<Card> deck;
    public Deck<Card> discardedCards;
    public Users users;
    private ServerSocket serverSocket;
    private Message message;
    public Card discardedCard;
    public boolean start;

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
        Socket clientSocket = new Socket();
        // inizializzazione utenti
        while (this.users.users.size() < NUMBER_MAX_CLIENT) {
            // metto in ascolto il server
            clientSocket = serverSocket.accept(); // bloccante--> aspetta una connessione

            // Crea un oggetto di comunicazione per gestire la connessione con il client
            Communication communication = new Communication(clientSocket);
            // Crea un oggetto di comunicazione per gestire la connessione con il client
            String message_client = communication.listening();
            System.out.println(message_client);

            this.message = new Message();
            this.message.InitMessageFromStringXML(message_client);

            // prendo l'utente con quel username
            int pos_user = this.users.findUserByUsername(this.message.username);
            if (this.message.command.equals(START)) {
                Boolean isAvailable = true;
                // controllo che non ci siano utenti con quel nome
                if (pos_user != -1) {
                    isAvailable = false;
                    // mando messaggio di errore--> esiste già
                    // inizializzo l'oggetto messaggio
                    this.message = new Message(false, ERROR_USERNAME, "", "Username non disponibile",
                            " ", " ");
                    // invio messaggio di risposta
                    communication.sendMessage(this.message);
                }
                if (isAvailable) {
                    // // creo l'utente e lo aggiungo alla lista
                    // ROUND TRUE PER DEBUG
                    User user = new User(clientSocket, true, false, false, this.message.username);
                    this.users.addUser(user);
                    // controllo che sia connesso almeno un altro client
                    StartGameThread sgt = new StartGameThread(user, communication, this);
                    sgt.start();
                }
            }
            // il thread ha svolto i controlli della partita--> se la variabile start è ==
            // true significa che si può iniziare
            if (start)
                break;
        }
        // imposto il turno del primo client--> poi verrà gestito nel case SKIP
        // invio il messaggio
        this.message = new Message(this.users.users.get(0).isUno, CORRECT, this.users.users.get(0).userName,
                "E' il tuo turno", " ", " ");
        // cambio la socket a cui comunicare
        Communication communication = new Communication(this.users.users.get(0).socket);
        communication.sendMessage(this.message);

        // lo imposto nella lista di client
        this.users.users.get(0).round = true;

        // per gestire i turni
        int index = 0;
        while (true) {
            // scorro fino a quando finisce il suo turno
            while (this.users.users.get(index).round) {
                // metto in ascolto il server
                communication = new Communication(this.users.users.get(index).socket);
                // Crea un oggetto di comunicazione per gestire la connessione con il client
                String message_client = communication.listening();
                System.out.println(message_client);

                this.message = new Message();
                this.message.InitMessageFromStringXML(message_client);

                // prendo l'utente con quel username
                int pos_user = this.users.findUserByUsername(this.message.username);

                // controllo se nel mazzo degli scarti ci sono già delle carte
                if (discardedCards.getSizeDeck() > 0)
                    // salvo la carta in cima al mazzo degli scarti
                    discardedCard = discardedCards.getCardOnTop();
                else {
                    // se non ci sono carte vuol dire che è appena
                    discardedCards.addCard(this.deck.getCard());
                }
                Boolean isAvailable = true;
                switch (this.message.command) {
                    case QUIT:
                        // // prendo l'utente con quel username
                        // User u = this.users.findUserByUsername(this.message.username);
                        if (pos_user != -1)
                            this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT,
                                    this.users.users.get(pos_user).userName, "Rimozione dalla partita", " ", " ");
                        else
                            this.message = new Message(false, ERROR_EXIT, "", "Errore rimozione dalla partita", " ", " ");

                        communication.sendMessage(this.message);
                        break;
                    case SKIP:
                        this.skip(pos_user);
                        break;
                    case UNO:
                        // controllo se l'utente è a una carta
                        if (this.users.users.get(pos_user).cards.getSizeDeck() == 1) {
                            this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT,
                                    this.users.users.get(pos_user).userName, "Complimenti! Sei a una carta", " ", " ");
                        } else {
                            // l'utente pesca due carte
                            drawCard(this.users.users.get(pos_user));
                            drawCard(this.users.users.get(pos_user));

                            // messaggio con le nuove carte dell'utente
                            String serialized_deck = this.users.users.get(pos_user).cards.serializeDeck();
                            // inizializzo il messaggio
                            this.message = new Message(this.users.users.get(pos_user).isUno, ERROR_UNO,
                                    this.users.users.get(pos_user).userName, serialized_deck, " ", " ");
                        }

                        // invio il messaggio
                        communication.sendMessage(this.message);
                        break;
                    case PLAY:
                        // prendo la carta giocata dall'utente
                        Card cardPlayed = Card.unserialize(this.message.message);

                        // controllo se la carta giocata è valida
                        if (this.checkCardPlayed(cardPlayed)) {

                            // rimuovo la carta all'utente
                            this.users.users.get(pos_user).removeCard(cardPlayed);

                            // aggiungo la carta agli scarti
                            discardedCards.addCard(cardPlayed);
                            // controllo se l'utente ha finito le carte
                            if (this.users.users.get(pos_user).cards.getSizeDeck() == 0) {
                                // mando il messaggio agli altri che hanno perso
                                this.message = new Message(this.users.users.get(pos_user).isUno, LOSE,
                                        this.users.users.get(pos_user).userName,
                                        "Hai perso." + this.users.users.get(pos_user).userName + " ha vinto.", " ", " ");
                                this.users.sendToAllClient(this.message);
                                this.message = new Message(this.users.users.get(pos_user).isUno, WINNER,
                                        this.users.users.get(pos_user).userName, "Complimenti! Hai vinto",
                                        cardPlayed.serializeToString(), " ");
                            }
                            else
                            {
                                // invio a tutti i client la carta scartata
                                Deck<Card> tmp = new Deck<>();
                                tmp.addCard(cardPlayed);

                                //dichiaro una stringa che conterrà le stringhe (username;numero_carte) per ogni utente
                                String str = "";

                                //salvo quante carte hanno i client e le invio
                                for(User u: this.users.users)
                                {
                                    //inserisco nella stringa la stringa (username;numero_carte) per l'utente
                                    str += u.userName + ";" + u.cards.getSizeDeck() + "||";
                                }

                                this.message = new Message(this.users.users.get(pos_user).isUno, DISCARDED_CARD,
                                        this.users.users.get(pos_user).userName, str, tmp.serializeDeck(), str);
                                this.users.sendToAllClient(this.message);

                                // serializzo il mazzo dell'utente per passarglielo nel messaggio
                                String serialized_deck = this.users.users.get(pos_user).cards.serializeDeck();

                                // inizializzo il messaggio
                                this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT, this.users.users.get(pos_user).userName, serialized_deck, tmp.serializeDeck(), str);
                            }
                        } 
                        else
                        {
                            this.message = new Message(this.users.users.get(pos_user).isUno, ERROR_CARD_PALYED, "",
                                    "Carta giocata in modo errato", " ", " ");
                        }

                        // invio il messaggio
                        communication.sendMessage(this.message);

                        break;
                    case DRAW:
                        // l'utente pesca la carta
                        drawCard(this.users.users.get(pos_user));

                        // se il mazzo è finito
                        if (deck.getSizeDeck() == 0) {
                            // rimescolo il mazzo degli scarti
                            deck.repopulateDeck(discardedCards.deck);
                        }
                        String serialized_deck4 = this.users.users.get(pos_user).cards.serializeDeck();
                        String str = "";

                        //salvo quante carte hanno i client e le invio
                        for(User u: this.users.users)
                        {
                            //inserisco nella stringa la stringa (username;numero_carte) per l'utente
                            str += u.userName + ";" + u.cards.getSizeDeck() + "||";
                        }
                        // inizializzo il messaggio
                        this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT,
                                this.users.users.get(pos_user).userName, serialized_deck4, " ", " ");
                        // invio le carte all'utente
                        communication.sendMessage(this.message);
                        // invio a tutti gli altri utenti il numero di carte modificato di questo utente
                        // e il suo username
                        this.message = new Message(this.users.users.get(pos_user).isUno, DRAW_USER,
                                this.users.users.get(pos_user).userName,
                                String.valueOf(this.users.users.get(pos_user).cards.deck.size()), " ", str);
                        this.users.sendToAllClient(this.message);
                        break;
                    case INIT_DECK:
                        // se devo mettere la prima carta degli scarti
                        if (discardedCards.getSizeDeck() == 0) {
                            discardedCards.addCard(deck.getCard()); // metto la carta
                        }
                        // creo il mazzo di carte da dare all'utente
                        this.users.users.get(pos_user).cards = this.deck.initUserDeck();
                        // serializzo il mazzo
                        String serialized_deck = this.users.users.get(pos_user).cards.serializeDeck();
                        // inizializzo il messaggio
                        this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT,
                                this.users.users.get(pos_user).userName, serialized_deck, " ", " ");
                        communication.sendMessage(this.message);
                        this.users.users.get(pos_user).round = false;
                        break;
                    case SORT_BY_COLOR:
                        // ordino le carte dell'utente per colore
                        this.users.users.get(pos_user).sortCardsByColor();

                        // invio le carte all'utente
                        String serialized_deck2 = this.users.users.get(pos_user).cards.serializeDeck();
                        this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT,
                                this.users.users.get(pos_user).userName, serialized_deck2, " ", " ");
                        communication.sendMessage(this.message);
                        break;
                    case SORT_BY_NUMBER:
                        // ordino le carte dell'utente per numero
                        this.users.users.get(pos_user).sortCardsByColor();

                        // invio le carte all'utente
                        String serialized_deck3 = this.users.users.get(pos_user).cards.serializeDeck();
                        this.message = new Message(this.users.users.get(pos_user).isUno, CORRECT,
                                this.users.users.get(pos_user).userName, serialized_deck3, " ", " ");
                        communication.sendMessage(this.message);
                        break;
                }
            }
            index++;
        }
    }

    /**
     * funzione che cambia il turno dei client
     * 
     * @param pos_user
     * @throws UnknownHostException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public void skip(int pos_user)
            throws UnknownHostException, IOException, ParserConfigurationException, TransformerException {
        User user = users.getProxUser(this.users.users.get(pos_user));

        // invio il messaggio
        this.message = new Message(user.isUno, CORRECT, user.userName, "E' il tuo turno", " ", " ");
        // cambio la socket a cui comunicare
        Communication communication = new Communication(user.socket);
        communication.sendMessage(this.message);
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