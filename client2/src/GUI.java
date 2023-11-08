import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private BufferedImage backgroundImage;
    private JTextField inputField;
    private JButton playButton;
    private JLabel messageLabel;
    private JLabel imageLabel;

    public GUI() throws IOException {
        setTitle("Uno Client");
        setSize(1050, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backgroundImage = ImageIO.read(new File("client2/img/wallpaper2.jpg"));
        // inizializzo una label che contiene l'immagine del titolo
        imageLabel = new JLabel();
        // creo l'oggetto immagine
        ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File("client2/img/title.png")));
        imageLabel.setIcon(imageIcon);

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

        inputField = new JTextField(20);
        playButton = new JButton("Gioca");
        messageLabel= new JLabel("Inserisci il nome con il quale vuoi giocare:");
        // creo l'oggetto font che mi serve per impostare il font alla messageLabel
        Font font = new Font("CabinBold", Font.BOLD, 16);

        // calcolo le coordinate in base alle percentuali dello schermo
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        // imposto le posizioni e le dimensioni dei componenti manualmente
        imageLabel.setBounds((int) (screenWidth * 0.1), 0, 600, 50);
        messageLabel.setBounds(20,(int) (screenHeight * 0.1), 600, 30);
        inputField.setBounds((int) (screenWidth * 0.30), (int) (screenHeight * 0.1), 200, 40);
        playButton.setBounds((int) (screenWidth * 0.48), (int) (screenHeight * 0.1), 100, 30);

        // imposto il colore della label
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(font);


        // aggiungo i componenti al pannello di sovrapposizione
        overlayPanel.add(imageLabel);
        overlayPanel.add(messageLabel);
        overlayPanel.add(inputField);
        overlayPanel.add(playButton);

        add(overlayPanel);
        setVisible(true);
    }
}
