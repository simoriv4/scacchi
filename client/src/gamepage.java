import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.events.MouseEvent;
import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.awt.*;
import java.awt.event.*;


public class gamepage extends JFrame {
    private final String rootName = "client";
    private final String COVER_CARD_PATH = rootName + "\\src\\assets\\card-back.png";
    private final String UNO_PATH = rootName + "\\src\\assets\\logo.png";
    private final String BACKGROUND_IMAGE_PATH = rootName + "\\src\\assets\\backgrounds\\bgG.png";
    private final String MUTE_PATH = rootName + "\\src\\assets\\mute.png";




    // lista comandi
    private final String skip = "skip";
    private final String uno = "uno";
    private final String play = "play";
    private final String draw = "draw";



    // messaggi di risposta
    private final static String CORRECT = "200";
    private final static String ERROR_USERNAME = "400";
    private final static String ERROR_CARD_PALYED = "406";
    private final static String WINNER = "201";
    private final static String ERROR_SKIP = "409";
    private final static String ERROR_EXIT = "500";

    private final static Integer WIDTH_UNO_IMAGE = 150;
    private final static Integer HEIGHT_UNO_IMAGE = 150;

    private final static Integer WIDTH_CARDS= 100;
    private final static Integer HEIGHT_CARDS = 150;



    public Socket socket;

    private BufferedImage backgroundImage;
    private JButton playButton;
    private JButton quitButton;
    private JButton unoButton;
    private JButton discardButton;
    private JButton skipButton;
    private JLabel UNO_Label;
    private JLabel deck_Label;
    private JLabel discarded_Label;
    private JComboBox<String> combobox;


    private Message message;
    // private Boolean isListening;
    private User user;

    private Server server;

    final Comunication comunication;

    // Streams
    // private BufferedReader inStream;
    // private PrintWriter outStream;

