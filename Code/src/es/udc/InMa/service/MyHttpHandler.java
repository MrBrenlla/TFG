package es.udc.InMa.service;

import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.udc.InMa.CategoriasManager.Manager;
import java_cup.runtime.lr_parser;

public class MyHttpHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		if("GET".equals(exchange.getRequestMethod())) { 
			
			 handleGetRequest(exchange);
			
		}else if("POST".equals(exchange.getRequestMethod())) { 

			handlePostRequest(exchange);        

		}  
		
		

	}


	private void handleGetRequest(HttpExchange exchange) throws IOException {
		
		            String requestParamValue = exchange. getRequestURI().toString();
		            
		            System.out.print("Accedendo a "+requestParamValue+"\n");
		            
		            String encoding = "UTF-8";
		            
		            if(requestParamValue.toUpperCase().startsWith("/CATEGORIA")){
		            	exchange.getResponseHeaders().set("Content-Type", "text/json; charset=" + encoding);
		            	requestParamValue=requestParamValue.toUpperCase();
		            	Manager manager=Manager.getManager();
		            	String cat = requestParamValue.replaceFirst("/CATEGORIA/", "");
		            	
		            	if(cat.equals("") || cat.equals("/CATEGORIA")) {
		            			OutputStream outputStream = exchange.getResponseBody(); 
		            			
		            			String aux=manager.getCategorias();
					    	    	
					    	    exchange.sendResponseHeaders(200, aux.length());
					    	
					    	    outputStream.write(aux.getBytes());
					    	
					    	    outputStream.flush();
					    	    
					    	    outputStream.close();
		            	}else {
		            		List<Informacion> l = manager.get(cat);
			            	if(l==null) {
			            		System.out.println("GET categoría inexistente");
			        			exchange.sendResponseHeaders(404,-1);
			            	} else {
			            		OutputStream outputStream = exchange.getResponseBody(); 
			            		
			            		String aux="[\n";
								for(int i=0; i<l.size()-1;i++){
			            			aux=aux+l.get(i).toJson()+",\n";
			            		}
								if(l.size()>0) aux=aux+l.get(l.size()-1).toJson()+"\n]";
								else aux=aux+"]";
			            					
								System.out.println(aux);
								
					    	    exchange.sendResponseHeaders(200, aux.length());
					    	
					    	    outputStream.write(aux.getBytes());
					    	
					    	    outputStream.flush();
					    	    
					    	    outputStream.close();
			            	}
		            	}
		            	
		            }else{
		            	if(requestParamValue.endsWith(".mp4"))exchange.getResponseHeaders().set("Content-Type", "video/mp4");
		            	
		            	Properties p = MyProperties.getProperties();
			        	
			    	    OutputStream outputStream = exchange.getResponseBody();
			    	
			    	    File f = new File(p.getProperty("DIR")+requestParamValue);
			    	    
			    	    exchange.sendResponseHeaders(200, f.length());
			    	
			    	    outputStream.write(Files.readAllBytes(f.toPath()));
			    	
			    	    outputStream.flush();
			    	    
			    	    outputStream.close();
		            	
		            }
		            
		            
		            

	}
	
	private void handlePostRequest(HttpExchange exchange) throws IOException {
		
		String ip = exchange.getRemoteAddress().getAddress().getHostAddress();
		
		
		if(! comprobarIP(ip)) {
			System.out.println("Ip "+ip+" sin permisos intentou facer POST");
			exchange.sendResponseHeaders(403,-1);
		}
		
		InputStream input = exchange.getRequestBody();
		
		int data = input.read();
		String text="";
		while (data != -1) {
			text+=(char) data;
			data = input.read();
		}
		
		Reader r=new CharArrayReader(text.toCharArray());
		
		lr_parser p = MyProperties.getParser(r);
		
		Informacion aux;
		String erroText="Error de sintaxis";
		try {
			aux = ((Informacion)p.parse().value);
			
			
			if(aux==null) {
				System.out.println("Error de sintaxis");
				exchange.sendResponseHeaders(400,erroText.length());
			}
			else if(aux.hasError()) {
				erroText=aux.error();
				System.out.println(aux.error());
				exchange.sendResponseHeaders(400,erroText.length());
			}
			else {
				System.out.println(aux.toJson());
				Manager m = Manager.getManager();
				m.add(aux);
				erroText="Engadido correctamente";
				exchange.sendResponseHeaders(200,erroText.length());
			}
		} catch (Exception e) {
			System.out.println("Error de sintaxis");
			exchange.sendResponseHeaders(400,erroText.length());
		} finally {
			OutputStream outputStream = exchange.getResponseBody();
			
			outputStream.write(erroText.getBytes());
	    	
    	    outputStream.flush();
    	    
    	    outputStream.close();
		}
		
	}
	
	private boolean comprobarIP(String ip) {
		String separator;
		
		if(ip.contains(":"))separator=":";
		else separator=".";
				
		Properties properties = MyProperties.getProperties();
		for(String s : properties.getProperty("IPPOST").split(";")) {
			if(s.contains(separator)) {
				if(s.contains("-")) {
					if(dentroRango(ip,s)) return true;
				}
				else {
					if(ip==s)return true;
				}
			}
		}
		return false;
	}
	
	private boolean dentroRango(String inIp, String ipRange) {
		String separator;
		
		if(inIp.contains(":"))separator=":";
		else separator=".";
		
		String[] ip=inIp.split(separator);
		
		String[] aux = ipRange.split("-");
		if(aux.length!=2) return false;
				
		String[][] ipLimits = new String[2][];
		ipLimits[0]=aux[0].split(separator);
		ipLimits[1]=aux[1].split(separator);
		if(ipLimits[0].length!=ipLimits[1].length || ipLimits[0].length!=ip.length) return false;
		
		boolean max=false;
		boolean min=false;
		
		for(int i=0; i<ip.length;i++) {
			if(Integer.parseInt(ip[i],16)<Integer.parseInt(ipLimits[0][i],16) && !min) return false;
			if(Integer.parseInt(ip[i],16)>Integer.parseInt(ipLimits[0][i],16)) min=true;
			if(Integer.parseInt(ip[i],16)>Integer.parseInt(ipLimits[1][i],16) && !max) return false;
			if(Integer.parseInt(ip[i],16)<Integer.parseInt(ipLimits[1][i],16)) max=true;
		}
		
		return true;
	}
}
