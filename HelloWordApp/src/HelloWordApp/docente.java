package HelloWordApp;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/docente")
public class docente {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Users written in TEXT format!";
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Users written in XML format" + "</hello>";
	}
	
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/aggiungiEvento")
	public String addevento(@QueryParam("add_evento") String info) throws SQLException, ClassNotFoundException {
		String[] result=info.split("_");
		String stringa = "";
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		
		try {
			PreparedStatement prepared = connection.prepareStatement("INSERT INTO eventi (id_evento,Descrizione, data, Tipo,Docente) values (?,?,?,?,?)");
			prepared.setInt(1, Integer.parseInt(result[0]));
			prepared.setString(2, result[1]);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date data = df.parse(result[2]);
			java.sql.Date sql = new java.sql.Date(data.getTime());
			prepared.setDate(3, sql);
			prepared.setString(4, result[3]);
			prepared.setInt(5, Integer.parseInt(result[4]));
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

		return "<html> " + "<title>" + "Aggiunta Evento " + "</title>"
		+ "<body><h1>" + "Evento aggiunto con successo" + "</body></h1>" 				+ "</html> ";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/visualizzaEventi")
	public String seeevent() throws SQLException, ClassNotFoundException {
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
	@Path("/visualizzaEventi/modificaEvento")
	public String updevent(@QueryParam("up_evento") String info) throws SQLException, ClassNotFoundException {
		String[] result=info.split("_");
		String stringa = "";
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		
		try {
			PreparedStatement prepared = connection.prepareStatement("update eventi set descrizione=?, data=?, tipo=?, Docente=? where id_evento=?");
			prepared.setInt(5, Integer.parseInt(result[0]));
			prepared.setString(1, result[1]);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date data = df.parse(result[2]);
			java.sql.Date sql = new java.sql.Date(data.getTime());
			prepared.setDate(2, sql);
			prepared.setString(3, result[3]);
			prepared.setInt(4, Integer.parseInt(result[4]));
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

		return "<html> " + "<title>" + "Modifica Evento " + "</title>"
		+ "<body><h1>" + "Evento modificato con successo" + "</body></h1>" 				+ "</html> ";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/visualizzaEventi/eliminaEvento")
	public String delevent(@QueryParam("del_evento") String info) throws SQLException, ClassNotFoundException {
		String[] result=info.split("_");
		String stringa = "";
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		
		try {
			PreparedStatement prepared = connection.prepareStatement("delete from eventi  where id_evento=?");
			prepared.setInt(1, Integer.parseInt(result[0]));
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

		return "<html> " + "<title>" + "Cancellazione Evento " + "</title>"
		+ "<body><h1>" + "Evento cancellato con successo" + "</body></h1>" 				+ "</html> ";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/visualizzaPartecipanti")
	public String seepart(@QueryParam("id_evento") String info) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		String stringa="";
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select * from partecipanti inner join studenti on partecipanti.studente=studenti.studente where id_evento="+Integer.parseInt(info));
			while (rs.next()) {
				stringa=stringa+rs.getString("Cognome") + " "+rs.getString("Nome")+"<br>";
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

		return "<html> " + "<title>" + "Evento " + "</title>"
		+ "<body><h1>" + "Partecipanti :</h1>"+ stringa +"" + "</body>"+ "</html> ";
		}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/visualizzaRichieste")
	public String seerich(@QueryParam("id_evento") String info) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		String stringa="";
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select * from richieste inner join studenti on richieste.studente=studenti.studente where id_evento="+Integer.parseInt(info));
			while (rs.next()) {
				stringa=stringa+rs.getString("Cognome") + " "+rs.getString("Nome")+" "+rs.getString("studente")+"<br>";
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

		return "<html> " + "<title>" + "Evento " + "</title>"
		+ "<body><h1>" + "Richieste :</h1>"+ stringa +"" + "</body>"+ "</html> ";
		}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("visualizzaPartecipanti/confermaPartecipante")
	public String acerich(@QueryParam("conferma") String info) throws SQLException, ClassNotFoundException {
		int cont=0;
		String[] result=info.split("_");
		Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
		String stringa="";
		try {
			
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("select * from richieste inner join studenti on richieste.studente=studenti.studente where id_evento="+Integer.parseInt(result[1])+" AND richieste.studente="+Integer.parseInt(result[2]));
			while (rs.next()) {
				cont++;
				stringa=stringa+rs.getString("Cognome") + " "+rs.getString("Nome")+" "+rs.getString("studente")+"<br>";
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
		
		if (cont>0) {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
			Class.forName("com.mysql.jdbc.Driver");
			try {
				PreparedStatement prepared = connection.prepareStatement("INSERT INTO partecipanti (id_docente,id_evento, studente) values (?,?,?)");
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
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "");
			Class.forName("com.mysql.jdbc.Driver");
			try {
				PreparedStatement prepared = connection.prepareStatement("delete from richieste  where richieste.id_evento=? and richieste.studente=? ");
				prepared.setInt(1, Integer.parseInt(result[1]));
				prepared.setInt(2, Integer.parseInt(result[2]));
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

		}

		return "<html> " + "<title>" + "Aggiunta Evento " + "</title>"
		+ "<body><h1>" + "Studente aggiunto con successo" + "</body></h1>" 				+ "</html> ";

		}
	}
