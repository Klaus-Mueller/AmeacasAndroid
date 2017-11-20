package com.example.pc.ameacasambientais;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 12/11/2017.
 */

public class AmeacaDB {
    // Nome do arquivo Db
    private static final String NOME_ARQUIVO = "ameacas.db";
    // Nome da tabela de ameaças
    private static final String TAB_AMEACAS = "AMEACAS";
    // Coluna Identificadora
    private static final String COL_ID = "ID";
    // Coluna da descrição da ameaça
    private static final String COL_AMEACA = "AMEACA";
    // Coluna do endereço da ameaça
    private static final String COL_ENDERECO = "ENDERECO";
    // Coluna do Bairro da ameaça
    private static final String COL_BAIRRO = "BAIRRO";
    // Coluna do valor de impacto da ameaça
    private static final String COL_IMPACTO = "IMPACTO";
    // SQL de criação da tabela
    private static final String SQL_TAB_AMEACAS = "create table " +
            TAB_AMEACAS + "(" + COL_ID +
            " integer primary key autoincrement," +
            COL_AMEACA + " text not null," +
            COL_ENDERECO + " text not null," +
            COL_BAIRRO + " text not null," +
            COL_IMPACTO + " text not null)";
    // SQL de update/Delete das ameaças
    private static final String SQL_WHERE_DELETE_UPDATE_AMEACA = COL_ID + " = ?";
    // SQL para buscar ameaças por ID
    private static final String SQL_GET_FIRST_ELEMENT_BY_ID = "SELECT * FROM " + TAB_AMEACAS + " WHERE " + COL_ID + " = ?";

    Context ctx;
    private ameacasHelper dbHelper;
    private SQLiteDatabase db;

    // Construtor padrão da classe
    public AmeacaDB(Context ctx){
        this.ctx = ctx;
        dbHelper = new ameacasHelper(ctx, NOME_ARQUIVO, null, 13);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * Adiciona novas ameças na base de dados
     *
     * @param a Objeto com dados da ameça
     */
    public void addAmeaca(Ameaca a){
        ContentValues values = new ContentValues();
        values.put(COL_AMEACA, a.getAmeaca());
        values.put(COL_ENDERECO, a.getEndereco());
        values.put(COL_BAIRRO, a.getBairro());
        values.put(COL_IMPACTO, a.getImpacto());
        db.insert(TAB_AMEACAS, null, values);
    }


    /**
     * Deleta ameaça da base de dados
     *
     * @param a Objeto com dados da ameça
     */
    public void deleteAmeaca(Ameaca a) {
        String[] values = new String[1];
        values[0] = a.getId().toString();
        db.delete(TAB_AMEACAS, SQL_WHERE_DELETE_UPDATE_AMEACA, values);
    }

    /**
     * Aletra ameaça na base de dados
     *
     * @param a Objeto com dados da ameça
     */
    public void update(Ameaca a) {
        String[] whereValues = new String[1];
        whereValues[0] = a.getId().toString();
        ContentValues values = new ContentValues();
        values.put(COL_AMEACA, a.getAmeaca());
        values.put(COL_ENDERECO, a.getEndereco());
        values.put(COL_BAIRRO, a.getBairro());
        values.put(COL_IMPACTO, a.getImpacto());
        db.update(TAB_AMEACAS, values, SQL_WHERE_DELETE_UPDATE_AMEACA, whereValues);

    }

    /**
     * Retorna ameça
     *
     * @param id Identificador da ameça
     * @return Ameaca
     */
    public Ameaca getAmeaca(int id) {
        Ameaca ameaca =  null;
        String[] values = new String[1];
        values[0] = String.valueOf(id);
        Cursor cursor = db.rawQuery(SQL_GET_FIRST_ELEMENT_BY_ID, values);
        if (cursor.moveToFirst())
        {
            ameaca = new Ameaca();
            ameaca.setId(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)));
            ameaca.setAmeaca(
                    cursor.getString(cursor.getColumnIndex(COL_AMEACA)));
            ameaca.setEndereco(
                    cursor.getString(cursor.getColumnIndex(COL_ENDERECO)));
            ameaca.setBairro(
                    cursor.getString(cursor.getColumnIndex(COL_BAIRRO)));
            ameaca.setImpacto(
                    cursor.getString(cursor.getColumnIndex(COL_IMPACTO)));
        }
        cursor.close();
        return ameaca;
    }

    /**
     * Retorna as ameaças da base de dados
     *
     * @return List<Ameaca>
     */
    public List<Ameaca> getAmeacas(){
        List<Ameaca> ameacas = new ArrayList<>();
        String[] campos = {COL_ID, COL_AMEACA, COL_ENDERECO, COL_BAIRRO, COL_IMPACTO};
        Cursor cursor = db.query(TAB_AMEACAS,
                campos,
                null,
                null,
                null,
                null,
                COL_ID);
        cursor.moveToFirst();
        if(cursor.getCount() == 0) {
            return ameacas;
        }
        Ameaca tmp;
        do{
            tmp = new Ameaca();
            tmp.setId(
                    cursor.getInt(cursor.getColumnIndex(COL_ID)));
            tmp.setAmeaca(
                    cursor.getString(cursor.getColumnIndex(COL_AMEACA)));
            tmp.setEndereco(
                    cursor.getString(cursor.getColumnIndex(COL_ENDERECO)));
            tmp.setBairro(
                    cursor.getString(cursor.getColumnIndex(COL_BAIRRO)));
            tmp.setImpacto(
                    cursor.getString(cursor.getColumnIndex(COL_IMPACTO)));
            ameacas.add(tmp);
        }while (cursor.moveToNext());
        return ameacas;
    }

    /**
     * Classe helper de ameaças
     */
    private static class ameacasHelper extends SQLiteOpenHelper {

        public ameacasHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_TAB_AMEACAS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TAB_AMEACAS);
            onCreate(sqLiteDatabase);
        }
    }

}
