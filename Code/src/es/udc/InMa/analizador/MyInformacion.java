package es.udc.InMa.analizador;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.text.StringEscapeUtils;

import es.udc.InMa.service.Informacion;

public class MyInformacion implements Informacion{

	private String autor,titulo,texto,videoURL,imagenURL,categoria,tipo,color,posicion,letra,colorLetra,errorMessage;
	private LocalDateTime fecha;
	private boolean error;
	

	protected MyInformacion(String autor, String titulo, String texto, String videoURL, String imagenURL, String categoria,
			String fecha, String tipo, String color, String letra, String colorLetra, String posicion) {

		if(autor==null)this.autor = null;
		else this.autor = new String(autor.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
		if(titulo==null)this.titulo = null;
		else this.titulo = new String(titulo.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);;
		if(texto==null)this.texto = null;
		else this.texto = new String(texto.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);;
		if(videoURL==null)this.videoURL = null;
		else this.videoURL = new String(videoURL.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);;
		if(imagenURL==null)this.imagenURL = null;
		else this.imagenURL = new String(imagenURL.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);;
		if(categoria!=null)this.categoria = new String(categoria.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).toUpperCase();
		else this.categoria="";
		
		if(tipo==null)this.tipo = null;
		else this.tipo = new String(tipo.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);;
		if(color==null)this.color = null;
		else this.color = new String(color.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);;
		if(posicion==null)this.posicion = null;
		else this.posicion = new String(posicion.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);;
		if(letra==null)this.letra = null;
		else this.letra = new String(letra.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);;
		if(colorLetra==null)this.colorLetra = null;
		else this.colorLetra = new String(colorLetra.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);;
		
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
	
	private String fix(String s) {
		if(s!=null) s=StringEscapeUtils.escapeJson(s).replace("\\n", "<br>");
		return s;
	}
	
	@Override
	public String toJson() {
		
		try {
			
			return "{\n" +
					    "\"Autor\":\""+fix(autor)+"\",\n"+
					    "\"Titulo\":\""+fix(titulo)+"\",\n"+
					    "\"Texto\":\""+fix(texto)+"\",\n"+
					    "\"Video\":\""+StringEscapeUtils.escapeJson(videoURL)+"\",\n"+
					    "\"Imagen\":\""+StringEscapeUtils.escapeJson(imagenURL)+"\",\n"+
					    "\"Categoria\":\""+StringEscapeUtils.escapeJson(categoria)+"\",\n"+
					    "\"Fecha\":\""+StringEscapeUtils.escapeJson(fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss")))+"\",\n"+
					    "\"Tipo\":\""+StringEscapeUtils.escapeJson(tipo)+"\",\n"+
					    "\"Color\":\""+StringEscapeUtils.escapeJson(color)+"\",\n"+
					    "\"Letra\":\""+StringEscapeUtils.escapeJson(letra)+"\",\n"+
					    "\"ColorLetra\":\""+StringEscapeUtils.escapeJson(colorLetra)+"\",\n"+
					    "\"Posicion\":\""+StringEscapeUtils.escapeJson(posicion)+"\"\n"+
					"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public boolean hasError() {
		return error;
	}

	@Override
	public String error() {
		return errorMessage;
	}
}
