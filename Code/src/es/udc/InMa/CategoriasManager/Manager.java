package es.udc.InMa.CategoriasManager;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.udc.InMa.service.Informacion;
import es.udc.InMa.service.MyProperties;
import org.apache.commons.text.StringEscapeUtils;

public class Manager {
	
	private static Manager m = null;
	private Map<String,Categoria> categorias =null;
	private String[] cat = MyProperties.getProperties().getProperty("CATEGORIAS").split(";");
	
	private Manager() {
		if (categorias==null) {
			categorias=new HashMap<String,Categoria>();
			for (String s : cat) {
				categorias.putIfAbsent(fix(s).toUpperCase(), new Categoria());
			}
			categorias.putIfAbsent("OUTRAS", new Categoria());
		}
	}
	
	public static Manager getManager() {
		if(m==null) m=new Manager();
		return m;
	}
	
	private String fix(String s) {
		if(s!=null) {
			System.out.println(s);
			s = new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
			System.out.println(s);
			s=StringEscapeUtils.escapeHtml4(s.toUpperCase());
			System.out.println(s);
		}
		return s;
	}
	
	//Engade a información á categoría correspondente ou a "OUTRAS" se a cetegoría non existe
	public void add(Informacion info) {
		Categoria c = categorias.get(StringEscapeUtils.escapeHtml4(info.getCategoria().toUpperCase()).toUpperCase());
		if(c==null) c = categorias.get("OUTRAS");
		c.add(info);
	}
	
	//Devolve o JSON coa información da categoría ou null se a categoría non existe
	public String get(String categoria) {
		System.out.println("Buscando categoria "+categoria);
		Categoria c = categorias.get(categoria);
		if(c==null) return null;
		List<Informacion> l = c.getList();
		String aux="[\n";
		for(int i=0; i<l.size()-1;i++){
			aux=aux+l.get(i).toJson()+",\n";
		}
		if(l.size()>0) aux=aux+l.get(l.size()-1).toJson()+"\n]";
		else aux=aux+"]";
		System.out.println(aux);
		return aux;
	}
	
	//Devolvese un Json co nome de todas as categorías
	public String getCategorias() {
		String aux = "[\n";
		ArrayList<String> l=new ArrayList<>();
		for(String c:cat) {
			if(!l.contains(c) && !c.equals("OUTRAS")) {
				aux=aux+"\""+fix(c)+"\",\n";
				l.add(c);
			}
		}
		aux=aux+"\"OUTRAS\"\n]";

		return aux;
	}
	
}
