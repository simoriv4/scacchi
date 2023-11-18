import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class ThreadClient extends Thread {
    // classe thread in modo da ascoltare sempre il client corrispondente

    // lista comandi
    private final String skip = "skip";
    private final String uno = "uno";
    private final String play = "play";
    private final String draw = "draw";
    private final String quit = "quit";
    private final String start = "start";

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

    private Communication communication;
    private User user;
    private Socket clientSocket;
    private Message message;
    private ServerSocket serverSocket;

    public ThreadClient(User u, ServerSocket s) throws UnknownHostException, IOException {
        this.user = u;
        this.serverSocket = s;
        // creo la socket per il client
        this.clientSocket = new Socket(this.user.IP, this.user.port);
        this.communication = new Communication(clientSocket);
    }

    public void run() {
        try {

            while (true) {
                // metto in ascolto il server
                // Socket clientSocket = serverSocket.accept(); // Bloccante, aspetta una
                // connessione
                // System.out.println("Nuova connessione da: " +
                // clientSocket.getInetAddress().getHostAddress());

                // Crea un oggetto di comunicazione per gestire la connessione con il client
                // Communication communication = new Communication(clientSocket);
                String message_client = communication.listening();
                System.out.println(message_client);
                this.message = new Message();
                this.message.InitMessageFromStringXML(message_client);

                // prendo l'utente con quel username
                // User u = this.users.findUserByUsername(this.message.username);

                switch (this.message.command) {
                    // case START:
                    // Boolean isAvailable = true;
                    // // controllo che non ci siano utenti con quel nome
                    // if(this.users.findUserByUsername(this.message.username)!= null)
                    // {
                    // isAvailable = false;
                    // // mando messaggio di errore--> esiste già
                    // // inizializzo l'oggetto messaggio
                    // this.message = new Message(false, ERROR_USERNAME, "","Username non
                    // disponibile" );
                    // }
                    // if(isAvailable)
                    // {
                    // // // creo l'utente e lo aggiungo alla lista
                    // User u2 = new User(clientSocket.getPort(), "127.0.0.1", false, false, false,
                    // this.message.username);
                    // this.users.addUser(u2);
                    // // inizializzo messaggio di risposta
                    // this.message = new Message(u2.isUno, CORRECT, u2.userName,"Username
                    // disponibile");
                    // // avvio il thread per quel client
                    // // ThreadClient tc =new ThreadClient(u);
                    // // tc.start();
                    // }
                    // // invio messaggio di risposta
                    // communication.sendMessage(this.message);

                    // break;
                    case QUIT:
                        // // prendo l'utente con quel username
                        // User u = this.users.findUserByUsername(this.message.username);
                        if (this.user != null)
                            this.message = new Message(this.user.isUno, CORRECT, this.user.userName,
                                    "Rimozione dalla partita");
                        else
                            this.message = new Message(false, ERROR_EXIT, "", "Errore rimozione dalla partita");

                        communication.sendMessage(this.message);

                        break;
                    case SKIP:
                        break;
                    case UNO:
                        break;
                    case PLAY:
                        break;
                    case DRAW:
                        break;
                    case INIT_DECK:
                        break;
                    case SORT_BY_COLOR:
                        break;
                    case SORT_BY_NUMBER:
                        break;
                }

            }
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * metodo che controlla quale operazione è stata richiesta dal client e gestisce
     * la risposta da inviare
     * 
     * @param message
     */
    public void ControlOperation(Message message) {
        switch (message.command) {
            case start:
                System.out.println("entrato");
                break;
            case skip:

                break;
            case quit:

                break;
            case play:
                System.out.println("entrato");
                break;
            case uno:

                break;
            case draw:

                break;
        }
    }

}
