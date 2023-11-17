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
import java.net.URISyntaxException;
import java.awt.event.*;

public class gamepage extends JFrame {
    // private final String rootName = "client";
    private final String COVER_CARD_PATH = "src\\assets\\card-back.png";
    private final String UNO_PATH = "src\\assets\\logo.png";
    private final String BACKGROUND_IMAGE_PATH = "src\\assets\\backgrounds\\bgG.png";
    private final String MUTE_PATH = "src\\assets\\mute.png";
    private final String MUSIC_PATH = "audio\\UNO_track.wav";

    // percorsi carte
    private final String REVERSE_B_PATH = "src\\assets\\cards-front\\_B.png";
    private final String REVERSE_Y_PATH = "src\\assets\\cards-front\\_Y.png";
    private final String REVERSE_G_PATH = "src\\assets\\cards-front\\_G.png";
    private final String REVERSE_R_PATH = "src\\assets\\cards-front\\_R.png";
    private final String ZERO_B_PATH = "src\\assets\\cards-front\\0B.png";
    private final String ZERO_Y_PATH = "src\\assets\\cards-front\\0Y.png";
    private final String ZERO_G_PATH = "src\\assets\\cards-front\\0G.png";
    private final String ZERO_R_PATH = "src\\assets\\cards-front\\0R.png";
    private final String ONE_B_PATH = "src\\assets\\cards-front\\1B.png";
    private final String ONE_Y_PATH = "src\\assets\\cards-front\\1Y.png";
    private final String ONE_G_PATH = "src\\assets\\cards-front\\1G.png";
    private final String ONE_R_PATH = "src\\assets\\cards-front\\1R.png";
    private final String TWO_B_PATH = "src\\assets\\cards-front\\2B.png";
    private final String TWO_Y_PATH = "src\\assets\\cards-front\\2Y.png";
    private final String TWO_G_PATH = "src\\assets\\cards-front\\2G.png";
    private final String TWO_R_PATH = "src\\assets\\cards-front\\2R.png";
    private final String THREE_B_PATH = "src\\assets\\cards-front\\3B.png";
    private final String THREE_Y_PATH = "src\\assets\\cards-front\\3Y.png";
    private final String THREE_G_PATH = "src\\assets\\cards-front\\3G.png";
    private final String THREE_R_PATH = "src\\assets\\cards-front\\3R.png";
    private final String FOUR_B_PATH = "src\\assets\\cards-front\\4B.png";
    private final String FOUR_Y_PATH = "src\\assets\\cards-front\\4Y.png";
    private final String FOUR_G_PATH = "src\\assets\\cards-front\\4G.png";
    private final String FOUR_R_PATH = "src\\assets\\cards-front\\4R.png";
    private final String FIVE_B_PATH = "src\\assets\\cards-front\\5B.png";
    private final String FIVE_Y_PATH = "src\\assets\\cards-front\\5Y.png";
    private final String FIVE_G_PATH = "src\\assets\\cards-front\\5G.png";
    private final String FIVE_R_PATH = "src\\assets\\cards-front\\5R.png";
    private final String SIX_B_PATH = "src\\assets\\cards-front\\6B.png";
    private final String SIX_Y_PATH = "src\\assets\\cards-front\\6Y.png";
    private final String SIX_G_PATH = "src\\assets\\cards-front\\6G.png";
    private final String SIX_R_PATH = "src\\assets\\cards-front\\6R.png";
    private final String SEVEN_B_PATH = "src\\assets\\cards-front\\7B.png";
    private final String SEVEN_Y_PATH = "src\\assets\\cards-front\\7Y.png";
    private final String SEVEN_G_PATH = "src\\assets\\cards-front\\7G.png";
    private final String SEVEN_R_PATH = "src\\assets\\cards-front\\7R.png";
    private final String EIGHT_B_PATH = "src\\assets\\cards-front\\8B.png";
    private final String EIGHT_Y_PATH = "src\\assets\\cards-front\\8Y.png";
    private final String EIGHT_G_PATH = "src\\assets\\cards-front\\8G.png";
    private final String EIGHT_R_PATH = "src\\assets\\cards-front\\8R.png";
    private final String NINE_B_PATH = "src\\assets\\cards-front\\9B.png";
    private final String NINE_Y_PATH = "src\\assets\\cards-front\\9Y.png";
    private final String NINE_G_PATH = "src\\assets\\cards-front\\9G.png";
    private final String NINE_R_PATH = "src\\assets\\cards-front\\9R.png";
    private final String ADD_TWO_CARDS_B_PATH = "src\\assets\\cards-front\\D2B.png";
    private final String ADD_TWO_CARDS_Y_PATH = "src\\assets\\cards-front\\D2Y.png";
    private final String ADD_TWO_CARDS_G_PATH = "src\\assets\\cards-front\\D2G.png";
    private final String ADD_TWO_CARDS_R_PATH = "src\\assets\\cards-front\\D2R.png";
    private final String SKIP_B_PATH = "src\\assets\\cards-front\\skipB.png";
    private final String SKIP_Y_PATH = "src\\assets\\cards-front\\skipY.png";
    private final String SKIP_G_PATH = "src\\assets\\cards-front\\skipG.png";
    private final String SKIP_R_PATH = "src\\assets\\cards-front\\skipR.png";
    private final String ADD_FOUR_CARDS_PATH = "src\\assets\\cards-front\\D4W.png";
    private final String CHANGE_COLOR_PATH = "src\\assets\\cards-front\\W.png";




