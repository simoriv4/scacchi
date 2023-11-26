import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class MyTimerTask extends TimerTask {
    private StartGameThread sgt;

    public MyTimerTask(StartGameThread sgt)
    {
        this.sgt = sgt;
    }

    @Override
    public void run() {
        // inizializzo il messaggio
        this.sgt.message = new Message(false, StartGameThread.CONNECTION_ERROR, "","Tempo di attesa eccessivo.", " ", " ");
        try {
            this.sgt.c.sendMessage(this.sgt.message);
            // elimino l'utente che avevo aggiunto alla lista di utenti nella classe game
           int pos = this.sgt.game.users.findUserByUsername(this.sgt.user.userName);
           this.sgt.game.users.removeUser(this.sgt.game.users.users.get(pos));
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
}
