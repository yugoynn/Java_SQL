package com.projeto.model;

import java.time.LocalDate;

public class NinjaMissao {

    private int id;
    private int idNinja;
    private int idMissao;
    private String funcao;
    private LocalDate dataParticipacao;

    private String nomeNinja;
    private String tituloMissao;

    public NinjaMissao() {
    }

    public NinjaMissao(int idNinja, int idMissao, String funcao, LocalDate dataParticipacao) {
        this.idNinja = idNinja;
        this.idMissao = idMissao;
        this.funcao = funcao;
        this.dataParticipacao = dataParticipacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNinja() {
        return idNinja;
    }

    public void setIdNinja(int idNinja) {
        this.idNinja = idNinja;
    }

    public int getIdMissao() {
        return idMissao;
    }

    public void setIdMissao(int idMissao) {
        this.idMissao = idMissao;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public LocalDate getDataParticipacao() {
        return dataParticipacao;
    }

    public void setDataParticipacao(LocalDate dataParticipacao) {
        this.dataParticipacao = dataParticipacao;
    }

    public String getNomeNinja() {
        return nomeNinja;
    }

    public void setNomeNinja(String nomeNinja) {
        this.nomeNinja = nomeNinja;
    }

    public String getTituloMissao() {
        return tituloMissao;
    }

    public void setTituloMissao(String tituloMissao) {
        this.tituloMissao = tituloMissao;
    }
}