    private final String ROULES_COMBOBOX = "Regolamento";
    private final String QUIT_COMBOBOX = "Esci";
    private final String MUSIC_OFF_COMBOBOX = "Disattiva audio";
    private final String MUSIC_ON_COMBOBOX = "Attiva audio";

    // lista comandi
    private final String SKIP = "skip";
    private final String UNO = "uno";
    private final String PLAY = "play";
    private final String DRAW = "draw";
    private final String QUIT = "quit";
    private final String INIT_DECK = "init";
    private final String START = "start";
    private final static String SORT_BY_NUMBER = "sortByNumber";
    private final static String SORT_BY_COLOR = "sortByColor"; 


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
    private JButton unoButton;
    private JButton skipButton;
    private JButton sortbyNumberButton;
    private JButton sortbyColorButton;
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

    final Communication communication;

    // Streams
    // private BufferedReader inStream;
    // private PrintWriter outStream;

    public gamepage(User user) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        // ininzializzo le informazioni del server
        this.server = new Server();
        // salvo le informazioni dell'utente
        this.user = user;
        this.socket = new Socket(this.server.IP, this.server.port);
        this.communication = new Communication(this.socket);
        
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

        // unserializzo la carta
        // Deck d = new Deck<>();
        // d.unserializeDeck(card);
        

        // controllo il tipo di carta--> in base a quello cerco l'immagine corretta
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

        // richiedo carte
        overlayPanel= this.initDeck(overlayPanel);

        // imposto la grafica del bottone
        this.initSkipButton();
        this.initPlayButton();
        this.initUnoButton();
        this.initSortByColorButton();
        this.initSortByNumberButton();


        // istanzio un vettore con le opzioni della combobox
        String[] options = { "", QUIT_COMBOBOX, ROULES_COMBOBOX, MUSIC_OFF_COMBOBOX };
        // inizializzo la combobox
        this.initCombobox(options, overlayPanel, screenWidth, screenHeight);

        // imposto le posizioni e le dimensioni dei componenti manualmente
        this.setPositions(screenWidth, screenHeight);

