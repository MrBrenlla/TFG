package es.udc.InMa.analizador;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import es.udc.InMa.service.Informacion;

public class MyInformacion implements Informacion{

	private String autor,titulo,texto,videoURL,imagenURL,categoria,tipo,color,posicion,letra,errorMessage;
	private LocalDateTime fecha;
	private boolean error;
	

	protected MyInformacion(String autor, String titulo, String texto, String videoURL, String imagenURL, String categoria,
			String fecha, String tipo, String color, String letra, String posicion) {

		this.autor = autor;
		this.titulo = titulo;
		this.texto = texto;
		this.videoURL = videoURL;
		this.imagenURL = imagenURL;
		if(categoria!=null)this.categoria = categoria.toUpperCase();
		else this.categoria="";
		
		this.tipo = tipo;
		this.color = color;
		this.posicion = posicion;
		this.letra = letra;
		
		try {
			this.fecha = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss"));
			this.error=false;
			this.errorMessage="Sin erros";
		} catch(Exception  e){
			this.error=true;
			if(fecha==null) this.errorMessage="O XML non inclue a fecha";
			else this.errorMessage="A fecha ten un formato incorrecto";
		}
		
	}
	
	protected MyInformacion(String errorMessage) {
		this.autor = null;
		this.titulo = null;
		this.texto = null;
		this.videoURL = null;
		this.imagenURL = null;
		this.categoria = null;
		this.fecha = null;
		this.tipo = null;
		this.color = null;
		this.posicion = null;
		this.letra = null;
		this.error=true;
		this.errorMessage=errorMessage;
	};

	public String getCategoria() {
		return categoria;
	}

	@Override
	public int compareTo(Object o) {
		MyInformacion target = (MyInformacion) o;
		return target.fecha.compareTo(this.fecha);
	}
	
	@Override
	public String toJson() {
		return "{\n" +
				    "\"Autor\":\""+autor+"\",\n"+
				    "\"Titulo\":\""+titulo+"\",\n"+
				    "\"Texto\":\""+texto+"\",\n"+
				    "\"Video\":\""+videoURL+"\",\n"+
				    "\"Imagen\":\""+imagenURL+"\",\n"+
				    "\"Categoria\":\""+categoria+"\",\n"+
				    "\"Fecha\":\""+fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss"))+"\",\n"+
				    "\"Tipo\":\""+tipo+"\",\n"+
				    "\"Color\":\""+color+"\",\n"+
				    "\"Letra\":\""+letra+"\",\n"+
				    "\"Posicion\":\""+posicion+"\"\n"+
				"}";
	}

	@Override
	public boolean hasError() {
		return error;
	}

	@Override
	public String error() {
		// TODO Auto-generated method stub
		return errorMessage;
	}
}
