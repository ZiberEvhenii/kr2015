import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

public class Frame extends JFrame implements ActionListener, Music {
    private JPanel playerPanel;
    public  String filename;
    public  Player player1 = new Player();
    private Thread playbackThread;
    private Timer timer;

    private boolean isPlaying = false;
    private boolean isPause = false;
    public JPanel panelButtons;
    public static String audioFilePath;
    public JPanel defoltPanel = new JPanel();
    private JLabel labelFileName = new JLabel("Playing File:");
    private JLabel labelTimeCounter = new JLabel("00:00:00");
    private JLabel labelDuration = new JLabel("00:00:00");
    public  String lastOpenPath;

    public static  JButton buttonOpen = new JButton();
    private JButton buttonPlay = new JButton();
    public JButton buttonPause = new JButton();
    private JButton open = new JButton("Open");
    private JButton left = new JButton();
    private JButton right = new JButton();
    private JButton openList = new JButton("Play list");
    private JSlider sliderTime = new JSlider();
    private ImagePanel panel;
    private JComboBox songs;
    private JTextPane textArea;
    private ImPanel impanel;
    public Frame() {
        initPlayer();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 400, 450);
        JPanel contentPane = new JPanel(new BorderLayout());
        impanel = new ImPanel("Apple-Music.jpeg.pagespeed.ce.L1OdXzzU7J.jpg", "");
        setContentPane(impanel);
        impanel.setLayout(new BorderLayout());
        contentPane.setLayout(new BorderLayout());
        JPanel defpanel11 = new JPanel();
        JPanel defpanel111 = new JPanel();
        defpanel11.setOpaque(false);
        defpanel111.setOpaque(false);

        defpanel11.setPreferredSize(new Dimension(30, 30));
        defpanel111.setPreferredSize(new Dimension(30, 30));
        contentPane.add(defpanel11, BorderLayout.SOUTH);
        impanel.add(defpanel111, BorderLayout.NORTH);


        JPanel defpanel22 = new JPanel();
        JPanel defpanel222 = new JPanel();
        defpanel22.setOpaque(false);
        defpanel222.setOpaque(false);

        defpanel22.setPreferredSize(new Dimension(30, 30));
        defpanel222.setPreferredSize(new Dimension(30, 30));
        impanel.add(defpanel22, BorderLayout.EAST);
        impanel.add(defpanel222, BorderLayout.WEST);
        impanel.add(contentPane, BorderLayout.CENTER);
        contentPane.setOpaque(false);
        setLocationRelativeTo(null);
        setResizable(false);
        buttonOpen.setIcon(new ImageIcon("1.png"));
        buttonPause.setIcon(new ImageIcon("2.png"));
        buttonPlay.setIcon(new ImageIcon("images/Stop.gif"));
        right.setIcon(new ImageIcon("4.png"));
        left.setIcon(new ImageIcon("3.png"));
        buttonOpen.setOpaque(false);
        buttonPlay.setOpaque(false);
        buttonPause.setOpaque(false);
        panel = new ImagePanel("Apple-Music.jpeg.pagespeed.ce.L1OdXzzU7J.jpg", "");
        panel.setOpaque(false);

        contentPane.add(panel, BorderLayout.CENTER);
        impanel.setOpaque(false);

        songs = new JComboBox(items);
        filename = String.valueOf(songs.getItemAt(0));
        JPanel panelsongs = new JPanel(new BorderLayout());
        panelsongs.setOpaque(false);
        panelsongs.add(openList, BorderLayout.WEST);
        panelsongs.add(open,BorderLayout.EAST);
        panelsongs.add(songs, BorderLayout.CENTER);
        openList.setOpaque(false);
        open.setOpaque(false);
        songs.setOpaque(false);
        openList.addActionListener(this);

