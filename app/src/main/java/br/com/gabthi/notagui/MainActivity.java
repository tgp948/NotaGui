package br.com.gabthi.notagui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.gabthi.notagui.procedureaudio.CapitaAudio;

public class MainActivity extends AppCompatActivity {
    private CapitaAudio capita;
    private Boolean flagCap = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button butao = findViewById(R.id.button);
        final TextView texto = findViewById(R.id.corpoTexto);
        butao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flagCap)
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
                   }


            }
        });
    }
}
