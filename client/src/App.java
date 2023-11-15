import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class App {  
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        // carico le info del server(ip e port)
       // Server s = new Server();
        // homepage homepage;
        // try {
        //     homepage = new homepage();
        //     //gui.setVisible(true);
        // } catch (IOException | ParserConfigurationException | SAXException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        gamepage gp = new gamepage(new User());
    }
}
