package terminal1.a4.loginui;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import terminal1.a4.listanegocios.nuevoperfil;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        fichero();
    }

    protected void fichero() {
        File fichero = new File("id.json");

        if (fichero.exists()){
            Intent intent2 = new Intent(StartActivity.this, mis_vuelos.class);
            startActivity(intent2);
        }
        else{
            Intent intent1 = new Intent(StartActivity.this, Login.class);
            startActivity(intent1);
        }
    }

}

