import javax.swing.*;
import java.awt.*;

public class Base64ToZipGUI extends JFrame {

    private JTextField jTextField;
    public Base64ToZipGUI() {
        this.setTitle("Base64ToZipGUI");
        this.setSize(600, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = this.getContentPane();
        pane.setLayout(new GridLayout(1, 1));

        jTextField = new JTextField(20);
        jTextField.setText("Please input here");
        jTextField.requestFocus();
        pane.add(jTextField);
    }
//    public static void main(String[] args) {
//        Base64ToZipGUI app = new Base64ToZipGUI();
//        app.setVisible(true);
//    }
}
