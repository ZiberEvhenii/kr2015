import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class PlayList extends Frame implements ActionListener, Music{
    public static String path = "songs.txt";
    Scanner sc = new  Scanner(System.in);
    JPanel panelList ;
    JList list;
    private int i = 0;
    Frame f ;
    public PlayList(){

        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 400, 450);
        panelList = new JPanel(new BorderLayout(5,5));
        panelList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        setContentPane(panelList);
        setVisible(true);

        DefaultListModel listModel = new DefaultListModel();
        list = new JList(listModel);

        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(path,"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String l;
        try {
            while((l = file.readLine()) != null){
                listModel.addElement(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        list.setSelectedIndex(0);
        list.setFocusable(false);
        panelList.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 0));
        panelList.add(buttonsPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add");
        addButton.setFocusable(false);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openF();
                //String element = "Element " + i++;
                listModel.addElement(audioFilePath);
                try {
                    addToFile(audioFilePath, path);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                int index = listModel.size() - 1;
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        });
        buttonsPanel.add(addButton);


        final JButton removeButton = new JButton("Delete");
        removeButton.setFocusable(false);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    Delete(list.getSelectedIndex());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                listModel.remove(list.getSelectedIndex());

            }

        });
        buttonsPanel.add(removeButton);

        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (list.getSelectedIndex() >= 0) {
                    removeButton.setEnabled(true);
                    //player1.stop();
                    int n = list.getSelectedIndex();
                    try {
                        audioFilePath = getString(n);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("KEK");

                    //defoltPanel.add(buttonPause);
                   // panelButtons.add(buttonOpen, BorderLayout.CENTER);
                    buttonOpen.doClick();


                } else {
                    removeButton.setEnabled(false);
                }
            }
        });
    }



    /*private void OpenF(){
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
            /*if (isPlaying || isPause) {
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
    }*/

    public void actionPerformed(ActionEvent e) {

    }


    public static void addToFile(String s, String path)throws Exception{
        RandomAccessFile file = null;
        file = new RandomAccessFile(path,"rw");
        file.seek(file.length());
        file.writeBytes(s + "\r\n");
        file.close();
    }




    public void Delete(int n1)throws Exception{
        RandomAccessFile file = null;
        file = new RandomAccessFile(path,"rw");
        String l;
        String str33="";
        int in=0;

        file.seek(0);
        while((l = file.readLine()) != null){

            if (in != n1) str33 += l + "\r\n";
            in++;

        }
        file.seek(0);
        file.setLength(0);
        file.writeBytes(str33);
    }
    public String getString(int n1) throws Exception{
        RandomAccessFile file = null;
        file = new RandomAccessFile(path,"rw");
        String l;
        int in=0;
        String s=null;
        file.seek(0);
        while((l = file.readLine()) != null){

            if (in == n1) s =l;
            in++;

        }
        return s;

    }
}
