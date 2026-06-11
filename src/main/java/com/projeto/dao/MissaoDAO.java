package com.projeto.dao;

import com.projeto.connection.Conexao;
import com.projeto.model.Missao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MissaoDAO {

    public void inserir(Missao missao) {
        String sql = "INSERT INTO tb_missao "
                + "(titulo, descricao, rank_missao, vila_origem, status) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, missao.getTitulo());
            ps.setString(2, missao.getDescricao());
            ps.setString(3, missao.getRankMissao());
            ps.setString(4, missao.getVilaOrigem());
            ps.setString(5, missao.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    missao.setId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir missao.", e);
        }
    }

    public void atualizar(Missao missao) {
        String sql = "UPDATE tb_missao SET titulo = ?, descricao = ?, "
                + "rank_missao = ?, vila_origem = ?, status = ? WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, missao.getTitulo());
            ps.setString(2, missao.getDescricao());
            ps.setString(3, missao.getRankMissao());
            ps.setString(4, missao.getVilaOrigem());
            ps.setString(5, missao.getStatus());
            ps.setInt(6, missao.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar missao.", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tb_missao WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir missao.", e);
        }
    }

    public List<Missao> listar() {
        String sql = "SELECT * FROM tb_missao ORDER BY id";
        List<Missao> lista = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(montar(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar missoes.", e);
        }
        return lista;
    }

    public Missao buscarPorId(int id) {
        String sql = "SELECT * FROM tb_missao WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montar(rs);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar missao.", e);
        }
        return null;
    }

    private Missao montar(ResultSet rs) throws Exception {
        Missao m = new Missao();
        m.setId(rs.getInt("id"));
        m.setTitulo(rs.getString("titulo"));
        m.setDescricao(rs.getString("descricao"));
        m.setRankMissao(rs.getString("rank_missao"));
        m.setVilaOrigem(rs.getString("vila_origem"));
        m.setStatus(rs.getString("status"));
        return m;
    }
}
