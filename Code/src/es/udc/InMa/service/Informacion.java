package es.udc.InMa.service;

public interface Informacion extends Comparable<Object>{
	
	//Devolve a categoría da información
	public String getCategoria();
	
	//Devolve o String do Json que representa esa informacioón
	public String toJson();
	
	//Devolvese true se houbo algún erro recoñecible durante o parse 
	public boolean hasError();
	
	//Devolvese o erro recoñecible durante o parse 
	public String error();
}
