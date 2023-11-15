import java.awt.List;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * classe che gestisce tutto il gioco (mazzo, scarti e giocatori)
 */
public class Game
{
    //attributi
    public Deck<Card> deck;
    public Deck<Card> discardedCards;

    // client connessi
    private ArrayList<User> users;      // FORSE CREARE UNA CLASSE USERS!!!!!!

    private ServerSocket serverSocket;
    /**
     * costruttore non parametrico del Game
     * 
     * crea il mazzo delle carte e il mazzo degli scarti
     * @throws IOException
     */
    public Game() throws IOException
    {
        //creo il mazzo
        deck = new Deck<Card>();

        users = new ArrayList<>();

        //riempio il mazzo
        deck.fillDeck();

        //creo il mazzo degli scarti
        discardedCards = new Deck<Card>();
        // inizializzo la socket del server
        serverSocket = new ServerSocket(666);
        // metto in ascolto il server
        while (true) {
            Socket clientSocket = serverSocket.accept(); // Bloccante, aspetta una connessione
            //System.out.println("Nuova connessione da: " + clientSocket.getInetAddress().getHostAddress());

            // Crea un oggetto di comunicazione per gestire la connessione con il client
            Communication communication = new Communication(clientSocket);

            // UTENTE TEST!!!
            User tmp = new User();
            tmp.port = 666;

            ThreadClient client1= new ThreadClient(tmp);  // NEW USER() E' TEMPORANEO--> BISOGNA PASSARE IL CLIENT CORRISPONDENTE
             // avvio il thread per la gestione della comunicazione con il client
            client1.start();
        
    }
        
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
