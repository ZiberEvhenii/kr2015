import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //new Form();
        SwingUtilities.invokeLater(new Runnable() {


            public void run() {
                new Frame();
            }
        });


    }
}
