package es.udc.InMa.CategoriasManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import es.udc.InMa.service.Informacion;
import es.udc.InMa.service.MyProperties;

public class Categoria {

	private ArrayList<Informacion> list=new ArrayList<>();
	private Properties p = MyProperties.getProperties();
	
	//Engade unha información á lista, ordenaa e elimina os excedentes
	public void add(Informacion info) {
		System.out.println("Engdindo en "+info.getCategoria());
		int maxSize=Integer.parseInt(p.getProperty("CATEGORIASIZE"));
		list.add(info);
		Collections.sort(list);
		while(list.size()>maxSize) {
			list.remove(list.size()-1);
		};
		System.out.println("Engadido en "+info.getCategoria());
	}

	public ArrayList<Informacion> getList() {
		return list;
	}
	
}
