import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private String Sline;
    private Image image;
    public ImagePanel(String img, String Sline) {
        this(new ImageIcon(img).getImage());
        this.Sline= Sline;
    }
    public ImagePanel(Image image) {
        this.image = image;
        Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
        g.drawImage(new ImageIcon(Sline).getImage(),100,50,null);
    }
}
