package com.view.kevin.myview.activity.util;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by Kevin on 2017/4/15.
 */

public class PetVoiceThread extends Thread {
    private int SAMPLE_RATE_IN_HZ = 8000;
    private AudioRecord ar;
    private int bs = 100;
    private int number = 1;
    private int tal = 1;
    private long currenttime;
    private long endtime;
    private long time = 1;
    private int BLOW_ACTIVI = 2600;//阈值，到达该值之后触发事件
    PetParamPorxy.UserVoiceLinstener userVoiceLinstener;
    public boolean is_listening_user_vocie = true;//是否监听用户声音,默认监听

    public PetVoiceThread(PetParamPorxy.UserVoiceLinstener userVoiceLinstener) {
        super();
        bs = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        ar = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bs);
        this.userVoiceLinstener = userVoiceLinstener;
    }

    @Override
    public void run() {
        try {
            ar.startRecording();
            // 用于读取的 buffer
            byte[] buffer = new byte[bs];
            while (is_listening_user_vocie) {
                number++;
                currenttime = System.currentTimeMillis();
                int r = ar.read(buffer, 0, bs) + 1;
                int v = 0;
                for (int i = 0; i < buffer.length; i++) {
                    v += (buffer[i] * buffer[i]);
                }
                int value = Integer.valueOf(v / (int) r);
                tal = tal + value;
                endtime = System.currentTimeMillis();
                time = time + (endtime - currenttime);
                if (time >= 500 || number > 5) {
                    int total = tal / number;
                    if (total > BLOW_ACTIVI) {
                        if (userVoiceLinstener != null) {
                            userVoiceLinstener.linstening();
                        }
                        number = 1;
                        tal = 1;
                        time = 1;
                    } else {
                        if (userVoiceLinstener != null) {
                            userVoiceLinstener.pause();
                        }
                    }
                }
            }
            ar.stop();
            ar.release();
            bs = 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
