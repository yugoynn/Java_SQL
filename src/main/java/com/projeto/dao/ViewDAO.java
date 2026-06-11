package com.projeto.dao;

import com.projeto.connection.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ViewDAO {

    public List<String> colunasDaView(String nomeView) {
        List<String> colunas = new ArrayList<>();
        String sql = "SELECT * FROM " + nomeView + " LIMIT 0";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData meta = rs.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                colunas.add(meta.getColumnLabel(i));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler colunas da view " + nomeView, e);
        }
        return colunas;
    }

    public List<Object[]> dadosDaView(String nomeView) {
        List<Object[]> linhas = new ArrayList<>();
        String sql = "SELECT * FROM " + nomeView;
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int totalColunas = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] linha = new Object[totalColunas];
                for (int i = 0; i < totalColunas; i++) {
                    linha[i] = rs.getObject(i + 1);
                }
                linhas.add(linha);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar a view " + nomeView, e);
        }
        return linhas;
    }
}
