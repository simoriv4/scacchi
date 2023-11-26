import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class User
{
    // bolleana che permette di capire se sia il turno di questo utente
    public Boolean round;
    // booleana che permette di capire se ha vinto
    public Boolean win;
    // booleana che permette di capire se ha detto UNO
    public Boolean isUno;

    public String userName;
    public Socket socket;

    public Deck<Card> cards;

    /**
     * costruttore parametrico
     * @param round
     * @param win
     * @param isUno
     * @param userName
     * @throws IOException
     * @throws UnknownHostException
     */
    public User(Socket clientSocket, Boolean round, Boolean win, Boolean isUno, String userName) throws UnknownHostException, IOException {
        this.round = round;
        this.win = win;
        this.userName = userName;
        this.isUno = isUno;
        // inizializzo la socket corrispondente all'untente
        this.socket = clientSocket;

        cards = new Deck<Card>();
    }
    /**
     * costruttore non parametrico
     */
    public User() {
        this.round = false;
        this.win = false;
        this.isUno = false;
        this.userName = "userName";
    }

    /**
     * metodo per aggiungere una carta alle carte dell'utente
     * 
     * @param card carta da aggiungere alle carte dell'utente
     */
    public void addCard(Card card)
    {
        cards.addCard(card);
    }

    /**
     * metodo per rimuovere una carta dal mazzo dell'utente
     * @param card carta da eliminare dal mazzo dell'utente
     */
    public void removeCard(Card card)
    {
        cards.deck.remove(card);
    }

    /**
     * metodo per ordinare le carte per colore
     */
    public void sortCardsByColor()
    {
        cards.sortCardsByColor();
    }

    /**
     * metodo per ordinare le carte per numero
     */
    public void sortCardsByNumber()
    {
        cards.sortCardsByNumber();
    }
    /**
     * funzione per inviare il messaggio all'utente
     * @param m
     * @throws UnknownHostException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public void sendMessage(Message m) throws UnknownHostException, IOException, ParserConfigurationException, TransformerException
    {
        // avvio la comunicazione
        Communication c = new Communication(socket);
        c.sendMessage(m);
    }
}