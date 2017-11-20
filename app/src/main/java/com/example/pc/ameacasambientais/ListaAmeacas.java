package com.example.pc.ameacasambientais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by pc on 19/11/2017.
 */
public class ListaAmeacas extends Activity {

    ListView listaAmeacas;
    AmeacasAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_ameacas);
        listaAmeacas = (ListView) findViewById(R.id.listAmeacas);
        adapter = new AmeacasAdapter(this.getApplicationContext());
        if(adapter.getCount() == 0) {
            Toast.makeText(this, "Não existem mais Ameaças cadastradas!!", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        listaAmeacas.setAdapter(adapter);
        listaAmeacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CrudAmeacas.class);
                Bundle b = new Bundle();
                b.putInt("acao", 2);
                b.putLong("id", l);
                intent.putExtras(b);
                startActivityForResult(intent, 200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200) {
            adapter.notifyDataSetChanged();
        }
    }
}
