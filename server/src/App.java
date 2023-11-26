import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

class App extends Constants{

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException
    {       
        Game game = new Game();
    }
}