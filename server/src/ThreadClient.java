import java.io.IOException;
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

    private Communication communication;
    private User user;

    public ThreadClient(User u) throws UnknownHostException, IOException {
        this.user = u;
        // creo la socket per il client
        Socket socket = new Socket(this.user.IP, this.user.port);
        this.communication = new Communication(socket);
    }

    public void run() {
        try {
            while (true) {
                // faccio rimanere in ascolto del messaggio dal client
                String message = this.communication.listening();
                Message m = new Message();
                // unsrializzo la stringa XML nell'oggetto messaggio
                m.InitMessageFromStringXML(message);
                this.ControlOperation(m);
                
                
                Message replyMessage = new Message(false, "replyCommand", "server", "Reply message");
                this.communication.sendMessage(replyMessage);
                
                // if (message.equals("quit")) {
                //     break;
                // }
            }
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            e.printStackTrace();
        } finally {
            try {
                // Chiudi la connessione alla fine
                communication.terminateConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
