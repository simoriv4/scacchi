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

    public GUI() throws IOException {
        setTitle("Uno Client");
        setSize(1050, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backgroundImage = ImageIO.read(new File("client2/img/wallpaper2.jpg"));

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

        // imposto le posizioni e le dimensioni dei componenti manualmente
        inputField.setBounds(100, 100, 200, 30);
        playButton.setBounds(100, 150, 100, 30);

        // aggiungo i componenti al pannello di sovrapposizione
        overlayPanel.add(inputField);
        overlayPanel.add(playButton);

        add(overlayPanel);
        setVisible(true);
    }
}
