import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainGUI extends JFrame {

    private JButton zipToBase64Button;
    private JButton base64ToZipButton;
    private JFileChooser fileChooser;


    public MainGUI() {
        this.setTitle("File Converter");
        this.setSize(600, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = this.getContentPane();
        pane.setLayout(new GridLayout(1, 2));

        fileChooser = new JFileChooser();

        zipToBase64Button = new JButton("ZIP to Base64");

        zipToBase64Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setMultiSelectionEnabled(false);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        Converter.toBase64(selectedFile);
                        JOptionPane.showMessageDialog(null, "ZIP to Base64 Done!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException error) {
                        JOptionPane.showMessageDialog(null, error.getMessage(), "FAIL", JOptionPane.INFORMATION_MESSAGE);
                    }


                }
				
            }
        });

        base64ToZipButton = new JButton("Base64 to ZIP");
        base64ToZipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                fileChooser.setMultiSelectionEnabled(true);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    String outputFileName = new String();
                    Converter.toZip(outputFileName, selectedFiles);

                    JOptionPane.showMessageDialog(null, "Base64 to ZIP done", "Notice", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        pane.add(zipToBase64Button);
        pane.add(base64ToZipButton);
    }

    public static void main(String[] args) {
        MainGUI app = new MainGUI();
        app.setVisible(true);
    }
}
