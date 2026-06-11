package com.projeto.service;

import com.projeto.dao.MissaoDAO;
import com.projeto.model.Missao;

import java.util.Arrays;
import java.util.List;

public class MissaoService {

    private static final List<String> RANKS_VALIDOS = Arrays.asList("D", "C", "B", "A", "S");

    private final MissaoDAO missaoDAO = new MissaoDAO();

    public void cadastrar(Missao missao) {
        validar(missao);
        missaoDAO.inserir(missao);
    }

    public void atualizar(Missao missao) {
        validar(missao);
        missaoDAO.atualizar(missao);
    }

    public void excluir(int id) {
        missaoDAO.excluir(id);
    }

    public List<Missao> listar() {
        return missaoDAO.listar();
    }

    private void validar(Missao missao) {
        if (missao.getTitulo() == null || missao.getTitulo().isBlank()) {
            throw new IllegalArgumentException("O titulo da missao e obrigatorio.");
        }
        if (!RANKS_VALIDOS.contains(missao.getRankMissao())) {
            throw new IllegalArgumentException(
                "O rank da missao deve ser D, C, B, A ou S.");
        }
    }
}
