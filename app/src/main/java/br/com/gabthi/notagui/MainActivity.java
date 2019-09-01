package br.com.gabthi.notagui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import br.com.gabthi.notagui.procedureaudio.CapitaAudio;

public class MainActivity extends AppCompatActivity {
    private CapitaAudio capita;
    private Boolean flagCap = false;

    String pathSave = "";
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlay;

    final int REQUEST_PERMISSION_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkPermissao())
            requerePermissao();


        Button butao = findViewById(R.id.button);
        Button play = findViewById(R.id.btnplayer);
        final TextView texto = findViewById(R.id.corpoTexto);
        butao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if(flagCap)
                   {
                       flagCap = false;
                       texto.setText("Capitação pausada.");
                       capita.stop();
                   }else

                   {
                       flagCap = true;
                       texto.setText("Capitando audio.");
                       capita = new CapitaAudio();
                       capita.play();
                   }*/

                if (checkPermissao()) {
                    if(flagCap)
                        flagCap = false;
                    else
                        flagCap = true;

                    if (flagCap) {

                        pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.3gp";
                        setupMediaRecorder();

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, "Gravando...", Toast.LENGTH_SHORT).show();

                    } else {
                        mediaRecorder.stop();
                        Toast.makeText(MainActivity.this, "Gravação encerrada.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mediaPlay = new MediaPlayer();

                try{
                    mediaPlay.setDataSource(pathSave);
                    mediaPlay.prepare();
                } catch (IOException e){
                    e.printStackTrace();
                }
                mediaPlay.start();
                Toast.makeText(MainActivity.this, "Tocando...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean checkPermissao(){
        int escreverStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int gravarAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return escreverStorage == PackageManager.PERMISSION_GRANTED && gravarAudio == PackageManager.PERMISSION_GRANTED;
    }

    private void requerePermissao(){
        ActivityCompat.requestPermissions(this, new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        }, REQUEST_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_PERMISSION_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permissão consedida.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"Permissão negada.", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private void setupMediaRecorder(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);

    }
}
