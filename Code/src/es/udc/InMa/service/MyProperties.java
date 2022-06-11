package es.udc.InMa.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import es.udc.InMa.analizador.Lexer;
import es.udc.InMa.analizador.parser;

public class MyProperties {

	private static Properties p=null;
	
	public static Properties getProperties() {
		if(p==null) {
			try {
				p=new Properties();
				p.load(new FileInputStream(new File("./Properties.txt"))); 
			} catch (IOException e) {
			}
		}
		
		return p;
	}
	
	@SuppressWarnings("deprecation")
	public static java_cup.runtime.lr_parser getParser(Reader r){
		return new parser(new Lexer(r));
	}
	
}
