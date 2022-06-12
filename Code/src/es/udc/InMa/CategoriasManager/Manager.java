package es.udc.InMa.CategoriasManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import es.udc.InMa.service.Informacion;
import es.udc.InMa.service.MyProperties;

public class Manager {
	
	private static Manager m = null;
	private static Map<String,Categoria> categorias =null;
	private Properties p = MyProperties.getProperties();
	
	private Manager() {
		if (categorias==null) {
			categorias=new HashMap<String,Categoria>();
			String[] cat = p.getProperty("CATEGORIAS").toUpperCase().split(";");
			for (String s : cat) {
				categorias.putIfAbsent(s, new Categoria());
			}
			categorias.putIfAbsent("OUTRAS", new Categoria());
		}
	}
	
	public static Manager getManager() {
		if(m==null) m=new Manager();
		return m;
	}
	
	//Engade a información á categoría correspondente ou a "OUTRAS" se a cetegoría non existe
	public void add(Informacion info) {
		Categoria c = categorias.get(info.getCategoria().toUpperCase());
		if(c==null) c = categorias.get("OUTRAS");
		c.add(info);
	}
	
	//Devolve a lista coa información de unha categoría
	public List<Informacion> get(String categoria) {
		System.out.println("Buscando categoria "+categoria);
		Categoria c = categorias.get(categoria);
		if(c==null) return null;
		return c.getList();
	}
	
	//Devolvese un Json co nome de todas as categorías
	public String getCategorias() {
		String aux = "[\n";
		String[] cat = p.getProperty("CATEGORIAS").toUpperCase().split(";");
		ArrayList<String> l=new ArrayList<>();
		for(String c:cat) {
			if(!l.contains(c) && !c.equals("OUTRAS")) {
				aux=aux+"\""+c+"\",\n";
				l.add(c);
			}
		}
		aux=aux+"\"OUTRAS\"\n]";

		return aux;
	}
	
}
