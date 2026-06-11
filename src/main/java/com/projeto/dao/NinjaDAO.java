package com.projeto.dao;

import com.projeto.connection.Conexao;
import com.projeto.model.Ninja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NinjaDAO {

    public void inserir(Ninja ninja) {
        String sql = "INSERT INTO tb_ninja "
                + "(nome, vila, cla, rank_ninja, natureza_chakra, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ninja.getNome());
            ps.setString(2, ninja.getVila());
            ps.setString(3, ninja.getCla());
            ps.setString(4, ninja.getRankNinja());
            ps.setString(5, ninja.getNaturezaChakra());
            ps.setString(6, ninja.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ninja.setId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir ninja.", e);
        }
    }

    public void atualizar(Ninja ninja) {
        String sql = "UPDATE tb_ninja SET nome = ?, vila = ?, cla = ?, "
                + "rank_ninja = ?, natureza_chakra = ?, status = ? WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ninja.getNome());
            ps.setString(2, ninja.getVila());
            ps.setString(3, ninja.getCla());
            ps.setString(4, ninja.getRankNinja());
            ps.setString(5, ninja.getNaturezaChakra());
            ps.setString(6, ninja.getStatus());
            ps.setInt(7, ninja.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar ninja.", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tb_ninja WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir ninja.", e);
        }
    }

    public List<Ninja> listar() {
        String sql = "SELECT * FROM tb_ninja ORDER BY id";
        List<Ninja> lista = new ArrayList<>();
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(montar(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar ninjas.", e);
        }
        return lista;
    }

    public Ninja buscarPorId(int id) {
        String sql = "SELECT * FROM tb_ninja WHERE id = ?";
        try (Connection con = Conexao.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montar(rs);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar ninja.", e);
        }
        return null;
    }

    private Ninja montar(ResultSet rs) throws Exception {
        Ninja n = new Ninja();
        n.setId(rs.getInt("id"));
        n.setNome(rs.getString("nome"));
        n.setVila(rs.getString("vila"));
        n.setCla(rs.getString("cla"));
        n.setRankNinja(rs.getString("rank_ninja"));
        n.setNaturezaChakra(rs.getString("natureza_chakra"));
        n.setStatus(rs.getString("status"));
        return n;
    }
}
