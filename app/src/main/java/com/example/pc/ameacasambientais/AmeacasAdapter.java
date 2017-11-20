package com.example.pc.ameacasambientais;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by pc on 19/11/2017.
 */

public class AmeacasAdapter extends BaseAdapter {

    Context ctx;
    AmeacaDB db;
    LayoutInflater inflater;

    public AmeacasAdapter(Context ctx) {
        this.ctx = ctx;
        db = new AmeacaDB(ctx);
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return db.getAmeacas().size();
    }

    @Override
    public Object getItem(int i) {
        return db.getAmeacas().get(i);
    }

    @Override
    public long getItemId(int i) {
        return db.getAmeacas().get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.ameaca_item, null, false);
        Ameaca tmp = db.getAmeacas().get(i);
        TextView itAmeaca = (TextView) v.findViewById(R.id.itAmeaca);
        TextView itEndereco = (TextView) v.findViewById(R.id.itEndereco);
        TextView itBairro = (TextView) v.findViewById(R.id.itBairro);
        TextView itImpacto = (TextView) v.findViewById(R.id.itImpacto);
        itAmeaca.setText(tmp.getAmeaca());
        itEndereco.setText(tmp.getEndereco());
        itBairro.setText(tmp.getBairro());
        itImpacto.setText(tmp.getImpacto());
        return v;
    }
}
