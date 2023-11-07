import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private JLabel messageLabel;
    private JTextField inputField;
    private JButton playButton;

    public GUI() {
        setTitle("Uno Client");
        // creo la finestra con le dimensioni specificate
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageLabel = new JLabel("Benvenuto a Uno! Inserisci il nome utente");
        add(messageLabel, BorderLayout.NORTH);
        
        setWallpaper();

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(20);
        playButton = new JButton("Gioca");
        inputPanel.add(inputField);
        inputPanel.add(playButton);

        add(inputPanel, BorderLayout.CENTER);

        playButton.addActionListener(e -> {
            String username = inputField.getText();
            // invio al server la richiesta di giocare--> passo il nome utente inserito
            //messageLabel.setText("Hai inserito: " + inputText);
            inputField.setText("");
        });
    }
    /**
     * setta lo sfondo della finsetra
     */
    private void setWallpaper() {
        JFrame frame = new JFrame("Sfondo con immagine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Crea un JLabel per contenere l'immagine di sfondo
        JLabel background = new JLabel();

        // Carica l'immagine da un file
        try {
            ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File("sfondo.jpg"))); // Sostituisci con il percorso dell'immagine
            background.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Imposta il layout del frame su null in modo da poter posizionare il JLabel come sfondo
        frame.setLayout(null);

        // Imposta le dimensioni del JLabel per coprire l'intero frame
        background.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // Aggiungi il JLabel al frame
        frame.add(background);
    }
}
