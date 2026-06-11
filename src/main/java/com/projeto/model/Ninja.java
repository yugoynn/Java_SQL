package com.projeto.model;

public class Ninja {

    private int id;
    private String nome;
    private String vila;
    private String cla;
    private String rankNinja;
    private String naturezaChakra;
    private String status;

    public Ninja() {
    }

    public Ninja(String nome, String vila, String cla, String rankNinja,
                 String naturezaChakra, String status) {
        this.nome = nome;
        this.vila = vila;
        this.cla = cla;
        this.rankNinja = rankNinja;
        this.naturezaChakra = naturezaChakra;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVila() {
        return vila;
    }

    public void setVila(String vila) {
        this.vila = vila;
    }

    public String getCla() {
        return cla;
    }

    public void setCla(String cla) {
        this.cla = cla;
    }

    public String getRankNinja() {
        return rankNinja;
    }

    public void setRankNinja(String rankNinja) {
        this.rankNinja = rankNinja;
    }

    public String getNaturezaChakra() {
        return naturezaChakra;
    }

    public void setNaturezaChakra(String naturezaChakra) {
        this.naturezaChakra = naturezaChakra;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return nome + " (" + rankNinja + ")";
    }
}
