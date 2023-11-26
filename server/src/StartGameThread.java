import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class StartGameThread extends Thread{
    private final static Long WAITING_TIME = 30000L;

    public final static String CONNECTION_ERROR = "504";
    private final static String CORRECT = "200";


    public Message message;
    public Communication c;
    public User user;
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

            // creo un timer per contare il tempo massimo di attesa
            Timer timer = new Timer();

            // istanzio un timer che invierà un messaggio al client se passa troppo tempo
            MyTimerTask task = new MyTimerTask(this);
            // aspetto 2 minuti
            timer.schedule(task, 2 * 60 * 1000);

            while(this.game.users.users.size()< 2); // PER TEST HO MESSO >2 MA DEVE ESSERE <2
            // se esce dal ciclo vuol dire che si è connesso qualcuno
            timer.cancel();
            this.game.start = true;

            // inoltro il messaggio al client
            // controllo di mandare il messaggio con username disponibile
            if(this.message.command.equals(CORRECT))
            {
                this.c.sendMessage(this.message);

                // inizializzo il mazzo all'untente
                int pos_user = this.game.users.findUserByUsername(this.message.username);
                // se devo mettere la prima carta degli scarti
                if (this.game.discardedCards.getSizeDeck() == 0) {
                    this.game.discardedCards.addCard(this.game.deck.getCard()); // metto la carta
                }
                // creo il mazzo di carte da dare all'utente
                this.game.users.users.get(pos_user).cards = this.game.deck.initUserDeck();
                // serializzo il mazzo
                String serialized_deck = this.game.users.users.get(pos_user).cards.serializeDeck();
                // inizializzo il messaggio
                // passo anche la carta scartata
                this.message = new Message(this.game.users.users.get(pos_user).isUno, CORRECT,
                        this.game.users.users.get(pos_user).userName, serialized_deck, this.game.discardedCards.serializeDeck());
                this.c.sendMessage(this.message);
                this.game.users.users.get(pos_user).round = false;
            }
            
            
            
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
