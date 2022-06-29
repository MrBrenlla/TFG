package es.udc.InMa.CategoriasManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.udc.InMa.service.Informacion;
import es.udc.InMa.service.MyProperties;

public class Manager {
	
	private static Manager m = null;
	private Map<String,Categoria> categorias =null;
	private String[] cat = MyProperties.getProperties().getProperty("CATEGORIAS").toUpperCase().split(";");
	
	private Manager() {
		if (categorias==null) {
			categorias=new HashMap<String,Categoria>();
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
		return aux;
	}
	
	//Devolvese un Json co nome de todas as categorías
	public String getCategorias() {
		String aux = "[\n";
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
