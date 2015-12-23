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
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Frame extends JFrame implements ActionListener, Music {
    private JPanel playerPanel;
    private String filename;
    private Player player1 = new Player();
    private Thread playbackThread;
    private Timer timer;

    private boolean isPlaying = false;
    private boolean isPause = false;

    private String audioFilePath;

    private JLabel labelFileName = new JLabel("Playing File:");
    private JLabel labelTimeCounter = new JLabel("00:00:00");
    private JLabel labelDuration = new JLabel("00:00:00");
    private String lastOpenPath;

    private JButton buttonOpen = new JButton();
    private JButton buttonPlay = new JButton();
    private JButton buttonPause = new JButton();
    private JButton open = new JButton("Open");

    private JSlider sliderTime = new JSlider();
    private ImagePanel panel;
    private JComboBox songs;
    private JTextPane textArea;

    public Frame() {
        initPlayer();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 400, 450);
        JPanel contentPane = new JPanel(new BorderLayout());
        //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setBackground(Color.cyan);
        //contentPane.setOpaque(false);
        setLocationRelativeTo(null);
        setResizable(false);
        //contentPane.setLayout(null);
        buttonOpen.setIcon(new ImageIcon("images/Play.gif"));
        buttonPause.setIcon(new ImageIcon("images/Pause.png"));
        buttonPlay.setIcon(new ImageIcon("images/Stop.gif"));

        panel = new ImagePanel("Apple-Music.jpeg.pagespeed.ce.L1OdXzzU7J.jpg", "");
        //panel.setBackground(Color.yellow);
        panel.setOpaque(false);
        //panel.setBounds(0, 0, 700, 500);

        contentPane.add(panel,BorderLayout.CENTER);
        //contentPane.setBackground(Color.yellow);
        //panel.setLayout(null);

        songs = new JComboBox(items);
        filename = String.valueOf(songs.getItemAt(0));
       // songs.setBounds(231, 112, 250, 20);
        //open.setBounds(500,112,60,20);

        JPanel panelsongs = new JPanel(new BorderLayout());
        //panelsongs.setBackground(Color.yellow);
        panelsongs.setOpaque(false);
        //Dimension size1 = new Dimension(70,25);
        //open.setPreferredSize(size1);
        panelsongs.add(open,BorderLayout.EAST);
        panelsongs.add(songs, BorderLayout.CENTER);

        contentPane.add(panelsongs, BorderLayout.SOUTH);
        JPanel defpanel = new JPanel();
        JPanel defpanel1 = new JPanel();
        defpanel.setOpaque(false);
        defpanel.setPreferredSize(new Dimension(50, 50));
        defpanel1.setOpaque(false);
        defpanel1.setPreferredSize(new Dimension(50, 50));
        contentPane.add(defpanel,BorderLayout.EAST);
        contentPane.add(defpanel1,BorderLayout.WEST);
        //contentPane.add(panel,BorderLayout.SOUTH);

        //textArea = new JTextPane();

        //JScrollPane text = new JScrollPane(textArea);
        //text.setBounds(10, 143, 652, 185);
        //panel.add(text);

        //JLabel lblNewLabel = new JLabel("Information:");
        //lblNewLabel.setFont(new Font("Verdana", Font.ITALIC, 29));
        //lblNewLabel.setBounds(10, 82, 211, 64);
        //panel.add(lblNewLabel);

        //JLabel Media = new JLabel("MediaPlayer");
        //Media.setFont(new Font("Verdana", Font.ITALIC, 48));
        //Media.setBounds(200, 15, 300, 64);
        //panel.add(Media);

        //playerPanel.setBounds(10, 338, 654, 101);
        contentPane.add(playerPanel,BorderLayout.NORTH);
        //ImagePanel imagesongs = new ImagePanel("Apple-Music.jpeg.pagespeed.ce.L1OdXzzU7J","");
        //contentPane.add(imagesongs, BorderLayout.CENTER);
        Actions();
        setVisible(true);
    }
    //255, 108, 0
    public void initPlayer() {
        playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        //playerPanel.setBackground(Color.red);
        playerPanel.setOpaque(false);
      //  playerPanel.setBackground(new Color(255, 34, 0));
        //GridBagConstraints constraints = new GridBagConstraints();
        //constraints.insets = new Insets(5, 5, 5, 5);
        //constraints.anchor = GridBagConstraints.WEST;

        buttonOpen.setFont(new Font("Sans", Font.BOLD, 14));
        //buttonOpen.setPreferredSize(new Dimension(25, 25));
        buttonPlay.setFont(new Font("Sans", Font.BOLD, 14));
        buttonPlay.setEnabled(false);
        //buttonPlay.setPreferredSize(new Dimension(25, 25));


        buttonPause.setFont(new Font("Sans", Font.BOLD, 14));
        buttonPause.setEnabled(false);
       // buttonPause.setPreferredSize(new Dimension(25, 25));

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

        //constraints.gridx = 0;
        //constraints.gridy = 0;
        //constraints.gridwidth = 2;
        //playerPanel.add(labelFileName, constraints);

        //constraints.anchor = GridBagConstraints.CENTER;
        //constraints.gridy = 1;
        //constraints.gridwidth = 1;
        //playerPanel.add(labelTimeCounter, constraints);

        //constraints.gridx = 1;
        //playerPanel.add(sliderTime, constraints);

        //constraints.gridx = 2;
        //playerPanel.add(labelDuration, constraints);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 5));
     //   panelButtons.setBackground(new Color(255, 34, 0));
        panelButtons.add(buttonOpen);
        panelButtons.add(buttonPlay);
        panelButtons.add(buttonPause);
        //panelButtons.setBackground(Color.yellow);
        panelButtons.setOpaque(false);
        playerPanel.add(panelButtons, BorderLayout.SOUTH);
        playerPanel.add(panelTimer, BorderLayout.CENTER);
        //constraints.gridwidth = 3;
        //constraints.gridx = 0;
        //constraints.gridy = 2;
        //playerPanel.add(panelButtons, constraints);

        buttonOpen.addActionListener(this);
        buttonPlay.addActionListener(this);
        buttonPause.addActionListener(this);
    }
    public void Actions() {
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //textArea.setText("");
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
                        while (i < items.length){
                            if (filename==songs.getItemAt(i)){
                                panel = new ImagePanel(items1[i],"");
                                repaint();
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
                //textArea.setText("");
                if (isPause) {
                    resumePlaying();
                }
                if (isPlaying) {
                    stopPlaying();
                }
                String name ="sounds\\"+String.valueOf(songs.getSelectedItem())+".txt";
                System.out.println(name);
                File file = new File(name);
                try {
                    Scanner scan = new Scanner(new FileReader(file));
                    while (scan.hasNext())
                        textArea.setText(textArea.getText() + scan.nextLine() + "\n");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println();
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
            }
        }
    }

    private void openF(){
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
    private void openFile() throws AWTException {
        audioFilePath = "sounds\\"+filename;
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
        isPause = true;
        player1.pause();
        timer.pauseTimer();
        playbackThread.interrupt();
    }
    private void resumePlaying() {
        buttonPause.setText("Pause");
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