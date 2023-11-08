import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private JLabel messageLabel;
    private JTextField inputField;
    private JButton playButton;
    private BufferedImage immagineSfondo;
    private JPanel panel = new JPanel(); // pannello per il contenuto

    public GUI() throws IOException {
        setTitle("Uno Client");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        immagineSfondo = ImageIO.read(new File("client2/img/wallpaper.jpg"));
        panel = initWallpaperPanel();
        panel.setLayout(new BorderLayout());

        messageLabel = new JLabel("Benvenuto a Uno! Inserisci il nome utente");
        panel.add(messageLabel, BorderLayout.NORTH);

        inputField = new JTextField(20);
        playButton = new JButton("Gioca");

        // Crea un pannello per il campo di input e il pulsante
        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(playButton);

        panel.add(inputPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    private JPanel initWallpaperPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.fillRect(0, 0, getWidth(), getHeight());
                disegnaSfondo(g);
            }
        };
    }

    private void disegnaSfondo(Graphics g) {
        if (immagineSfondo != null)
            g.drawImage(immagineSfondo, 0, 0, getWidth(), getHeight(), this);
    }
}
