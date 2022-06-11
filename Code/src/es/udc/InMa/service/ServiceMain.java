package es.udc.InMa.service;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

import com.sun.net.httpserver.*;


public class ServiceMain{
    
    public static void main(String[] args) {
    	
    	Properties p = MyProperties.getProperties();
    	
    	try {
    		HttpServer httpServer;
            //Create HttpServer which is listening on the given port 
            httpServer = HttpServer.create(new InetSocketAddress(Integer.parseInt(p.getProperty("PORT"))), 0);
            //Create a new context for the given context and handler
            httpServer.createContext("/", new MyHttpHandler());
            //Create a default executor
            httpServer.setExecutor(null);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
}