        impanel.add(panelsongs, BorderLayout.SOUTH);
        JPanel defpanel = new JPanel();
        JPanel defpanel1 = new JPanel();
        defpanel.setOpaque(false);
        defpanel.setPreferredSize(new Dimension(50, 50));
        defpanel1.setOpaque(false);
        defpanel1.setPreferredSize(new Dimension(35, 50));
        contentPane.add(defpanel1,BorderLayout.WEST);
        contentPane.add(playerPanel,BorderLayout.NORTH);
        Actions();
        setVisible(true);
    }

    public void initPlayer() {
        playerPanel = new JPanel();
        playerPanel.setOpaque(false);
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setOpaque(false);

        buttonOpen.setFont(new Font("Sans", Font.BOLD, 14));
        buttonPlay.setFont(new Font("Sans", Font.BOLD, 14));
        buttonPlay.setEnabled(false);

        buttonPause.setFont(new Font("Sans", Font.BOLD, 14));
        buttonPause.setEnabled(false);

        labelTimeCounter.setFont(new Font("Sans", Font.BOLD, 12));
        labelDuration.setFont(new Font("Sans", Font.BOLD, 12));

        sliderTime.setPreferredSize(new Dimension(400, 20));
        sliderTime.setEnabled(false);
        sliderTime.setValue(0);
        sliderTime.setOpaque(false);
        JPanel panelTimer = new JPanel(new BorderLayout());
        panelTimer.setOpaque(false);
        panelTimer.add(labelTimeCounter,BorderLayout.WEST);
        panelTimer.add(labelDuration,BorderLayout.EAST);
        panelTimer.add(sliderTime, BorderLayout.CENTER);
        panelTimer.add(labelFileName,BorderLayout.SOUTH);
        panelButtons = new JPanel(new BorderLayout());
        panelButtons.add(buttonOpen,BorderLayout.CENTER);
        panelButtons.add(left,BorderLayout.WEST);
        panelButtons.add(right, BorderLayout.EAST);
        buttonOpen.setText("Play");
        right.setOpaque(false);
        left.setOpaque(false);
        panelButtons.setOpaque(false);
        playerPanel.add(panelButtons, BorderLayout.SOUTH);
        playerPanel.add(panelTimer, BorderLayout.CENTER);
        buttonOpen.addActionListener(this);
        buttonPlay.addActionListener(this);
        buttonPause.addActionListener(this);
        left.addActionListener(this);
        right.addActionListener(this);
    }
    public void Actions() {
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPause) {
                    resumePlaying();
                }
                if (isPlaying) {
                    stopPlaying();
                }
                openF();
            }
        });
        songs.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent ev) {
                        if (ev.getStateChange() == ItemEvent.SELECTED) {
                            filename = String.valueOf(songs.getSelectedItem());
                            System.out.println(filename);
                        }
                        int i=0;
                        player1.stop();
                        while (i < items.length){
                            if (filename==songs.getItemAt(i)){
                                panel = new ImagePanel(items1[i],"");
                                impanel = new ImPanel(items2[i],"");
                                repaint();
                                defoltPanel.add(buttonPause);
                                panelButtons.add(buttonOpen, BorderLayout.CENTER);
                                //buttonOpen.doClick();
                            }
                            i++;
                        }
                    }
                });

    }
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            if (button == buttonOpen) {
                if (isPause) {
                    resumePlaying();
                }
                if (isPlaying) {
                    stopPlaying();
                }

                try {
                    openFile();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            } else if (button == buttonPlay) {
                if (!isPlaying) {
                    try {
                        playBack();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                } else {
                    stopPlaying();
                }
            } else if (button == buttonPause) {
                if (!isPause) {
                    pausePlaying();
                } else {
                    resumePlaying();
                }
            } else if(button == left){
                int i = 0;
                while (i < items.length){
                    if (filename==songs.getItemAt(i)){
                        System.out.println(i);
                        player1.stop();
                        if(i>=1) {
                            songs.setSelectedIndex(i - 1);
                            break;
                        } else{
                            System.out.println(items.length - 1);
                            songs.setSelectedIndex(items.length-1);
                            break;
                        }
                    }
                    i++;
                }
                buttonOpen.doClick();

            } else if (button == right){
                int i = 0;
                while (i < items.length){
                    if (filename==songs.getItemAt(i)){
                        System.out.println(i);
                        player1.stop();
                        if(i<items.length-1) {
                            songs.setSelectedIndex(i + 1);
                            break;
                        } else{
                            System.out.println(items.length-1);
                            songs.setSelectedIndex(0);
                            break;
                        }
                    }
                    i++;
                }
                buttonOpen.doClick();
            } else if (button == openList){
                new PlayList();

            }
        }
    }

    public void openF(){
        JFileChooser fileChooser;

        if (lastOpenPath != null && !lastOpenPath.equals("")) {
            fileChooser = new JFileChooser(lastOpenPath);
        } else {
            fileChooser = new JFileChooser();
        }

        FileFilter wavFilter = new FileFilter() {
            @Override
            public String getDescription() {
                return "Sound file (*.WAV)";
            }

            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().toLowerCase().endsWith(".wav");
            }
        };


        fileChooser.setFileFilter(wavFilter);
        fileChooser.setDialogTitle("Open Audio File");
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userChoice = fileChooser.showOpenDialog(this);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            audioFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            lastOpenPath = fileChooser.getSelectedFile().getParent();
            if (isPlaying || isPause) {
                stopPlaying();
                while (player1.getAudioClip().isRunning()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            try {
                playBack();
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }
    }
    public  void openFile() throws AWTException {
        //audioFilePath = "sounds\\"+filename;
        if (isPlaying || isPause) {
            stopPlaying();
            while (player1.getAudioClip().isRunning()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        playBack();
    }
    private void playBack() throws AWTException {
        timer = new Timer(labelTimeCounter, sliderTime);
        timer.start();
        isPlaying = true;

        playbackThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    panelButtons.add(buttonPause,BorderLayout.CENTER);
                    defoltPanel.add(buttonOpen);
                    buttonPlay.setText("Stop");
                    buttonPlay.setEnabled(true);

                    buttonPause.setText("Pause");
                    buttonPause.setEnabled(true);

                    player1.load(audioFilePath);
                    timer.setAudioClip(player1.getAudioClip());
                    labelFileName.setText("Playing File: " + audioFilePath);
                    sliderTime.setMaximum((int) player1.getClipSecondLength());

                    labelDuration.setText(player1.getClipLengthString());
                    player1.play();

                    resetControls();

                } catch (UnsupportedAudioFileException ex) {
                    JOptionPane.showMessageDialog(Frame.this,
                            "The audio format is unsupported!", "Error", JOptionPane.ERROR_MESSAGE);
                    resetControls();
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    JOptionPane.showMessageDialog(Frame.this,
                            "Could not play the audio file because line is unavailable!", "Error", JOptionPane.ERROR_MESSAGE);
                    resetControls();
                    ex.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(Frame.this,
                            "I/O error while playing the audio file!", "Error", JOptionPane.ERROR_MESSAGE);
                    resetControls();
                    ex.printStackTrace();
                }
            }
        });
        playbackThread.start();

    }
    private void stopPlaying() {
        isPause = false;
        buttonPause.setText("Pause");
        buttonPause.setEnabled(false);
        timer.reset();
        timer.interrupt();
        player1.stop();
        playbackThread.interrupt();
    }
    private void pausePlaying() {
        buttonPause.setText("Resume");
        buttonPause.setIcon(new ImageIcon("1.png"));
        isPause = true;
        player1.pause();
        timer.pauseTimer();
        playbackThread.interrupt();
    }
    private void resumePlaying() {
        buttonPause.setText("Pause");
        buttonPause.setIcon(new ImageIcon("2.png"));
        isPause = false;
        player1.resume();
        timer.resumeTimer();
        playbackThread.interrupt();
    }
    private void resetControls() {
        timer.reset();
        timer.interrupt();
        buttonPlay.setText("Play");
        buttonPause.setEnabled(false);

        isPlaying = false;
    }


    private class ImageAction implements ActionListener {
        public ImageAction(File im){
            changeImage = im;
        }

        public void actionPerformed(ActionEvent event) {
            try {
                ImagePanel.img = ImageIO.read(changeImage);
                repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private File changeImage;
    }
}