        // imposto la dimensione della label
        this.UNO_Label.setSize(WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
        
        // richiedo al server quale sia la prima carta da mostrare tra quelle scartate

        // aggiungo i componenti al pannello di sovrapposizione
        overlayPanel.add(this.UNO_Label);
        overlayPanel.add(this.deck_Label);
        overlayPanel.add(this.playButton);
        // overlayPanel.add(this.quitButton);
        overlayPanel.add(this.skipButton);
        overlayPanel.add(this.sortbyColorButton);
        overlayPanel.add(this.sortbyNumberButton);
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
                    message = new Message(user.isUno, DRAW, user.userName, "");
                    communication.sendMessage(message);
                    // aspetto la risposta
                    String response = communication.listening();
                    // <root_message>
                    // <command>response</command>
                    // <message>200</message>
                    // <username>username</username>
                    // </root_message>
                    // setto il messaggio da inviare
                    message.InitMessageFromStringXML(response);
                    Deck d = new Deck<>();
                    // unserializzo il deck --> sarà un deck di length = 1--> corrisponde alla carta da pescare
                    d.unserializeDeck(message.message);
                    // aggiungo la carta al mazzo dello user
                    user.deck.deck.add((Card) d.deck.get(0));                    
                } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        

    }

    private JPanel initDeck(JPanel overlayPanel) throws IOException, ParserConfigurationException, TransformerException, SAXException {
        message = new Message(user.isUno, INIT_DECK, user.userName, "");
        this.communication.sendMessage(message);
        // attendo la risposta
        String reply = this.communication.listening();
        // unserializzo il messaggio
        message.InitMessageFromStringXML(reply);
        // unserializzo il mazzo
        this.user.deck.unserializeDeck(reply);

        int x = (int) (screenWidth * 0.4);
        int y = (int) (screenHeight * 0.9);
        // scorro le carte e le aggiungo all'overlay panel
        for(int i= 0; i<this.user.deck.getSizeDeck(); i++)
        {
            // switch (this.user.deck.deck.get(i).type) {
            //     case value:
                    
            //         break;
            
            //     default:
            //         break;
            // }
            JLabel card= new JLabel();
            card = this.initImageLabel(card, COVER_CARD_PATH, WIDTH_CARDS, HEIGHT_CARDS);
            // setto la posizione
            // la prima carta è sempre al centro--> poi si mette a x - n   e   x + n
            // controllo quante carte
            this.deck_Label.setBounds(x, y, WIDTH_CARDS, HEIGHT_CARDS);
            // controllo il mouse click sul mazzo girato--> per pescare la carta
            card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // quando viene cliccato il mazzo richiede al server di pescare la carta

                // invio il messaggio al server
                try {
                    message = new Message(user.isUno, DRAW, user.userName, "");
                    communication.sendMessage(message);
                    // aspetto la risposta
                    String response = communication.listening();
                    // <root_message>
                    // <command>response</command>
                    // <message>200</message>
                    // <username>username</username>
                    // </root_message>
                    // setto il messaggio da inviare
                    message.InitMessageFromStringXML(response);
                    Deck d = new Deck<>();
                    // unserializzo il deck --> sarà un deck di length = 1--> corrisponde alla carta da pescare
                    d.unserializeDeck(message.message);
                    // aggiungo la carta al mazzo dello user
                    user.deck.deck.add((Card) d.deck.get(0));                    
                } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
            // aggiungi la carta al panel
            overlayPanel.add(card);
        }
        return overlayPanel;
        
    }

    /**
     * inizializza la combobox con le opzioni disponibili
     * @param a
     * @param overlayPanel
     * @param screenWidth
     * @param screenHeight
     */
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
                } catch (IOException | URISyntaxException | SAXException ex) {
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
        this.playButton.setBounds((int) (screenWidth * 0.30), (int) (screenHeight * 0.4), 120, 30);
        this.skipButton.setBounds((int) (screenWidth * 0.60), (int) (screenHeight * 0.6), 120, 30);
        this.unoButton.setBounds((int) (screenWidth * 0.30), (int) (screenHeight * 0.5), 120, 30);
        this.sortbyColorButton.setBounds((int) (screenWidth * 0.60), (int) (screenHeight * 0.4), 120, 30);
        this.sortbyNumberButton.setBounds((int) (screenWidth * 0.60), (int) (screenHeight * 0.5), 120, 30);
    }

    /**
     * funzione che permette l'avvio del file audio di sottofondo
     */
    public void playMusic() {
        try {
            // creo un oggetto Clip per riprodurre il file audio
            this.clip = AudioSystem.getClip();

            // apro il file audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(MUSIC_PATH));
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
     * inizializzo l'oggetto bottone con la relativa grafica e funzionalità
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
                this.message = new Message(user.isUno, SKIP, user.userName, user.isUno.toString());
                this.communication.sendMessage(message);
            } catch (IOException | ParserConfigurationException | TransformerException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica e funzionalità
     */
    private void initSortByNumberButton() {
         // inizializzo l'oggetto
        this.sortbyNumberButton = new JButton("Riordina numero");

        this.sortbyNumberButton.setBackground(new Color(0, 162, 174)); // sfondo azzuro
        this.sortbyNumberButton.setForeground(Color.WHITE); // testo bianco
        this.sortbyNumberButton.setFont(new Font("Arial", Font.BOLD, 10));

        // assegno l'azione da svolgere quando cliccato
        this.sortbyNumberButton.addActionListener(e -> {
            try {

                // manda il messaggio 
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è impostato correttamente
                //                                                                              false e mazzo > 1
                //                                                                              true e mazzo == 1
                this.message = new Message(user.isUno, SORT_BY_NUMBER, user.userName, user.isUno.toString());
                this.communication.sendMessage(message);
            } catch (IOException | ParserConfigurationException | TransformerException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

    }
    /**
     * inizializzo l'oggetto bottone con la relativa grafica e funzionalità
     */
    private void initSortByColorButton() {
        // inizializzo l'oggetto
        this.sortbyColorButton = new JButton("Riordina colore");

        this.sortbyColorButton.setBackground(new Color(0, 162, 174)); // sfondo azzuro
        this.sortbyColorButton.setForeground(Color.WHITE); // testo bianco
        this.sortbyColorButton.setFont(new Font("Arial", Font.BOLD, 10));

        // assegno l'azione da svolgere quando cliccato
        this.sortbyColorButton.addActionListener(e -> {
            try {

                // manda il messaggio 
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è impostato correttamente
                //                                                                              false e mazzo > 1
                //                                                                              true e mazzo == 1
                this.message = new Message(user.isUno, SORT_BY_COLOR, user.userName, user.isUno.toString());
                this.communication.sendMessage(message);
            } catch (IOException | ParserConfigurationException | TransformerException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
    }


    /**
     * inizializzo l'oggetto bottone con la relativa grafica e funzionalità
     */
    public void initUnoButton() {
        // inizializzo l'oggetto
        this.unoButton = new JButton("UNO!");

        this.unoButton.setBackground(new Color(255, 196, 0)); // sfondo giallo
        this.unoButton.setForeground(Color.WHITE); // testo bianco
        this.unoButton.setFont(new Font("Arial", Font.BOLD, 14));

        // assegno l'azione da svolgere quando cliccato
        this.playButton.addActionListener(e -> {
            try {

                // manda il messaggio 
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è impostato correttamente
                //                                                                              false e mazzo > 1
                //                                                                              true e mazzo == 1
                this.message = new Message(user.isUno, UNO, user.userName, user.isUno.toString());
                this.communication.sendMessage(message);
            } catch (IOException | ParserConfigurationException | TransformerException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
    }

    /**
     * inizializzo l'oggetto bottone con la relativa grafica e funzionalità
     */
    public void initPlayButton() {
        // inizializzo l'oggetto
        this.playButton = new JButton("Gioca carta");

        this.playButton.setBackground(new Color(0, 174, 46)); // sfondo verde
        this.playButton.setForeground(Color.WHITE); // testo bianco
        this.playButton.setFont(new Font("Arial", Font.BOLD, 14));

        // assegno l'azione da svolgere quando cliccato
        this.playButton.addActionListener(e -> {
            try {

                // manda il messaggio 
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è impostato correttamente
                //                                                                              false e mazzo > 1
                //                                                                              true e mazzo == 1
                this.message = new Message(user.isUno, PLAY, user.userName, user.isUno.toString());
                this.communication.sendMessage(message);
            } catch (IOException | ParserConfigurationException | TransformerException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });


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

    public void controlSelection(JPanel overlauPanel) throws IOException, URISyntaxException, SAXException {
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
            case QUIT_COMBOBOX:
                try {
                    if(user.round)
                    {
                        // manda il messaggio 
                        // il messaggio invia anche se è stato detto UNO--> il server controlla se è impostato correttamente
                        //                                                                              false e mazzo > 1
                        //                                                                              true e mazzo == 1
                        this.message = new Message(user.isUno, QUIT, user.userName, "");
                        this.communication.sendMessage(message);
                        // aspetto la risposta
                        String reply = this.communication.listening();
                        // unserializzo il messaggio
                        this.message.InitMessageFromStringXML(reply);
                        if(this.message.command.equals(CORRECT))
                        {
                            // porta sulla homepage
                            setVisible(false);
                            this.clip.stop();
                            homepage h = new homepage();
                        }
                    }
                    else{
                        // messaggio di errore
                        JOptionPane.showMessageDialog(this, "Attendi il tuo turno!", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException | ParserConfigurationException | TransformerException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                break;
        }

    }
}
