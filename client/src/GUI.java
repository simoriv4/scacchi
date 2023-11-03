
import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JLabel messageLabel; // Dichiarazione della variabile messageLabel
    private JTextField inputField; // Dichiarazione della variabile inputField
    private JButton playButton; // Dichiarazione della variabile playButton

    public GUI() {
        // Impostazioni iniziali della finestra
        setTitle("Uno Client");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Etichetta per messaggi
        messageLabel = new JLabel("Benvenuto a Uno!\n Inserisci il nome utente");
        add(messageLabel, BorderLayout.NORTH);

        // Pannello per il campo di input e il pulsante
        JPanel inputPanel = new JPanel();
        inputField = new JTextField(20);
        playButton = new JButton("Gioca");
        inputPanel.add(inputField);
        inputPanel.add(playButton);

        add(inputPanel, BorderLayout.CENTER);

        // Listener per il pulsante "Gioca"
        playButton.addActionListener(e -> {
            String inputText = inputField.getText();
            // Gestisci l'input qui
            messageLabel.setText("Hai inserito: " + inputText);
            inputField.setText(""); // Cancella il campo di input
        });
    }


}
