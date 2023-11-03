import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JLabel messageLabel;
    private JTextField inputField;
    private JButton playButton;

    public GUI() {
        setTitle("Uno Client");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageLabel = new JLabel("Benvenuto a Uno!\n Inserisci il nome utente");
        add(messageLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(20);
        playButton = new JButton("Gioca");
        inputPanel.add(inputField);
        inputPanel.add(playButton);

        add(inputPanel, BorderLayout.CENTER);

        playButton.addActionListener(e -> {
            String inputText = inputField.getText();
            messageLabel.setText("Hai inserito: " + inputText);
            inputField.setText("");
        });
    }
}
