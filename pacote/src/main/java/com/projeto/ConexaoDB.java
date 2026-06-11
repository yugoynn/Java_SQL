package com.projeto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    // ⚠️  Altere as variáveis abaixo com os dados do seu banco
    private static final String URL      = "jdbc:postgresql://localhost:5432/nome_do_banco";
    private static final String USUARIO  = "seu_usuario";
    private static final String SENHA    = "sua_senha";

    /**
     * Retorna uma conexão com o banco de dados PostgreSQL.
     * Lembre de fechar a conexão após o uso (connection.close()).
     */
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    // Teste rápido de conexão
    public static void main(String[] args) {
        try (Connection con = getConexao()) {
            System.out.println("✅ Conexão com o banco bem-sucedida!");
        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar: " + e.getMessage());
        }
    }
}
