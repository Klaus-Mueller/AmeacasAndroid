package com.example.pc.ameacasambientais;

import android.app.Activity;
import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cadastraAmeacas(View v) {
        Intent intent = new Intent(this, CrudAmeacas.class);
        Bundle b = new Bundle();
        b.putInt("acao", 1);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void listaAmeacas (View v) {
        Intent i =  new Intent(this, ListaAmeacas.class);
        startActivity(i);
    }
}
