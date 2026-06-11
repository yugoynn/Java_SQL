package com.projeto.service;

import com.projeto.dao.TotalizadorDAO;
import com.projeto.model.Totalizador;

import java.util.List;

public class TotalizadorService {

    private final TotalizadorDAO totalizadorDAO = new TotalizadorDAO();

    public List<Totalizador> gerarEsalvar() {
        List<Totalizador> totalizadores = totalizadorDAO.gerar();
        for (Totalizador t : totalizadores) {
            totalizadorDAO.salvar(t);
        }
        return totalizadores;
    }
}
