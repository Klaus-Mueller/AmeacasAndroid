package com.example.pc.ameacasambientais;

/**
 * Created by pc on 12/11/2017.
 */

public class Ameaca {

    // Nome da ameaça
    private String ameaca;
    // Endereço de localização da ameaça
    private String endereco;
    // Bairro que se localiza a ameaça
    private String bairro;
    // Impacto da ameaça no ambiente
    private String impacto;
    // Identificador da ameaça
    private Integer id;

    public String getAmeaca() {
        return ameaca;
    }

    public void setAmeaca(String ameaca) {
        this.ameaca = ameaca;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
