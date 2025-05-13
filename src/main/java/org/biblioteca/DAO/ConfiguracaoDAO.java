package org.biblioteca.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConfiguracaoDAO {
    public double getValorMulta() {
        double valorMulta = -1;
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/drools", "drools", "drools")) {
            PreparedStatement stmt = conn.prepareStatement("SELECT valor FROM configuracao WHERE chave = ?");
            stmt.setString(1, "valor_multa_diario");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                valorMulta = rs.getDouble("valor");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (valorMulta == -1) {
            System.out.println("Erro ao recuperar valor da multa! Verifique a conexão com o banco.");
        }

        return valorMulta;
    }

}
