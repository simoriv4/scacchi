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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class gamepage extends JFrame {
    private final String rootName = "client2";
    private final String COVER_UNO_PATH = rootName + "\\src\\assets\\card-back.png";

    // lista comandi
    private final String skip = "skip";
    private final String uno = "uno";
    private final String play = "play";


    // messaggi di risposta
    private final static String CORRECT = "200";
    private final static String ERROR_USERNAME = "400";
    private final static String ERROR_CARD_PALYED = "406";
    private final static String WINNER = "201";
    private final static String ERROR_SKIP = "409";
    private final static String ERROR_EXIT = "500";

    private final static Integer WIDTH_UNO_IMAGE = 150;
    private final static Integer HEIGHT_UNO_IMAGE = 150;



    public static Socket socket;

    private BufferedImage backgroundImage;
    private JButton playButton;
    private JButton quitButton;
    private JButton unoButton;
    private JButton drawButton;
    private JButton skipButton;
    private JLabel UNO_Label;
    private JLabel deck_Label;
    private JLabel discarded_Label;

    private Message message;
    // private Boolean isListening;
    private User user;

    private Server server;



    // Streams
    // private BufferedReader inStream;
    // private PrintWriter outStream;

    public gamepage() throws IOException, ParserConfigurationException, SAXException {

        // ininzializzo le informazioni del server
        this.server = new Server();
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

        this.backgroundImage = ImageIO.read(new File(rootName + "\\src\\assets\\backgrounds\\bgG.png"));
        this.resizeBufferedImage(this.backgroundImage, (int) (screenWidth * 0.4), (int) (screenHeight * 0.4));

        // inizializzo una label che contiene l'immagine del logo
        this.initImageLabel(this.UNO_Label, COVER_UNO_PATH);

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

        

        // this.initLabel();

        // imposto le posizioni e le dimensioni dei componenti manualmente
        this.setPositions(screenWidth, screenHeight);

        // imposto la dimensione della label
        this.UNO_Label.setSize(WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);;

        // aggiungo i componenti al pannello di sovrapposizione
        overlayPanel.add(UNO_Label);
        overlayPanel.add(playButton);
        overlayPanel.add(drawButton);
        overlayPanel.add(quitButton);
        overlayPanel.add(skipButton);
        overlayPanel.add(unoButton);

        add(overlayPanel);


        //this.frame.add(panel);
        // imposta il frame a schermo intero
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private String listening() throws UnknownHostException, IOException {
        Socket s = new Socket(this.server.IP, this.server.port);

        InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
        BufferedReader reader = new BufferedReader(streamReader);

        //Get the response message and print it to console
        String responseMessage;
        while ((responseMessage = reader.readLine()) != null) 
        reader.close();
        return responseMessage;
    }

    /**
     * setto la posizione degli elementi nella finestra
     * 
     * @param screenWidth
     * @param screenHeight
     */
    public void setPositions(int screenWidth, int screenHeight) {
        this.UNO_Label.setBounds(0, 0, WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
        this.playButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.4), 120, 30);
        this.quitButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.5), 100, 30);
        this.skipButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.6), 100, 30);
        this.drawButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.7), 100, 30);
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
            // creo una connessione TCP con il server
           Socket socket = new Socket(this.server.IP, this.server.port);

            OutputStream outputStream = socket.getOutputStream();

            // creo un oggetto OutputStreamWriter per inviare dati al server
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            // invio il messaggio al server
            writer.write(message.serialize());
            writer.flush(); // scrivo e svuoto il buffer

            // chiduo la connessione
            socket.close();
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
        this.drawButton = new JButton("Scarta");

        this.drawButton.setBackground(new Color(0,162,174)); // sfondo azzuro
        this.drawButton.setForeground(Color.WHITE); // testo bianco
        this.drawButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * funzione che imposta le immagini nella JLabel 
     * @param label
     * @param imagePath
     * @return una JLabel con l'immagine passata
     * @throws IOException
     */
    public JLabel initImageLabel(JLabel label, String imagePath) throws IOException
    {
        // inizializzo una label che contiene l'immagine del logo
        label = new JLabel();
        // creo l'oggetto immagine
        ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(imagePath)));
        imageIcon =this.initImageIcon(imageIcon, WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
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

}
