import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Communication {
    
    private Socket socket;
    public Communication(Socket s)
    {
        this.socket = s;
    }

    /**
     * funzione che legge il messaggio inviato dal server e ritorna la stringa XML
     * @return
     * @throws IOException
     */
    public String listening() throws IOException
    {
        String tmp = "";
        BufferedReader message = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        // leggo il messaggio inviato dal server
        tmp = message.readLine();
        return tmp;
    }

    /**
     * funzione che invia al server il messaggio
     * @param m
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public void sendMessage(Message m) throws IOException, ParserConfigurationException, TransformerException
    {
        PrintWriter output = new PrintWriter(this.socket.getOutputStream(), true);
        // invio al server il messaggio serializzato XML
        output.println(m.serialize());
    }

    public void terminateConnection() throws IOException
    {
        this.socket.close();
    }
}
