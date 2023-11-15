import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.*;

public class gamepage extends JFrame {
    private final String rootName = "client";
    private final String COVER_CARD_PATH = rootName + "\\src\\assets\\card-back.png";
    private final String UNO_PATH = rootName + "\\src\\assets\\logo.png";
    private final String BACKGROUND_IMAGE_PATH = rootName + "\\src\\assets\\backgrounds\\bgG.png";
    private final String MUTE_PATH = rootName + "\\src\\assets\\mute.png";

    private final String ROULES_COMBOBOX = "Regolamento";
    private final String QUIT_COMBOBOX = "Esci";
    private final String MUSIC_OFF_COMBOBOX = "Disattiva audio";
    private final String MUSIC_ON_COMBOBOX = "Attiva audio";

    // lista comandi
    private final String skip = "skip";
    private final String uno = "uno";
    private final String play = "play";
    private final String draw = "draw";
    private final String quit = "quit";


    // messaggi di risposta
    private final static String CORRECT = "200";
    private final static String ERROR_USERNAME = "400";
    private final static String ERROR_CARD_PALYED = "406";
    private final static String WINNER = "201";
    private final static String ERROR_SKIP = "409";
    private final static String ERROR_EXIT = "500";

    private final static Integer WIDTH_UNO_IMAGE = 150;
    private final static Integer HEIGHT_UNO_IMAGE = 150;

    private final static Integer WIDTH_CARDS = 100;
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
    private Clip clip;

    private Integer screenWidth;
    private Integer screenHeight;


    private Message message;
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
        // this.socket = new Socket(this.server.IP, this.server.port);
        this.comunication = new Comunication(this.socket);

        // avvio il sottofondo musicale
        this.playMusic();
        // imposto il titolo al frame
        setTitle("gamepage");

        // calcolo le coordinate in base alle percentuali dello schermo
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        // imposto le dimensioni del frame
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
        // this.resizeBufferedImage(this.backgroundImage, (int) (screenWidth * 0.4),
        // (int) (screenHeight * 0.4));

