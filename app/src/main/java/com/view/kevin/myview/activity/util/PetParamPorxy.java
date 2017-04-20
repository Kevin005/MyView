package com.view.kevin.myview.activity.util;

/**
 * Created by Kevin on 2017/4/15.
 */

public class PetParamPorxy {
    private static PetVoiceThread petVoiceThread;
    private static PetParamPorxy petParamPorxy;

    public static PetParamPorxy getInstant() {
        if (petParamPorxy == null) {
            petParamPorxy = new PetParamPorxy();
        }
        return petParamPorxy;
    }

    public interface UserVoiceLinstener {
        void linstening();

        void pause();
    }

    //启动监听
    public void startUserVoiceLinstener(UserVoiceLinstener userVoiceLinstener) {
        if (petVoiceThread == null) {
            petVoiceThread = new PetVoiceThread(userVoiceLinstener);
            if (!petVoiceThread.isAlive()) {
                petVoiceThread.start();
            }
        } else if (petVoiceThread != null && !petVoiceThread.isAlive() && petVoiceThread.getState() != Thread.State.RUNNABLE) {
            petVoiceThread.is_listening_user_vocie = true;
            petVoiceThread.start();
        } else {
            petVoiceThread.is_listening_user_vocie = true;
        }
    }

    //结束监听
    public void overUserVoiceLinstener() {
        if (petVoiceThread != null && petVoiceThread.isAlive()) {
            petVoiceThread.is_listening_user_vocie = false;
            petVoiceThread = null;
        }
    }
}
