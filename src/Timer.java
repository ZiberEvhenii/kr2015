import javax.sound.sampled.Clip;
import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Timer extends Thread{
    private DateFormat dateFormater = new SimpleDateFormat("HH:mm:ss");
    private boolean isRunning = false;
    private boolean isPause = false;
    private boolean isReset = false;
    private long startTime;
    private long pauseTime;

    private JLabel labelRecordTime;
    private JSlider slider;
    private Clip audioClip;
    private int currentSecond;
    public void setAudioClip(Clip audioClip) {
        this.audioClip = audioClip;
    }
    Timer(JLabel labelRecordTime, JSlider slider) {
        this.labelRecordTime = labelRecordTime;
        this.slider = slider;
    }
    public void run() {
        isRunning = true;

        startTime = System.currentTimeMillis();

        while (isRunning) {


            if (!isPause) {
                if (audioClip != null && audioClip.isRunning()) {
                    labelRecordTime.setText(toTimeString());
                     currentSecond = (int) audioClip.getMicrosecondPosition() / 1000000;
                    slider.setValue(currentSecond);
                }
            } else {
                pauseTime += 100;
                //pauseTime=currentSecond;
            }


            if (isReset) {
                slider.setValue(0);
                labelRecordTime.setText("00:00:00");
                isRunning = false;
                break;
            }
        }
    }

    void reset() {
        isReset = true;
        isRunning = false;
    }
    void pauseTimer() {
        isPause = true;
        System.out.println(toTimeString());
    }
    void resumeTimer() {
        isPause = false;
        System.out.println(currentSecond);
    }

    private String toTimeString() {
        long now = System.currentTimeMillis();
        Date current = new Date(now - startTime - pauseTime);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timeCounter = dateFormater.format(current);
        return timeCounter;
    }
}
