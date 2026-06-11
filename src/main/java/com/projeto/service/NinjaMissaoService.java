package com.projeto.service;

import com.projeto.dao.NinjaDAO;
import com.projeto.dao.NinjaMissaoDAO;
import com.projeto.model.Missao;
import com.projeto.model.Ninja;
import com.projeto.model.NinjaMissao;

import com.projeto.dao.MissaoDAO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NinjaMissaoService {

    private static final Map<String, List<String>> REGRA_RANK = Map.of(
        "Genin", Arrays.asList("D", "C"),
        "Chunin", Arrays.asList("D", "C", "B"),
        "Jounin", Arrays.asList("D", "C", "B", "A", "S"),
        "Kage", Arrays.asList("A", "S")
    );

    private final NinjaMissaoDAO ninjaMissaoDAO = new NinjaMissaoDAO();
    private final NinjaDAO ninjaDAO = new NinjaDAO();
    private final MissaoDAO missaoDAO = new MissaoDAO();

    public void vincular(NinjaMissao vinculo) {
        Ninja ninja = ninjaDAO.buscarPorId(vinculo.getIdNinja());
        Missao missao = missaoDAO.buscarPorId(vinculo.getIdMissao());

        if (ninja == null || missao == null) {
            throw new IllegalArgumentException("Ninja ou missao nao encontrados.");
        }

        if (ninjaMissaoDAO.vinculoExiste(vinculo.getIdNinja(), vinculo.getIdMissao())) {
            throw new IllegalStateException(
                "Este ninja ja esta vinculado a esta missao.");
        }

        if (!podeParticipar(ninja.getRankNinja(), missao.getRankMissao())) {
            throw new IllegalStateException(
                ninja.getNome() + " e " + ninja.getRankNinja()
                + " e nao pode participar de uma missao de rank "
                + missao.getRankMissao() + ".");
        }

        ninjaMissaoDAO.inserir(vinculo);
    }

    public boolean podeParticipar(String rankNinja, String rankMissao) {
        List<String> permitidas = REGRA_RANK.get(rankNinja);
        if (permitidas == null) {
            return false;
        }
        return permitidas.contains(rankMissao);
    }

    public List<NinjaMissao> listar() {
        return ninjaMissaoDAO.listar();
    }
}
