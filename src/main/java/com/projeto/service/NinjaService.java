package com.projeto.service;

import com.projeto.dao.NinjaDAO;
import com.projeto.model.Ninja;

import java.util.List;

public class NinjaService {

    private final NinjaDAO ninjaDAO = new NinjaDAO();

    public void cadastrar(Ninja ninja) {
        if (ninja.getNome() == null || ninja.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do ninja e obrigatorio.");
        }
        if (ninja.getVila() == null || ninja.getVila().isBlank()) {
            throw new IllegalArgumentException("A vila do ninja e obrigatoria.");
        }
        ninjaDAO.inserir(ninja);
    }

    public void atualizar(Ninja ninja) {
        ninjaDAO.atualizar(ninja);
    }

    public void excluir(int id) {
        ninjaDAO.excluir(id);
    }

    public List<Ninja> listar() {
        return ninjaDAO.listar();
    }
}
