package com.receita.teste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.receita.teste.processo.*;

@SpringBootApplication
public class ReceitafederalApplication  implements CommandLineRunner {
	
	
	@Autowired
	ProcessaCSV processaCSV;

	public static void main(String[] args) {
		SpringApplication.run(ReceitafederalApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		 
		 if(args.length > 0 ) {
			processaCSV.processar(args[0]);
			// processaCSV.processar("E:\\DEV\\Java\\Teste Spring\\Backend\\teste1.csv");	 
		 } else {
			 System.out.println("Informe a arquivo a processar....");
		 }
		 
		
	}

}
