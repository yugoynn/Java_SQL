package com.projeto.dao;

import com.projeto.connection.Conexao;
import com.projeto.model.NinjaMissao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NinjaMissaoDAO {

    public void inserir(NinjaMissao nm) {
        String sql = "INSERT INTO tb_ninja_missao "
                + "(id_ninja, id_missao, funcao, data_participacao) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nm.getIdNinja());
            ps.setInt(2, nm.getIdMissao());
            ps.setString(3, nm.getFuncao());
            ps.setDate(4, Date.valueOf(nm.getDataParticipacao()));
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao vincular ninja a missao.", e);
        }
    }

    public boolean vinculoExiste(int idNinja, int idMissao) {
        String sql = "SELECT 1 FROM tb_ninja_missao WHERE id_ninja = ? AND id_missao = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idNinja);
            ps.setInt(2, idMissao);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar vinculo existente.", e);
        }
    }

    public List<NinjaMissao> listar() {
        String sql = "SELECT nm.id, nm.id_ninja, nm.id_missao, nm.funcao, "
                + "nm.data_participacao, n.nome AS nome_ninja, m.titulo AS titulo_missao "
                + "FROM tb_ninja_missao nm "
                + "JOIN tb_ninja n ON n.id = nm.id_ninja "
                + "JOIN tb_missao m ON m.id = nm.id_missao "
                + "ORDER BY nm.id";
        List<NinjaMissao> lista = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NinjaMissao nm = new NinjaMissao();
                nm.setId(rs.getInt("id"));
                nm.setIdNinja(rs.getInt("id_ninja"));
                nm.setIdMissao(rs.getInt("id_missao"));
                nm.setFuncao(rs.getString("funcao"));
                nm.setDataParticipacao(rs.getDate("data_participacao").toLocalDate());
                nm.setNomeNinja(rs.getString("nome_ninja"));
                nm.setTituloMissao(rs.getString("titulo_missao"));
                lista.add(nm);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar vinculos.", e);
        }
        return lista;
    }
}
