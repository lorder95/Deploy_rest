package HelloWordApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class datab {
	public static void main(String[] args)
    {
	String connectionString ="jdbc:mysql://localhost:3306/mobile?user=root&password=";
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	Connection connection = null;
	try {
		connection = DriverManager.getConnection(connectionString);
		PreparedStatement prepared = connection.prepareStatement("INSERT INTO `docenti`(`id_docente`, `Cognome`, `Nome`, `Evento`) VALUES (1,Rossi,Giuseppe,Riunione Bimestrale)");
		prepared.executeUpdate();
		Statement stm = connection.createStatement();
		ResultSet rs = stm.executeQuery("select * from docenti");
		while (rs.next()) {
			System.out.println(rs.getString("cognome") + " " + rs.getString("nome"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception e) {
		System.out.println(e.getMessage());
	} finally {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// gestione errore in chiusura
		}
	}
}}
