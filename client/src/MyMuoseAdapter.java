import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

class MyMuoseAdapter extends MouseAdapter {
    private MyLabel ml;
    private gamepage gp;
    private Boolean isDeck;

    public MyMuoseAdapter(MyLabel ml, gamepage gp, Boolean isDeck) {
        this.ml = ml;
        this.gp = gp;
        this.isDeck = isDeck;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!isDeck)
        {
            System.out.println(ml.index);
            this.gp.selected_card = ml.index;
        }
        else{
            try {
                Message message = new Message(this.gp.user.isUno, gp.DRAW, this.gp.user.userName, "", "", "");
                this.gp.communication.sendMessage(message);
                // aspetto la risposta
                String reply = this.gp.communication.listening();
                // creo il panel che conterrà il nuovo mazzo di carte
                this.gp.overlayPanel = this.gp.initDeck(this.gp.overlayPanel, reply);
                this.gp.remove(this.gp.overlayPanel);
                // aggiungo il nuovo deckPanel
                this.gp.add(this.gp.overlayPanel);
            } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ml.setBackground(Color.CYAN);
        
    }
    @Override
    public void mousePressed(MouseEvent e)
    {
        ml.setBorder(new CompoundBorder( // sets two borders
        BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK), // outer border
        BorderFactory.createEmptyBorder(1, 1, 1, 1))); // inner invisible border as the margin 
   }
    @Override
    public void mouseReleased(MouseEvent e)
    {
        ml.setBorder(new CompoundBorder( // sets two borders
        BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK), // outer border
        BorderFactory.createEmptyBorder(0, 0, 0, 0))); // inner invisible border as the margin );
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ml.setBackground(Color.LIGHT_GRAY);
        
    }
}