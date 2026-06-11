import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    // ⚠️ Altere com os dados do seu banco
    private static final String URL     = "jdbc:postgresql://localhost:5432/nome_do_banco";
    private static final String USUARIO = "seu_usuario";
    private static final String SENHA   = "sua_senha";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
