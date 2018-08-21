package com.emaraic.recorder;

import com.esotericsoftware.tablelayout.swing.Table;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class CamRecorder extends JFrame {

    private JLabel recordingLabel;
    private JPanel canvas;
    private static FFmpegFrameRecorder recorder = null;
    private static OpenCVFrameGrabber grabber = null;
    private static final int WEBCAM_DEVICE_INDEX = 0;
    private static final int CAPTUREWIDTH = 600;
    private static final int CAPTUREHRIGHT = 600;
    private static final int FRAME_RATE = 30;
    private static final int GOP_LENGTH_IN_FRAMES = 60;
    private volatile boolean runnable = true;
    private static final long serialVersionUID = 1L;
    private Catcher cat;
    private Thread catcher;

    public CamRecorder() {
        grabber = new OpenCVFrameGrabber(WEBCAM_DEVICE_INDEX);
        cat = new Catcher();

        setTitle("Camera Recorder");
        setSize(1000, 1100);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        recordingLabel = new JLabel("  ");
        canvas = new JPanel();
        Table table = new Table();
        table.pad(40);
        getContentPane().add(table);
        canvas.setBorder(BorderFactory.createEtchedBorder());
        table.addCell(canvas).width(CAPTUREWIDTH).height(CAPTUREHRIGHT);
        table.row();
        table.addCell(recordingLabel);
        table.row();
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
        setResizable(false);
        setAlwaysOnTop(true);

    }

    public void startRecording() {
        catcher = new Thread(cat);
        catcher.start();
        runnable = true;
        recordingLabel.setText("<html><font color='red'>Recording ...</font></html>");
    }

    public void stopRecording() {
        try {
            catcher.interrupt();
            cat = null;
            recorder.stop();
            grabber.stop();
            runnable = false;
            grabber.close();
        } catch (Exception | FrameGrabber.Exception ex) {
            Logger.getLogger(CamRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setFrameEnable() {
        setVisible(true);
    }

    public void setFrameDisable() {
        setVisible(false);
    }

    class Catcher implements Runnable {

        @Override
        public void run() {
            synchronized (this) {
                // while (runnable) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    String fileName = calendar.getTime().toString();
                    fileName = fileName.replaceAll("[^0-9a-zA-Z \" \"]", " ");
                    grabber.setImageWidth(CAPTUREWIDTH);
                    grabber.setImageHeight(CAPTUREHRIGHT);
                    grabber.start();
                    recorder = new FFmpegFrameRecorder(
                            fileName + ".mp4",
                            CAPTUREWIDTH, CAPTUREHRIGHT, 2);
                    recorder.setInterleaved(true);
                    // video options //
                    recorder.setVideoOption("tune", "zerolatency");
                    recorder.setVideoOption("preset", "ultrafast");
                    recorder.setVideoOption("crf", "28");
                    recorder.setVideoBitrate(2000000);
                    recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
                    recorder.setFormat("mp4");
                    recorder.setFrameRate(FRAME_RATE);
                    recorder.setGopSize(GOP_LENGTH_IN_FRAMES);
                    // audio options //
                    recorder.setAudioOption("crf", "0");
                    recorder.setAudioQuality(0);
                    recorder.setAudioBitrate(192000);
                    recorder.setSampleRate(44100);
                    recorder.setAudioChannels(2);
                    recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);

                    recorder.start();

                    Frame capturedFrame = null;
                    Java2DFrameConverter paintConverter = new Java2DFrameConverter();
                    long startTime = System.currentTimeMillis();
                    long frame = 0;
                    while ((capturedFrame = grabber.grab()) != null && runnable) {
                        BufferedImage buff = paintConverter.getBufferedImage(capturedFrame, 1);
                        Graphics g = canvas.getGraphics();
                        g.drawImage(buff, 0, 0, CAPTUREWIDTH, CAPTUREHRIGHT, 0, 0, buff.getWidth(), buff.getHeight(), null);
                        recorder.record(capturedFrame);
                        frame++;
                        long waitMillis = 1000 * frame / FRAME_RATE - (System.currentTimeMillis() - startTime);
                        while (waitMillis <= 0) {
                            // If this error appeared, better to consider lower FRAME_RATE.
//                            System.out.println("[ERROR] grab image operation is too slow to encode, skip grab image at frame = " + frame + ", waitMillis = " + waitMillis);
                            recorder.record(capturedFrame);  // use same capturedFrame for fast processing.
                            frame++;
                            waitMillis = 1000 * frame / FRAME_RATE - (System.currentTimeMillis() - startTime);
                        }
                        //System.out.println("frame " + frame + ", System.currentTimeMillis() = " + System.currentTimeMillis() + ", waitMillis = " + waitMillis);
                        Thread.sleep(waitMillis);
                    }
                } catch (FrameGrabber.Exception | InterruptedException | Exception ex) {
                    Logger.getLogger(CamRecorder.class.getName()).log(Level.SEVERE, null, ex);
                }
                //}//end of while

                //}//end of while
            }
        }
    }

}
