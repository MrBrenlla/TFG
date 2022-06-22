package es.udc.InMa.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import es.udc.InMa.analizador.Lexer;
import es.udc.InMa.analizador.parser;
import java_cup.runtime.lr_parser;

public class MyProperties {

	//Devolve o properties do documento Properties.txt
	public static Properties getProperties() {
			Properties p=new Properties();
			try {
				p.load(new FileInputStream(new File("./Properties.txt")));
			} catch (IOException e) {
				e.printStackTrace();
			} 

		return p;
	}
	
	//Devolvese o parser
	@SuppressWarnings("deprecation")
	public static lr_parser getParser(Reader r){
		return new parser(new Lexer(r));
	}
	
}
