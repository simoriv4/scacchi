import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;

class LabelAdapter extends MouseAdapter {
    private MyLabel ml;
    private gamepage gp;
    private Boolean isDeck;

    public LabelAdapter(MyLabel ml, gamepage gp, Boolean isDeck) {
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
            // Message message = new Message(user.isUno, gp.DRAW, user.userName, "", "");
            // communication.sendMessage(message);
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