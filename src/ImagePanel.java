import javax.swing.*;
import java.awt.*;

class ImagePanel extends JPanel {
    public static Image img;
    private String line;
    public ImagePanel(String img, String line) {
        this(new ImageIcon(img).getImage());
        this.line= line;
    }
    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        //g.drawImage(img, 0, 0, null);
       // g.drawImage(new ImageIcon(line).getImage(),50,50,null);
        super.paintComponent(g);
        //int imageWidth = image.getWidth(this);
        //int imageHeight = image.getHeight(this);
        g.drawImage((new ImageIcon(img).getImage()), 0, 0, 270, 270, null);
    }

}