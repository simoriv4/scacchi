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

public class homepage extends JFrame {
    private final String UNO_PATH = "src\\assets\\logo.png";
    private final String BACKGROUND_IMAGE_PATH = "src\\assets\\backgrounds\\wallpaper.png";
    private final String SONG_PATH = "audio\\UNO_track.wav";

    // lista comandi
    private final String START = "start";

    // messaggi di risposta
    private final static String CORRECT = "200";
    private final static String ERROR_USERNAME = "400";
    private final static String ERROR_CARD_PALYED = "406";
    private final static String WINNER = "201";
    private final static String ERROR_SKIP = "409";
    private final static String ERROR_EXIT = "500";

    private final static Integer WIDTH_UNO_IMAGE = 200;
    private final static Integer HEIGHT_UNO_IMAGE = 200;



    public Socket socket;

    private BufferedImage backgroundImage;
    private JTextField username;
    private JButton playButton;
    // private JLabel messageLabel;
    private JLabel UNO_Label;
    private Message message;
    private User user;
    private Clip clip;
    private JLabel find_message;

    private Communication communication;

    private Server server;



    // Streams
    // private BufferedReader inStream;
    // private PrintWriter outStream;

    public homepage() throws IOException, ParserConfigurationException, SAXException {
        // ininzializzo le informazioni del server
        this.server = new Server();
        // creo una connessione TCP con il server
       this.socket = new Socket(this.server.IP, this.server.port);

        this.communication = new Communication(socket);
        // avvio il sottofondo musicale
        this.playMusic();
        // imposto il titolo al frame
        setTitle("homepage");

        // calcolo le coordinate in base alle percentuali dello schermo
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
        //this.resizeBufferedImage(this.backgroundImage, (int) (screenWidth * 0.4), (int) (screenHeight * 0.4));
        // inizializzo una label che contiene l'immagine del titolo
        this.UNO_Label = new JLabel();
        // creo l'oggetto immagine
        ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(UNO_PATH)));
        imageIcon =this.initImageIcon(imageIcon, WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
        this.UNO_Label.setIcon(imageIcon);

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
        JLabel find_message = new JLabel("RICERCA PARTITA...");
        this.initLabel("RICERCA PARTITA...");
        // imposto la grafica del bottone
        this.initButton();

        // this.initLabel();

        this.initInputText();

        // imposto le posizioni e le dimensioni dei componenti manualmente
        this.setPositions(screenWidth, screenHeight);

        // imposto il colore della label
        this.UNO_Label.setSize(WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);;

        // aggiungo i componenti al pannello di sovrapposizione
        overlayPanel.add(UNO_Label);
        overlayPanel.add(find_message);
        // overlayPanel.add(messageLabel);
        overlayPanel.add(username);
        overlayPanel.add(playButton);
        //panel.add(GIF_Label);
        add(overlayPanel);


        //this.frame.add(panel);
        // imposta il frame a schermo intero
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);

        // funzione che verifica quando viene premuto il pulsante
        playButton.addActionListener(e -> {
            try{
                if (!username.getText().isEmpty()) {
                    // quando premo il pulsante GIOCA mando una richiesta al server di aggiungere il
                    // client ad una nuova partita-->se non ci sono altri client rimane in attesa
                    // setto il messaggio da inviare
                    message = new Message(false, START, username.getText(), "", "");
                    // invio il messaggio al server
                    this.communication.sendMessage(message);

                    // <root_message>
                        // <command>reply</command>
                        // <message>200</message>
                        // <username>username</username>
                    // </root_message>

                    // passo la prima carta da visualizzare alla gamepage

                    // faccio comparire messaggio di attesa
                    JOptionPane.showMessageDialog(this, "RICERCA PARTITA....", "", JOptionPane.INFORMATION_MESSAGE);

                    // jl.setBounds((int) (screenWidth * 0.4), (int) (screenHeight * 0.8), 300, 300);
                    // overlayPanel.add(jl);
                    // add(overlayPanel);
                    // aspetto la risposta
                    String response = this.communication.listening();

                    message.InitMessageFromStringXML(response);

                    if(message.command.equals(CORRECT))
                    {
                        // se risosta è positiva -> creo l'utente e passo alla seconda finestra
                        this.initUser(username.getText());

                        setVisible(false);
                        this.clip.stop();
                        // creo la gamepage
                        gamepage gp = new gamepage(user, this.communication);
                    }
                    else{
                        // messaggio di errore
                        JOptionPane.showMessageDialog(this, message.message, "Errore", JOptionPane.ERROR_MESSAGE);
                    }

                    username.setText("");
                } else {
                    // messaggio di errore
                    JOptionPane.showMessageDialog(this, "Inserisci un nome utente", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }catch (ParserConfigurationException | SAXException | IOException | TransformerException e1){
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
            }

        });
    }

    private void initUser(String username) {
        this.user = new User();
        this.user.userName =username;

    }

    /**
     * setto la posizione degli elementi nella finestra
     * 
     * @param screenWidth
     * @param screenHeight
     */
    public void setPositions(int screenWidth, int screenHeight) {
        this.UNO_Label.setBounds((int) (screenWidth * 0.4), (int) (screenHeight * 0.1), WIDTH_UNO_IMAGE, HEIGHT_UNO_IMAGE);
        this.username.setBounds((int) (screenWidth * 0.40), (int) (screenHeight * 0.4), 200, 40);
        this.playButton.setBounds((int) (screenWidth * 0.58), (int) (screenHeight * 0.4), 100, 30);
        this.playButton.setBounds((int) (screenWidth * 0.58), (int) (screenHeight * 0.4), 100, 30);
        this.find_message.setBounds((int) (screenWidth * 0.58), (int) (screenHeight * 0.7), 100, 30);
    }

    /**
     * funzione che permette l'avvio del file audio di sottofondo
     */
    public void playMusic() {
        try {
            // creo un oggetto Clip per riprodurre il file audio
            this.clip = AudioSystem.getClip();

            // apro il file audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(SONG_PATH));
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
    public void initButton()
    {
        // inizializzo l'oggetto
        this.playButton = new JButton("Gioca");

        this.playButton.setBackground(new Color(255, 0, 0)); // sfondo blu
        this.playButton.setForeground(Color.WHITE); // testo bianco
        this.playButton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * inizializzo l'oggetto label con la relativa grafica
     */
    public void initLabel(String content)
    {
        // inizializzo l'oggetto
        this.find_message = new JLabel(content);
        // imposto la grafica
        this.find_message.setForeground(Color.WHITE);
        this.find_message.setFont(new Font("Arial", Font.PLAIN, 16)); // Font di dimensione 16
    }

    /**
     * inizializzo l'oggetto label con la relativa grafica
     */
    public void initInputText()
    {
        // inizializzo l'oggetto
        this.username = new JTextField(15);
        this.username.setBackground(new Color(255, 255, 255)); // sfondo bianco
        this.username.setForeground(new Color(0, 0, 0)); // testo nero
        this.username.setFont(new Font("Arial", Font.PLAIN, 16)); // font di dimensione 16
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
