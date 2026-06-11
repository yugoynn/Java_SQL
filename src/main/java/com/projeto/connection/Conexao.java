package com.projeto.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    private static Properties config;

    private static Properties carregarConfig() {
        if (config == null) {
            config = new Properties();
            try (InputStream in = Conexao.class.getClassLoader()
                    .getResourceAsStream("database.properties")) {
                if (in == null) {
                    throw new RuntimeException(
                        "Arquivo database.properties nao encontrado em resources. "
                        + "Copie o database.properties.example e preencha suas credenciais.");
                }
                config.load(in);
            } catch (Exception e) {
                throw new RuntimeException("Falha ao carregar configuracao do banco.", e);
            }
        }
        return config;
    }

    public static Connection abrir() {
        Properties p = carregarConfig();
        String url = p.getProperty("db.url");
        String user = p.getProperty("db.user");
        String senha = p.getProperty("db.password");
        try {
            return DriverManager.getConnection(url, user, senha);
        } catch (SQLException e) {
            throw new RuntimeException("Nao foi possivel conectar ao banco de dados.", e);
        }
    }
}
