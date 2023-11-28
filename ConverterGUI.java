import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ConverterGUI extends JFrame {

    private JButton zipToBase64Button;
    private JButton base64ToZipButton;
    private JFileChooser fileChooser;

    public ConverterGUI() {
        this.setTitle("Base64/Bin Converter");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = this.getContentPane();
        pane.setLayout(new GridLayout(3, 1));


        zipToBase64Button = new JButton("Convert Bin to Base64");
        zipToBase64Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
					String inputFile = selectedFile.getAbsolutePath();

					Converter.toBase64(inputFile);
					JOptionPane.showMessageDialog(null, "Done", "Notice", JOptionPane.INFORMATION_MESSAGE);
                    // call your ZiptoBase64 method here
                    // ZiptoBase64.convert(selectedFile);
                }
				
            }
        });

        base64ToZipButton = new JButton("Convert Base64 to Bin");
        base64ToZipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
				// enable mutiSelect
				fileChooser.setMultiSelectionEnabled(true);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
					String[] inputFiles = new String[selectedFiles.length];
					for(int i = 0; i < selectedFiles.length; i++) {
						inputFiles[i] = selectedFiles[i].getAbsolutePath();
					}


                    JOptionPane.showMessageDialog(null, Converter.toBin(inputFiles), "Notice", JOptionPane.INFORMATION_MESSAGE);
					//JOptionPane.showMessageDialog(null, inputFiles, "Notice", JOptionPane.INFORMATION_MESSAGE);					
                    // call your CombineTozip method here
                    // CombineTozip.convert(selectedFile);
                }
            }
        });

        pane.add(zipToBase64Button);
        pane.add(base64ToZipButton);
    }

    public static void main(String[] args) {
        ConverterGUI app = new ConverterGUI();
        app.setVisible(true);
    }
}
