import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Users {
    // classe per gestire la lista di utenti
    public ArrayList<User> users;

    public Users() {
        this.users = new ArrayList<>();
    }

    /**
     * funzione che cerca nella lista di utenti, l'utente con l'username uguale a
     * quello passato
     * 
     * @param username
     * @return l'oggetto utente
     */
    public int findUserByUsername(String username) {
        // scorro la lista
        for (User u : users) {
            // eseguo il controllo dell'username
            if (u.userName.equals(username))
                return users.indexOf(u);
        }
        return -1;
    }

    /**
     * funzione che aggiunge un utente alla lista
     */
    public void addUser(User u) {
        this.users.add(u);
    }

    /**
     * funzione che rimuove un utente dalla lista passandogli l'oggetto utente
     */
    public void removeUser(User u) {
        this.users.remove(u);
    }

    /**
     * metodo per ottenere l'utente dopo l'utente passato come parametro
     * 
     * @param user utente attuale
     * @return utente dopo l'utente passato come parametro
     */
    public User getProxUser(User user) {
        // ritorno l'utente dopo l'utente passato come parametro
        return users.get(users.indexOf(user) + 1);
    }

    /**
     * funzione che scorre tutti gli utenti e invia il messaggio
     * @throws TransformerException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws UnknownHostException
     */
    public void sendToAllClient(Message m) throws UnknownHostException, IOException, ParserConfigurationException, TransformerException {
        for (User u : this.users) {
            // invio il messaggio ad ogni utente --> tranne quello di cui è il turno--> è già stato inviato
            if(!u.round)
                u.sendMessage(m);
        }

    }
}