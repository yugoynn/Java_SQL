package com.projeto.model;

import java.time.LocalDateTime;

public class Totalizador {

    private int id;
    private String descricao;
    private int quantidade;
    private LocalDateTime dataGeracao;

    public Totalizador() {
    }

    public Totalizador(String descricao, int quantidade, LocalDateTime dataGeracao) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.dataGeracao = dataGeracao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }
}
