package HelloWordApp;

public class Evento {
	private String data,tipo,descrizione;
	
	public Evento(String da,String t, String de) {
		this.data = da;
		this.tipo = t;
		this.descrizione = de;
	}
	
	public String getInfo() {
		return ("Data:"+data+" Tipo:"+tipo+" Descrizione:"+descrizione);
	}
	

}
