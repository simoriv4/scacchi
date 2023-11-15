import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Roules extends JFrame{
    private final String rootName = "client";

    private final String BACKGROUND_IMAGE_PATH = rootName + "\\src\\assets\\backgrounds\\bgY.png";
    private final String ROULES_TITLE_PATH = rootName + "\\src\\assets\\roules-title.png";

    private final String URL_ROULES = "https://www.giochibambiniragazzi.it/giochi-da-tavolo/regole-carte-uno-regolamento-ufficiale-come-si-gioca/";


    private final static Integer WIDTH_ROULES_TITLE_IMAGE = 200;
    private final static Integer HEIGHT_ROULES_TITLE_IMAGE = 50;

    private final static Integer WIDTH_ROULES_LABEL = 520;
    private final static Integer HEIGHT_ROULES_LABEL = 50;

    private BufferedImage backgroundImage;
    private JLabel roules_title;
    private JButton moreOption;

    private JTextArea roules;
    public Roules() throws IOException
    {
        setTitle("Regolamento");
        setSize(800,600);

        // calcolo le coordinate in base alle percentuali dello schermo
        // int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        // int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        
        this.backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
        // inizializzo una label che contiene l'immagine del titolo
        this.roules_title = new JLabel();
        // creo l'oggetto immagine
        ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(ROULES_TITLE_PATH)));
        imageIcon =this.initImageIcon(imageIcon, WIDTH_ROULES_TITLE_IMAGE, HEIGHT_ROULES_TITLE_IMAGE);
        this.roules_title.setIcon(imageIcon);

        JPanel overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // disegno l'immagine di sfondo
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // imposto il layout manager su null per posizionare manualmente i componenti
        overlayPanel.setLayout(null);
        // utilizzo JTextArea per visualizzare il testo del regolamento
        this.roules = new JTextArea(this.initRoules());
        this.roules.setEditable(false); // rendo il JTextArea non modificabile

        this.initButton();
        // imposto le posizioni e le dimensioni dei componenti manualmente
        this.setPositions();

        overlayPanel.add(this.roules);
        overlayPanel.add(this.roules_title);
        overlayPanel.add(this.moreOption);
        

        add(overlayPanel);

        setVisible(true);
    }

    /**
     * funzione che inizializza il bottone
     */
    public void initButton()
    {
        // inizializzo l'oggetto
        this.moreOption = new JButton("Maggiori informazioni");

        this.moreOption.setBackground(new Color(128, 0, 0)); // sfondo rosso
        this.moreOption.setForeground(Color.WHITE); // testo bianco
        this.moreOption.setFont(new Font("Arial", Font.BOLD, 14));
        // assegno l'azione da svolgere quando cliccato
        this.moreOption.addActionListener(e -> {

        try {
            Desktop.getDesktop().browse(new URI(URL_ROULES));
        } catch (IOException | URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        });

    }
    
    public String initRoules()
    {
        String roules = "Obiettivo del gioco:"+
        "\r\nEssere il primo giocatore a scartare tutte le carte."+
        "\r\nSe si gioca a punti, essere il giocatore che, sommando i punti di tutti i round, arriva primo a 500.";
        return roules;
    }

    /**
     * modifica la dimensione della Image Icon
     * @param i
     * @param width
     * @param height
     * @return
     */
    public ImageIcon initImageIcon(ImageIcon i, Integer width, Integer height)
    {
        Image image = i.getImage(); // transform it 
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        i = new ImageIcon(newimg);  // transform it back
        return i;
    }

    /**
     * setto la posizione degli elementi nella finestra
     */
    public void setPositions() {
        this.roules.setBounds((int) (getWidth() * 0.1), (int) (getHeight() * 0.1), WIDTH_ROULES_LABEL, HEIGHT_ROULES_LABEL);
        this.roules_title.setBounds((int) (getWidth() * 0.25), 0, WIDTH_ROULES_TITLE_IMAGE, HEIGHT_ROULES_TITLE_IMAGE);
        this.moreOption.setBounds((int) (getWidth() * 0.25), (int) (getWidth() * 0.2), WIDTH_ROULES_TITLE_IMAGE, HEIGHT_ROULES_TITLE_IMAGE);

    }
}
