import javax.swing.*;
import java.awt.*;

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
}
