package com.example.pc.ameacasambientais;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.annotation.AnnotationTypeMismatchException;

/**
 * Controlador de inserção e alteração de ameaças
 */

public class CrudAmeacas extends Activity {

    private static int INCLUSAO_AMEACA = 1;
    private static int ALTERACAO_EXCLUSAO_AMEACA = 2;

    Spinner levelAmeacas;
    EditText descricaoAmeaca;
    EditText endereco;
    EditText bairro;
    EditText identificador;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_ameacas);
        // Cria lista do nivel das ameaças
        createAmeacasLevel();
        descricaoAmeaca = (EditText) findViewById(R.id.txtAmeaca);
        endereco = (EditText) findViewById(R.id.txtEndereco);
        bairro = (EditText) findViewById(R.id.txtBairro);
        identificador = (EditText) findViewById(R.id.txtIdentificador);
        Bundle b = getIntent().getExtras();
        // Se a ação executada for de manutenção executa a rotina de manutenção
        if(b != null && b.getInt("acao") == ALTERACAO_EXCLUSAO_AMEACA && b.getLong("id") != 0){
            manutecaoAmeaca((int) b.getLong("id"));
        }
    }

    /**
     * Cria elemento para selecionar nivel da ameaça cadastrada
     */
    protected void createAmeacasLevel() {
        // Cria lista do nivel das ameaças
        levelAmeacas = (Spinner) findViewById(R.id.ameacasNivel);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.ameacas_level, android.R.layout.simple_spinner_item);
        levelAmeacas.setAdapter(adapter);
    }

    /**
     * Executa o a ação de manutenção da ameaça
     */
    public void manutecaoAmeaca(int id) {
        button = (Button) findViewById(R.id.bCrud);
        button.setText("Alterar");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar();
            }
        });
        Button bDelete = (Button) findViewById(R.id.bDelete);
        bDelete.setVisibility(View.VISIBLE);
        AmeacaDB ameacaDb =  new AmeacaDB(getApplicationContext());
        Ameaca ameaca =  ameacaDb.getAmeaca(id);
        // Se não encontrar a ameaça
        if(ameaca == null) {
            Toast.makeText(this, "Ocorrreu um erro na durante a manutenção! Desculpe o transtorno", Toast.LENGTH_SHORT).show();
            return;
        }
        descricaoAmeaca.setText(ameaca.getAmeaca());
        endereco.setText(ameaca.getEndereco());
        bairro.setText(ameaca.getBairro());
        identificador.setText(ameaca.getId().toString());
        int selectedItem = Integer.parseInt(ameaca.getImpacto()) - 1;
        levelAmeacas.setSelection(selectedItem);
    }

    /**
     * Executa a gravação da nova ameaça cadastrada
     * @param view
     */
    public void cadastrar(View view) {
        // Verifica se existe dados não preenchidos no formulário
        if(checkEmptyElements()) {
            return;
        }
        try{
            // Cria elemento e grava
            Ameaca ameaca =  new Ameaca();
            ameaca.setAmeaca(descricaoAmeaca.getText().toString());
            ameaca.setEndereco(endereco.getText().toString());
            ameaca.setBairro(bairro.getText().toString());
            ameaca.setImpacto(levelAmeacas.getSelectedItem().toString());
            AmeacaDB ameacaDb =  new AmeacaDB(getApplicationContext());
            ameacaDb.addAmeaca(ameaca);
        }catch (Exception ex) {
            Log.d("ERROR AMEACA:", ex.toString());
            Toast.makeText(this, "Ocorrreu um erro na durante a manutenção! Desculpe o transtorno", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        Toast.makeText(this, "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    /**
     * Executa a alteração da ameaça
     */
    public void alterar() {
        // Verifica se existe dados não preenchidos no formulário
        if(checkEmptyElements()) {
            return;
        }
        try{
            // Cria elemento e grava
            Ameaca ameaca =  new Ameaca();
            ameaca.setAmeaca(descricaoAmeaca.getText().toString());
            ameaca.setEndereco(endereco.getText().toString());
            ameaca.setBairro(bairro.getText().toString());
            ameaca.setImpacto(levelAmeacas.getSelectedItem().toString());
            ameaca.setId(Integer.parseInt(identificador.getText().toString()));
            AmeacaDB ameacaDb =  new AmeacaDB(getApplicationContext());
            ameacaDb.update(ameaca);
        }catch (Exception ex) {
            Log.d("ERROR AMEACA:", ex.toString());
            Toast.makeText(this, "Erro na alteração!!", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        Toast.makeText(this, "Ameaça: " + descricaoAmeaca.getText().toString() + " alterada com sucesso!!", Toast.LENGTH_SHORT).show();
        setResult(200);
        this.finish();
    }

    /**
     * Retorna se não definiu valor em algum dos campos
     *
     * @return Boolean
     */
    private boolean checkEmptyElements() {
        if(descricaoAmeaca.getText().toString().isEmpty()) {
            Toast.makeText(this, "Favor preencher o campo ameaça", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(endereco.getText().toString().isEmpty()) {
            Toast.makeText(this, "Favor preencher o campo endereço", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(bairro.getText().toString().isEmpty()) {
            Toast.makeText(this, "Favor preencher o campo bairro", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void deleteAmeaca(View view) {
        try{
            // Cria elemento e deleta
            Ameaca ameaca =  new Ameaca();
            ameaca.setAmeaca(descricaoAmeaca.getText().toString());
            ameaca.setEndereco(endereco.getText().toString());
            ameaca.setBairro(bairro.getText().toString());
            ameaca.setImpacto(levelAmeacas.getSelectedItem().toString());
            ameaca.setId(Integer.parseInt(identificador.getText().toString()));
            AmeacaDB ameacaDb =  new AmeacaDB(getApplicationContext());
            ameacaDb.deleteAmeaca(ameaca);
        }catch (Exception ex) {
            Log.d("ERROR AMEACA:", ex.toString());
            Toast.makeText(this, "Ocorreu um problema ao deletar a ameaça!!", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        Toast.makeText(this, "Registro excluido com sucesso", Toast.LENGTH_SHORT).show();
        setResult(200);
        this.finish();
    }
}
