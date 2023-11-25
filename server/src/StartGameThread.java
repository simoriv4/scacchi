import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class StartGameThread extends Thread{
    private final static Long WAITING_TIME = 30000L;

    private final static String CONNECTION_ERROR = "504";
    private final static String CORRECT = "200";


    private Message message;
    private Communication c;
    private User user;
    public Game game;
    /**
     * costruttore parametrico
     * @param u
     * @param c
     * @param u
     */
    public StartGameThread(User u, Communication c, Game g)
    {
        this.user = u;
        this.message = new Message();
        this.game = g;
        this.c = c;
    }
    /**
     * funzione per eseguire il thread
     */
    public void run()
    {
        try {
            // attendo 30 s e poi invio il messaggio
            // prima controllo che la lunghezza di users sia > 0
            // se no riprendo i 30 s
            // se il contatore supera il 2 invio messaggio di errore di connessione
            this.message = new Message(user.isUno, CORRECT, user.userName,"Username disponibile", "");

            int i = 0;
            while(this.game.users.users.size()< 2) // PER TEST HO MESSO >2 MA DEVE ESSERE <2
            {
                // creo un timer per contare il tempo massimo di attesa
                Timer timer = new Timer();

                // specifico l'istruzione da eseguire
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        message = new Message(false, CONNECTION_ERROR, "","Tempo di attesa eccessivo.", "");
                    }
                };

                
                timer.schedule(task, 2 * 60 * 1000);
            }
            // inoltro il messaggio al client
            this.c.sendMessage(this.message);


        } catch (IOException | ParserConfigurationException | TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
