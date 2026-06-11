package com.projeto.model;

public class Missao {

    private int id;
    private String titulo;
    private String descricao;
    private String rankMissao;
    private String vilaOrigem;
    private String status;

    public Missao() {
    }

    public Missao(String titulo, String descricao, String rankMissao,
                  String vilaOrigem, String status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.rankMissao = rankMissao;
        this.vilaOrigem = vilaOrigem;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRankMissao() {
        return rankMissao;
    }

    public void setRankMissao(String rankMissao) {
        this.rankMissao = rankMissao;
    }

    public String getVilaOrigem() {
        return vilaOrigem;
    }

    public void setVilaOrigem(String vilaOrigem) {
        this.vilaOrigem = vilaOrigem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return titulo + " [" + rankMissao + "]";
    }
}
