import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class homepage extends JFrame {
    private BufferedImage backgroundImage;
    private JTextField username;
    private JButton playButton;
    private JLabel messageLabel;
    private JLabel imageLabel;
    private final String rootName = "client2";

    public homepage() throws IOException {
        this.playMusic();
        setTitle("Uno Client");

        // calcolo le coordinate in base alle percentuali dello schermo
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        setSize((int) (screenWidth * 1), (int) (screenHeight * 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.backgroundImage = ImageIO.read(new File(rootName + "/img/wallpaper4.jpg"));
        // inizializzo una label che contiene l'immagine del titolo
        this.imageLabel = new JLabel();
        // creo l'oggetto immagine
        ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(rootName + "/img/title.png")));
        this.imageLabel.setIcon(imageIcon);

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

        this.username = new JTextField(20);
        this.playButton = new JButton("Gioca");
        this.messageLabel= new JLabel("Inserisci il nome con il quale vuoi giocare:");

        // creo l'oggetto font che mi serve per impostare il font
        Font font = new Font("Bauhaus 93", Font.BOLD, 16);

        // imposto le posizioni e le dimensioni dei componenti manualmente
        this.setPositions(screenWidth, screenHeight);

        // imposto il colore della label
        this.messageLabel.setForeground(Color.WHITE);
        this.messageLabel.setFont(font);


        // aggiungo i componenti al pannello di sovrapposizione
        overlayPanel.add(imageLabel);
        overlayPanel.add(messageLabel);
        overlayPanel.add(username);
        overlayPanel.add(playButton);

        add(overlayPanel);
        setVisible(true);

        // funzione che verifica quando viene premuto il pulsante
        playButton.addActionListener(e -> {
            String inputText = username.getText();

            username.setText("");
        });
    }

    /**
     * setto la posizione degli elementi nella finestra
     * @param screenWidth
     * @param screenHeight
     */
    public void setPositions(int screenWidth, int screenHeight)
    {
        this.imageLabel.setBounds((int) (screenWidth * 0.1), (int) (screenHeight * 0.1), 600, 50);
        this.messageLabel.setBounds(20,(int) (screenHeight * 0.2), 600, 30);
        this.username.setBounds((int) (screenWidth * 0.30), (int) (screenHeight * 0.2), 200, 40);
        this.playButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.2), 100, 30);
    }

    public void setFontLabel()
    {

    }

    /**
     * funzione che permette l'avvio del file audio di sottofondo
     */
    public void playMusic()
    {
        try {
            // Crea un oggetto Clip per riprodurre il file audio
            Clip clip = AudioSystem.getClip();

            // Apri il file audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(rootName +"/audio/UNO_track.wav"));
            clip.open(audioStream);

            // Riproduci la musica in loop
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Avvia la riproduzione
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