    public gamepage(User user) throws IOException, ParserConfigurationException, SAXException {
        // ininzializzo le informazioni del server
        this.server = new Server();
        // salvo le informazioni dell'utente
        this.user = user;
       //this.socket = new Socket(this.server.IP, this.server.port);
       this.comunication = new Comunication(this.socket);


        // avvio il sottofondo musicale
        this.playMusic();
        // imposto il titolo al frame
        setTitle("gamepage");

        // calcolo le coordinate in base alle percentuali dello schermo
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        // imposto le dimensioni del frame
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
        this.resizeBufferedImage(this.backgroundImage, (int) (screenWidth * 0.4), (int) (screenHeight * 0.4));

        // inizializzo una label che contiene l'immagine del logo
        this.UNO_Label =  this.initImageLabel(this.UNO_Label, UNO_PATH, WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
        this.deck_Label =  this.initImageLabel(this.deck_Label, COVER_CARD_PATH, WIDTH_CARDS, HEIGHT_CARDS);

        // creo un pannello personalizzato per sovrapporre i componenti
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

        // imposto la grafica del bottone
        this.initSkipButton();
        this.initQuitButton();
        this.initPlayButton();
        this.initUnoButton();
        this.initDrawButton();

        
        //menu a tendina
        String[] opzioniMenu = {"","Esci", "Regolamento", "Disattiva/attiva audio"};
        combobox = new JComboBox<>(opzioniMenu);
        combobox.setPreferredSize(new Dimension(200, 40));
        combobox.setFont(new Font("Arial", Font.PLAIN, 17));

        combobox.setBounds((int) (screenWidth * 0.8), (int) (screenHeight * 0.1), 180, 30);

        //regolamento
        combobox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    actionRules();
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // this.initLabel();

        // imposto le posizioni e le dimensioni dei componenti manualmente
        this.setPositions(screenWidth, screenHeight);

        // imposto la dimensione della label
        this.UNO_Label.setSize(WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);;

        // aggiungo i componenti al pannello di sovrapposizione
        overlayPanel.add(this.UNO_Label);
        overlayPanel.add(this.deck_Label);
        overlayPanel.add(this.playButton);
        overlayPanel.add(this.discardButton);
        // overlayPanel.add(this.quitButton);
        overlayPanel.add(this.skipButton);
        overlayPanel.add(this.unoButton);
        overlayPanel.add(combobox);


        add(overlayPanel);


        //this.frame.add(panel);
        // imposta il frame a schermo intero
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        // controllo il mouse click sul mazzo girato--> per pescare la carta
        this.deck_Label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // quando viene cliccato il mazzo richiede al server di pescare la carta

                // invio il messaggio al server
                try {
                    comunication.sendMessage(message);
                    // aspetto la risposta
                    String response = comunication.listening();
                    // <root_message>
                    //     <command>response</command>
                    //     <message>200</message>
                    //     <username>username</username>
                    // </root_message>
                    // setto il messaggio da inviare
                    message = new Message(draw, user.userName, "");
                    message.InitMessageFromStringXML(response);
                } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }



            }
        });
        
    }

    /**
     * setto la posizione degli elementi nella finestra
     * 
     * @param screenWidth
     * @param screenHeight
     */
    public void setPositions(int screenWidth, int screenHeight) {
        this.UNO_Label.setBounds(0, 0, WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
        this.deck_Label.setBounds((int) (screenWidth * 0.4), (int) (screenHeight * 0.4), WIDTH_CARDS, HEIGHT_CARDS);
        this.playButton.setBounds((int) (screenWidth * 0.60), (int) (screenHeight * 0.5), 120, 30);
        this.quitButton.setBounds((int) (screenWidth * 0.9), (int) (screenHeight * 0.1), 100, 30);
        this.skipButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.6), 100, 30);
        this.discardButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.7), 100, 30);
        this.unoButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.8), 100, 30);

    }

    /**
     * funzione che permette l'avvio del file audio di sottofondo
     */
    public void playMusic() {
        try {
            // creo un oggetto Clip per riprodurre il file audio
            Clip clip = AudioSystem.getClip();

            // apro il file audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(rootName + "/audio/UNO_track.wav"));
            clip.open(audioStream);

            // riproduco l'audio in loop
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // avvio l'audio
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * funzione che invia al server un messaggio
     * @param message
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
    public void sendMessage(Message message) throws ParserConfigurationException, TransformerException {
        try {
            OutputStream outputStream = socket.getOutputStream();

            // creo un oggetto OutputStreamWriter per inviare dati al server
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            // invio il messaggio al server
            writer.write(message.serialize());
            writer.flush(); // scrivo e svuoto il buffer

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initSkipButton()
    {
        // inizializzo l'oggetto
        this.skipButton = new JButton("Skip");

        this.skipButton.setBackground(new Color(128, 0, 0)); // sfondo rosso
        this.skipButton.setForeground(Color.WHITE); // testo bianco
        this.skipButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initQuitButton()
    {
        // inizializzo l'oggetto
        this.quitButton = new JButton("Esci");

        this.quitButton.setBackground(new Color(255, 0, 0)); // sfondo rosso
        this.quitButton.setForeground(Color.WHITE); // testo bianco
        this.quitButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initUnoButton()
    {
        // inizializzo l'oggetto
        this.unoButton = new JButton("UNO");

        this.unoButton.setBackground(new Color(255, 196, 0)); // sfondo giallo
        this.unoButton.setForeground(Color.WHITE); // testo bianco
        this.unoButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

        /**
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initPlayButton()
    {
        // inizializzo l'oggetto
        this.playButton = new JButton("Gioca carta");

        this.playButton.setBackground(new Color(0, 174, 46)); // sfondo verde
        this.playButton.setForeground(Color.WHITE); // testo bianco
        this.playButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initDrawButton()
    {
        // inizializzo l'oggetto
        this.discardButton = new JButton("Scarta");

        this.discardButton.setBackground(new Color(0,162,174)); // sfondo azzuro
        this.discardButton.setForeground(Color.WHITE); // testo bianco
        this.discardButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * funzione che imposta le immagini nella JLabel 
     * @param label
     * @param imagePath
     * @return una JLabel con l'immagine passata
     * @throws IOException
     */
    public JLabel initImageLabel(JLabel label, String imagePath, Integer width, Integer height) throws IOException
    {
        // inizializzo una label che contiene l'immagine del logo
        label = new JLabel();
        // creo l'oggetto immagine
        ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(imagePath)));
        imageIcon =this.initImageIcon(imageIcon, width, height);
        label.setIcon(imageIcon);
        return label;
    }



    /**
     * funzione che imposta la dimensione di una BufferedImage
     * @param img
     * @param newW
     * @param newH
     * @return bufferedImage con le nuove dimensioni
     */
    public BufferedImage resizeBufferedImage(BufferedImage img, Integer width, Integer height) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(width, height, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, width, height, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
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

    private void actionRules() throws IOException, URISyntaxException 
    {
        if ("Regolamento".equals(this.combobox.getSelectedItem())) 
        {            
            JFrame f= new JFrame("Regolamento");
            f.setSize(800,600);

            f.setVisible(true);
        }
    }

}
