package es.udc.InMa.service;

public interface Informacion extends Comparable<Object>{

	public String getCategoria();
	
	public String toJson();
	
	public boolean hasError();
	
	public String error();
}
