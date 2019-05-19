package HelloWordApp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/studente")
public class studente {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/visualizzaDocenti")
	public String seedocenti() throws SQLException, ClassNotFoundException {
		
		String stringa = "";
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select * from docenti");
			while (rs.next()) {
				stringa=stringa+rs.getString("Cognome") + " " + rs.getString("Nome")+"<br>";
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

		return "<html> " + "<title>" + "Lista Docenti " + "</title>"
		+ "<body><h1>" + "Docenti :</h1>"+ stringa +"" + "</body>"+ "</html> ";
		
		
	}
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/visualizzaDocenti/visualizzaEventi")
	public String seeeventi() throws SQLException, ClassNotFoundException {
		
		String stringa = "";
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select * from eventi inner join docenti on eventi.Docente=Docenti.id_docente");
			while (rs.next()) {
				Date date=rs.getDate("Data");
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String text = df.format(date);
				stringa=stringa+rs.getString("id_evento") + " "+rs.getString("Tipo") + " " + text+" " + rs.getString("Cognome")+"<br>";
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

		return "<html> " + "<title>" + "Eventi " + "</title>"
		+ "<body><h1>" + "Eventi :</h1>"+ stringa +"" + "</body>"+ "</html> ";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/visualizzaDocenti/visualizzaEventi/richiediPartecipazione")
	public String entereventi(@QueryParam("id_evento") String info) throws SQLException, ClassNotFoundException {
		String[] result=info.split("_");
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		try {
			PreparedStatement prepared = connection.prepareStatement("INSERT INTO richieste (id_evento,studente, id_docente) values (?,?,?)");
			prepared.setInt(1, Integer.parseInt(result[0]));
			prepared.setInt(2, Integer.parseInt(result[1]));
			prepared.setInt(3, Integer.parseInt(result[2]));
			prepared.executeUpdate();
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

		return "<html> " + "<title>" + "Eventi " + "</title>"
		+ "<body><h1>" + "Richiesta effetuata con successo</h1>"+ "</body>"+ "</html> ";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/visualizzaDocenti/visualizzaEventi/cancellaPartecipazione")
	public String delpart(@QueryParam("id_evento") String info) throws SQLException, ClassNotFoundException {
		String[] result=info.split("_");
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		try {
			PreparedStatement prepared = connection.prepareStatement("delete from richieste where id_evento=? AND studente=? AND id_docente=?");
			prepared.setInt(1, Integer.parseInt(result[0]));
			prepared.setInt(2, Integer.parseInt(result[1]));
			prepared.setInt(3, Integer.parseInt(result[2]));
			prepared.executeUpdate();
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

		Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		try {
			PreparedStatement prepared = connection.prepareStatement("delete from partecipanti where id_evento=? AND studente=? AND id_docente=?");
			prepared.setInt(1, Integer.parseInt(result[0]));
			prepared.setInt(2, Integer.parseInt(result[1]));
			prepared.setInt(3, Integer.parseInt(result[2]));
			prepared.executeUpdate();
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

		
		return "<html> " + "<title>" + "Eventi " + "</title>"
		+ "<body><h1>" + "Richiesta cancellata con successo</h1>"+ "</body>"+ "</html> ";
	}




}
