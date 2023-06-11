import javax.swing.*;

public class JProgressBarDrive {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Progress Bar Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        // Create a JProgressBar
        final JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        // Add the progress bar to the frame
        frame.getContentPane().add(progressBar);

        // Display the frame
        frame.setVisible(true);

        // Update the progress bar in a separate thread
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    final int progress = i;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            progressBar.setValue(progress);
                        }
                    });
                    try {
                        Thread.sleep(100);  // Sleep for 100 milliseconds
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }
}
