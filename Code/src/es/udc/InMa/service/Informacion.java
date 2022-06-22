package es.udc.InMa.service;

public interface Informacion extends Comparable<Object>{
	
	//Debese inplementar o método compareTo(Objet o) da interfaz Comparable<Object> de tal forma que ordene a información de maior a menor prioridade.
	
	
	//Devolve a categoría da información
	public String getCategoria();
	
	//Devolve o String do Json que representa esa información
	public String toJson();
	
	//Devolvese true se houbo algún erro recoñecible durante o parse 
	public boolean hasError();
	
	//Devolvese o erro recoñecible durante o parse 
	public String error();
}
