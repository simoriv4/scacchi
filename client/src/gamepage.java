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

    private final String CARDS_PATH = "src\\assets\\cards-front";
    private final String ADD_2_CARDS_NAME = "D2";
    private final String ADD_4_CARDS_NAME = "D4W";
    private final String CHANGE_COLOR_NAME = "W";
    private final String BLOCK_CARD_NAME = "skip";
    private final String CHANGE_TURN_CARD_NAME = "_";

    private final String ROULES_COMBOBOX = "Regolamento";
    private final String QUIT_COMBOBOX = "Esci";
    private final String MUSIC_OFF_COMBOBOX = "Disattiva audio";
    private final String MUSIC_ON_COMBOBOX = "Attiva audio";

    // lista comandi
    public final String SKIP = "skip";
    public final String UNO = "uno";
    public final String PLAY = "play";
    public final String DRAW = "draw";
    public final String QUIT = "quit";
    public final String INIT_DECK = "init";
    public final String START = "start";
    public final static String SORT_BY_NUMBER = "sortByNumber";
    public final static String SORT_BY_COLOR = "sortByColor";

    private final static String CARD_ADD_2_CARDS = "CardAdd2Cards";
    private final static String CARD_ADD_4_CARDS = "CardAdd4Cards";
    private final static String CARD_BLOCK = "CardBlock";
    private final static String CARD_CHANGE_COLOR = "CardChangeColor";
    private final static String CARD_CHANGE_TURN = "CardChangeTurn";
    private final static String CARD_NUMBER = "CardNumber";

    private final static String RED = "red";
    private final static String YELLOW = "yellow";
    private final static String BLUE = "blue";
    private final static String GREEN = "green";

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
    private MyLabel UNO_Label;
    private MyLabel deck_Label;
    private MyLabel discarded_Label;
    private JComboBox<String> combobox;
    private Clip clip;
    private JPanel overlayPanel;

    private Integer screenWidth;
    private Integer screenHeight;

    private Message message;
    private User user;
    private Server server;
    private Card played_card;
    public Integer selected_card;

    public final Communication communication;

    // Streams
    // private BufferedReader inStream;
    // private PrintWriter outStream;

    public gamepage(User user, Communication c) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        // ininzializzo le informazioni del server
        this.server = new Server();
        // salvo le informazioni dell'utente
        this.user = user;
        this.socket = new Socket(this.server.IP, this.server.port);
        this.communication = c;

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

        // inizializzo una label che contiene l'immagine del logo
        this.UNO_Label = this.initImageLabel(this.UNO_Label, UNO_PATH, WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
        this.deck_Label = this.initImageLabel(this.deck_Label, COVER_CARD_PATH, WIDTH_CARDS, HEIGHT_CARDS);

        // controllo il tipo di carta--> in base a quello cerco l'immagine corretta
        // creo un pannello personalizzato per sovrapporre i componenti
        this.overlayPanel = new JPanel() {
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
        this.message = new Message(user.isUno, INIT_DECK, user.userName, "", "");
        this.communication.sendMessage(this.message);
        // attendo la risposta
        String reply = this.communication.listening();
        overlayPanel = this.initDeck(overlayPanel, reply);

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

        // controllo il mouse click sul mazzo girato--> per pescare la carta
        this.deck_Label.addMouseListener(new MouseAdapter() {
            // private Communication communication = new Communication(socket);

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // quando viene cliccato il mazzo richiede al server di pescare la carta
                System.out.println("cliccato");
                // invio il messaggio al server
                try {
                    Message message = new Message(user.isUno, DRAW, user.userName, "", "");
                    communication.sendMessage(message);
                    // aspetto la risposta
                    String reply = communication.listening();
                    JPanel overlayPanel = new JPanel();
                    overlayPanel = initDeck(overlayPanel, reply);

                } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        add(overlayPanel);

        // imposta il frame a schermo intero
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

    }

    private JPanel initDeck(JPanel overlayPanel, String reply)
            throws IOException, ParserConfigurationException, TransformerException, SAXException {

        // unserializzo il messaggio
        this.message.InitMessageFromStringXML(reply);

        // unserializzo il mazzo
        this.user.deck.unserializeDeck(this.message.message);

        int x = (int) (screenWidth * 0.4);
        int y = (int) (screenHeight * 0.7);
        // scorro le carte e le aggiungo all'overlay panel
        for (int i = 0; i < this.user.deck.getSizeDeck(); i++) {
            MyLabel card = new MyLabel();
            Card c = this.user.deck.deck.get(i);
            String cardPath = "";
            switch (c.getType()) {
                case CARD_ADD_2_CARDS:
                    cardPath = CARDS_PATH + "\\" + ADD_2_CARDS_NAME + this.getColor(c) + ".png";
                    break;

                case CARD_ADD_4_CARDS:
                    cardPath = CARDS_PATH + "\\" + ADD_4_CARDS_NAME + ".png";
                    break;

                case CARD_BLOCK:
                    cardPath = CARDS_PATH + "\\" + BLOCK_CARD_NAME + this.getColor(c) + ".png";
                    break;
                case CARD_CHANGE_COLOR:
                    cardPath = CARDS_PATH + "\\" + CHANGE_COLOR_NAME + ".png";
                    break;
                case CARD_CHANGE_TURN:
                    cardPath = CARDS_PATH + "\\" + CHANGE_TURN_CARD_NAME + this.getColor(c) + ".png";
                    break;
                case CARD_NUMBER:
                    cardPath = CARDS_PATH + "\\" + c.getNumber() + this.getColor(c) + ".png";
                    break;
            }
            card = this.initImageLabel(card, cardPath, WIDTH_CARDS, HEIGHT_CARDS);
            // assegno l'indice alla carta
            card.index = i;

            card.addMouseListener(new LabelAdapter(card, this, false));
            // setto la posizione
            // la prima carta è sempre al centro--> poi si mette a x - n e x + n
            // setto posizione
            card.setBounds(x, y, WIDTH_CARDS, HEIGHT_CARDS);
            // aggiorno la x
            x += (WIDTH_CARDS + 10);
            // aggiungi la carta al panel
            overlayPanel.add(card);
        }
        return overlayPanel;

    }

    /**
     * funzione che ritorna la lettera corrispondente al colore della carta--> sarà
     * usata per formare il nome della foto della carta
     * 
     * @param c
     * @return
     */
    public String getColor(Card c) {
        switch (c.getColor()) {
            case RED:
                return "R";
            case YELLOW:
                return "Y";
            case BLUE:
                return "B";
            case GREEN:
                return "G";
        }
        return null;
    }

    /**
     * inizializza la combobox con le opzioni disponibili
     * 
     * @param a
     * @param overlayPanel
     * @param screenWidth
     * @param screenHeight
     */
    public void initCombobox(String[] a, JPanel overlayPanel, Integer screenWidth, Integer screenHeight) {
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
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è
                // impostato correttamente
                // false e mazzo > 1
                // true e mazzo == 1
                this.message = new Message(user.isUno, SKIP, user.userName, user.isUno.toString(), "");
                this.communication.sendMessage(message);
                // aspetto la risposta
                String reply = this.communication.listening();
                // unserializzo il messaggio
                this.message.InitMessageFromStringXML(reply);
                // controllo il codice di errore
                if (this.message.command != CORRECT)
                    // messaggio di errore
                    JOptionPane.showMessageDialog(this, message.message, "Errore", JOptionPane.ERROR_MESSAGE);
                else {
                    // rimane in ascolto fino a che non è il suo turno
                    while (!user.round) {
                        // leggo il messaggio del server
                        String server_message = this.communication.listening();
                        // unserializzo il messaggio
                        this.message.InitMessageFromStringXML(server_message);
                        // se è stata passata una carta scartata
                        if (!this.message.discarderdCard.isEmpty()) {
                            int x = (int) (screenWidth * 0.5);
                            int y = (int) (screenHeight * 0.4);
                            // scorro le carte e le aggiungo all'overlay panel
                            for (int i = 0; i < this.user.deck.getSizeDeck(); i++) {
                                MyLabel card = new MyLabel();
                                Card c = this.user.deck.deck.get(i);
                                String cardPath = "";
                                switch (c.getType()) {
                                    case CARD_ADD_2_CARDS:
                                        cardPath = CARDS_PATH + "\\" + ADD_2_CARDS_NAME + this.getColor(c) + ".png";
                                        break;

                                    case CARD_ADD_4_CARDS:
                                        cardPath = CARDS_PATH + "\\" + ADD_4_CARDS_NAME + ".png";
                                        break;

                                    case CARD_BLOCK:
                                        cardPath = CARDS_PATH + "\\" + BLOCK_CARD_NAME + this.getColor(c) + ".png";
                                        break;
                                    case CARD_CHANGE_COLOR:
                                        cardPath = CARDS_PATH + "\\" + CHANGE_COLOR_NAME + ".png";
                                        break;
                                    case CARD_CHANGE_TURN:
                                        cardPath = CARDS_PATH + "\\" + CHANGE_TURN_CARD_NAME + this.getColor(c)
                                                + ".png";
                                        break;
                                    case CARD_NUMBER:
                                        cardPath = CARDS_PATH + "\\" + c.getNumber() + this.getColor(c) + ".png";
                                        break;
                                }
                                card = this.initImageLabel(card, cardPath, WIDTH_CARDS, HEIGHT_CARDS);
                                card.setBounds(x, y, WIDTH_CARDS, HEIGHT_CARDS);

                                this.overlayPanel.add(card);
                                add(this.overlayPanel);
                            }
                        }

                    }
                }

            } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
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
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è
                // impostato correttamente
                // false e mazzo > 1
                // true e mazzo == 1
                this.message = new Message(user.isUno, SORT_BY_NUMBER, user.userName, user.isUno.toString(), "");
                this.communication.sendMessage(message);

                // aspetto la risposta
                String reply = this.communication.listening();
                // unserializzo il messaggio
                this.message.InitMessageFromStringXML(reply);
                // controllo il codice di errore
                if (this.message.command == CORRECT) {
                    this.overlayPanel = initDeck(overlayPanel, reply);
                    remove(overlayPanel);
                    add(this.overlayPanel);
                } else
                    // messaggio di errore
                    JOptionPane.showMessageDialog(this, message.message, "Errore", JOptionPane.ERROR_MESSAGE);

            } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
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
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è
                // impostato correttamente
                // false e mazzo > 1
                // true e mazzo == 1
                this.message = new Message(user.isUno, SORT_BY_COLOR, user.userName, user.isUno.toString(), "");
                this.communication.sendMessage(message);

                // aspetto la risposta
                String reply = this.communication.listening();
                // unserializzo il messaggio
                this.message.InitMessageFromStringXML(reply);
                // controllo il codice di errore
                if (this.message.command == CORRECT) {
                    this.overlayPanel = initDeck(overlayPanel, reply);
                    remove(overlayPanel);
                    add(this.overlayPanel);
                } else
                    // messaggio di errore
                    JOptionPane.showMessageDialog(this, message.message, "Errore", JOptionPane.ERROR_MESSAGE);

            } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
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
        this.unoButton.addActionListener(e -> {
            try {

                // manda il messaggio
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è
                // impostato correttamente
                // false e mazzo > 1
                // true e mazzo == 1
                this.message = new Message(user.isUno, UNO, user.userName, user.isUno.toString(), "");
                this.communication.sendMessage(message);
                // aspetto la risposta
                String reply = this.communication.listening();
                // unserializzo il messaggio
                this.message.InitMessageFromStringXML(reply);
                // controllo il codice di errore
                if (this.message.command == CORRECT) {
                    this.overlayPanel = initDeck(overlayPanel, reply);
                    remove(overlayPanel);
                    add(this.overlayPanel);

                    JOptionPane.showMessageDialog(this, "Non hai una sola carta!", "Errore", JOptionPane.ERROR_MESSAGE);
                } else
                    // messaggio di errore
                    JOptionPane.showMessageDialog(this, message.message, "Errore", JOptionPane.ERROR_MESSAGE);

            } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
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
                // il messaggio invia anche se è stato detto UNO--> il server controlla se è
                // impostato correttamente
                // false e mazzo > 1
                // true e mazzo == 1
                this.played_card = this.user.deck.deck.get(selected_card);
                this.message = new Message(user.isUno, PLAY, user.userName, this.played_card.serializeToString(), "");
                this.communication.sendMessage(message);

                // aspetto la risposta
                String reply = this.communication.listening();
                // unserializzo il messaggio
                this.message.InitMessageFromStringXML(reply);
                // controllo il codice di errore
                if (this.message.command == CORRECT) {
                    this.overlayPanel = initDeck(overlayPanel, reply);
                    remove(overlayPanel);
                    add(this.overlayPanel);
                } else
                    // messaggio di errore
                    JOptionPane.showMessageDialog(this, message.message, "Errore", JOptionPane.ERROR_MESSAGE);

            } catch (IOException | ParserConfigurationException | TransformerException | SAXException e1) {
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
    public MyLabel initImageLabel(MyLabel label, String imagePath, Integer width, Integer height) throws IOException {
        // inizializzo una label che contiene l'immagine del logo
        label = new MyLabel();
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
                    if (user.round) {
                        // manda il messaggio
                        // il messaggio invia anche se è stato detto UNO--> il server controlla se è
                        // impostato correttamente
                        // false e mazzo > 1
                        // true e mazzo == 1
                        this.message = new Message(user.isUno, QUIT, user.userName, "", "");
                        this.communication.sendMessage(message);
                        // aspetto la risposta
                        String reply = this.communication.listening();
                        // unserializzo il messaggio
                        this.message.InitMessageFromStringXML(reply);
                        if (this.message.command.equals(CORRECT)) {
                            // porta sulla homepage
                            setVisible(false);
                            this.clip.stop();
                            homepage h = new homepage();
                        }
                    } else {
                        // messaggio di errore
                        JOptionPane.showMessageDialog(this, "Attendi il tuo turno!", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException | ParserConfigurationException | TransformerException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                break;
        }

    }
}
