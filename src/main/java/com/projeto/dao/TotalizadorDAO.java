package com.projeto.dao;

import com.projeto.connection.Conexao;
import com.projeto.model.Totalizador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TotalizadorDAO {

    public void salvar(Totalizador t) {
        String sql = "INSERT INTO tb_totalizador_ninja "
                + "(descricao, quantidade, data_geracao) VALUES (?, ?, ?)";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDescricao());
            ps.setInt(2, t.getQuantidade());
            ps.setTimestamp(3, Timestamp.valueOf(t.getDataGeracao()));
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar totalizador.", e);
        }
    }

    public List<Totalizador> gerar() {
        List<Totalizador> resultado = new ArrayList<>();
        LocalDateTime agora = LocalDateTime.now();

        adicionarAgrupado(resultado,
                "Ninjas na vila ",
                "SELECT vila AS chave, COUNT(*) AS qtd FROM tb_ninja GROUP BY vila",
                agora);

        adicionarAgrupado(resultado,
                "Ninjas com rank ",
                "SELECT rank_ninja AS chave, COUNT(*) AS qtd FROM tb_ninja GROUP BY rank_ninja",
                agora);

        adicionarAgrupado(resultado,
                "Ninjas com natureza de chakra ",
                "SELECT natureza_chakra AS chave, COUNT(*) AS qtd FROM tb_ninja GROUP BY natureza_chakra",
                agora);

        adicionarAgrupado(resultado,
                "Missoes com rank ",
                "SELECT rank_missao AS chave, COUNT(*) AS qtd FROM tb_missao GROUP BY rank_missao",
                agora);

        adicionarAgrupado(resultado,
                "Missoes com status ",
                "SELECT status AS chave, COUNT(*) AS qtd FROM tb_missao GROUP BY status",
                agora);

        adicionarSimples(resultado,
                "Ninjas vinculados a missoes",
                "SELECT COUNT(DISTINCT id_ninja) AS qtd FROM tb_ninja_missao",
                agora);

        adicionarSimples(resultado,
                "Missoes sem nenhum ninja vinculado",
                "SELECT COUNT(*) AS qtd FROM tb_missao m "
                + "WHERE NOT EXISTS (SELECT 1 FROM tb_ninja_missao nm WHERE nm.id_missao = m.id)",
                agora);

        return resultado;
    }

    private void adicionarAgrupado(List<Totalizador> lista, String prefixo,
                                   String sql, LocalDateTime data) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String chave = rs.getString("chave");
                int qtd = rs.getInt("qtd");
                lista.add(new Totalizador(prefixo + chave, qtd, data));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar totalizador agrupado.", e);
        }
    }

    private void adicionarSimples(List<Totalizador> lista, String descricao,
                                  String sql, LocalDateTime data) {
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                lista.add(new Totalizador(descricao, rs.getInt("qtd"), data));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar totalizador simples.", e);
        }
    }
}
