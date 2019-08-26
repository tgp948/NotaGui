package br.com.gabthi.notagui.procedureaudio;

import android.media.MediaRecorder;
import android.provider.MediaStore;

public class CapitaAudio {
    MediaRecorder media = new MediaRecorder();


    public void play(){

        try{

                media.setAudioSource(MediaRecorder.AudioSource.MIC);
                media.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                media.setAudioSource(MediaRecorder.OutputFormat.AMR_WB);
                media.setOutputFile("audioNotaGui.mp3");
                media.prepare();
                media.start();

            }catch(
            Exception e)

            {
                e.printStackTrace();
            }

    }

    public void stop(){
        media.stop();
    }

}