        // inizializzo una label che contiene l'immagine del logo
        this.UNO_Label = this.initImageLabel(this.UNO_Label, UNO_PATH, WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
        this.deck_Label = this.initImageLabel(this.deck_Label, COVER_CARD_PATH, WIDTH_CARDS, HEIGHT_CARDS);

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

        // istanzio un vettore con le opzioni della combobox
        String[] options = { "", QUIT_COMBOBOX, ROULES_COMBOBOX, MUSIC_OFF_COMBOBOX };
        // inizializzo la combobox
        this.initCombobox(options, overlayPanel, screenWidth, screenHeight);

        // imposto le posizioni e le dimensioni dei componenti manualmente
        this.setPositions(screenWidth, screenHeight);

        // imposto la dimensione della label
        this.UNO_Label.setSize(WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
        ;

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

        // this.frame.add(panel);
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
                    // <command>response</command>
                    // <message>200</message>
                    // <username>username</username>
                    // </root_message>
                    // setto il messaggio da inviare
                    message = new Message(user.isUno, draw, user.userName, "");
                    message.InitMessageFromStringXML(response);
                } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });

    }

    public void initCombobox(String[] a, JPanel overlayPanel, Integer screenWidth, Integer screenHeight)
    {
        // coombobox
        String[] options = a;
        this.combobox = new JComboBox<>(options);
        this.combobox.setPreferredSize(new Dimension(200, 40));
        this.combobox.setFont(new Font("Arial", Font.PLAIN, 17));

        this.combobox.setBounds((int) (screenWidth * 0.8), (int) (screenHeight * 0.1), 180, 30);

        // regolamento
        this.combobox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // controllo quale opzione è stata selezionata
                    controlSelection(overlayPanel);
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
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
        this.skipButton.setBounds((int) (screenWidth * 0.60), (int) (screenHeight * 0.6), 120, 30);
        this.discardButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.7), 100, 30);
        this.unoButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.8), 100, 30);

    }

    /**
     * funzione che permette l'avvio del file audio di sottofondo
     */
    public void playMusic() {
        try {
            // creo un oggetto Clip per riprodurre il file audio
            this.clip = AudioSystem.getClip();

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
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initSkipButton() {
        // inizializzo l'oggetto
        this.skipButton = new JButton("Skip");

        this.skipButton.setBackground(new Color(128, 0, 0)); // sfondo rosso
        this.skipButton.setForeground(Color.WHITE); // testo bianco
        this.skipButton.setFont(new Font("Arial", Font.BOLD, 14));

        // assegno l'azione da svolgere quando cliccato
        this.skipButton.addActionListener(e -> {
            try {

                // manda il messaggio 
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è impostato correttamente
                //                                                                              false e mazzo > 1
                //                                                                              true e mazzo == 1
                this.message = new Message(user.isUno, skip, user.userName, user.isUno.toString());
                    this.comunication.sendMessage(message);
            } catch (IOException | ParserConfigurationException | TransformerException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initQuitButton() {
        // inizializzo l'oggetto
        this.quitButton = new JButton("Esci");

        this.quitButton.setBackground(new Color(255, 0, 0)); // sfondo rosso
        this.quitButton.setForeground(Color.WHITE); // testo bianco
        this.quitButton.setFont(new Font("Arial", Font.BOLD, 14));

        // assegno l'azione da svolgere quando cliccato
        this.skipButton.addActionListener(e -> {
            try {

                // manda il messaggio 
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è impostato correttamente
                //                                                                              false e mazzo > 1
                //                                                                              true e mazzo == 1
                this.message = new Message(user.isUno, quit, user.userName, "");
                    this.comunication.sendMessage(message);
            } catch (IOException | ParserConfigurationException | TransformerException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initUnoButton() {
        // inizializzo l'oggetto
        this.unoButton = new JButton("UNO");

        this.unoButton.setBackground(new Color(255, 196, 0)); // sfondo giallo
        this.unoButton.setForeground(Color.WHITE); // testo bianco
        this.unoButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initPlayButton() {
        // inizializzo l'oggetto
        this.playButton = new JButton("Gioca carta");

        this.playButton.setBackground(new Color(0, 174, 46)); // sfondo verde
        this.playButton.setForeground(Color.WHITE); // testo bianco
        this.playButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica
     */
    public void initDrawButton() {
        // inizializzo l'oggetto
        this.discardButton = new JButton("Scarta");

        this.discardButton.setBackground(new Color(0, 162, 174)); // sfondo azzuro
        this.discardButton.setForeground(Color.WHITE); // testo bianco
        this.discardButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * funzione che imposta le immagini nella JLabel
     * 
     * @param label
     * @param imagePath
     * @return una JLabel con l'immagine passata
     * @throws IOException
     */
    public JLabel initImageLabel(JLabel label, String imagePath, Integer width, Integer height) throws IOException {
        // inizializzo una label che contiene l'immagine del logo
        label = new JLabel();
        // creo l'oggetto immagine
        ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(imagePath)));
        imageIcon = this.initImageIcon(imageIcon, width, height);
        label.setIcon(imageIcon);
        return label;
    }

    /**
     * funzione che imposta la dimensione di una BufferedImage
     * 
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
     * 
     * @param i
     * @param width
     * @param height
     * @return
     */
    public ImageIcon initImageIcon(ImageIcon i, Integer width, Integer height) {
        Image image = i.getImage(); // transform it
        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        i = new ImageIcon(newimg); // transform it back
        return i;
    }

    public void controlSelection(JPanel overlauPanel) throws IOException, URISyntaxException {
        String selected = this.combobox.getSelectedItem().toString();
        switch (selected) {
            case ROULES_COMBOBOX:
                // apro il frame con le regole del gioco
                Roules r = new Roules();
                break;

            case MUSIC_OFF_COMBOBOX:
                // fermo la musica
                this.clip.stop();
                // aggiungo al pannello la combobox modificata
                overlauPanel.remove(this.combobox);
                // elimino questa voce e aggiungo alla combobox la voce "attiva audio"
                String[] options = { "", QUIT_COMBOBOX, ROULES_COMBOBOX, MUSIC_ON_COMBOBOX };
                this.initCombobox(options, overlauPanel, this.screenWidth, this.screenHeight);

                // aggiungo al pannello la combobox modificata
                overlauPanel.add(this.combobox);
                remove(overlauPanel);
                add(overlauPanel);
                break;
            case MUSIC_ON_COMBOBOX:
                // avvio la musica
                this.playMusic();
                // aggiungo al pannello la combobox modificata
                overlauPanel.remove(this.combobox);
                // elimino questa voce e aggiungo alla combobox la voce "attiva audio"
                String[] options2 = { "", QUIT_COMBOBOX, ROULES_COMBOBOX, MUSIC_OFF_COMBOBOX };
                this.initCombobox(options2, overlauPanel, this.screenWidth, this.screenHeight);

                // aggiungo al pannello la combobox modificata
                overlauPanel.add(this.combobox);
                remove(overlauPanel);
                add(overlauPanel);
                break;
        }

    }
}
