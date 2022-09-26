package com.receita.teste.processo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessaCSV {
	
	
	@Autowired
	ReceitaService receitaService;
	
	public void processar(String arquivoCSV) {
		
		// agencia;conta;saldo;status
		
		if(arquivoCSV == null || arquivoCSV.isEmpty()) {
			return;
		}
		  
		    BufferedReader br = null;
		    String linha = "";
		    String csvDivisor = ";";
		    
		    String nomeArquivo = arquivoCSV.replace(".csv", "").replace(".CSV", "") + "_retornoReceita.csv";
		    File arquivoRetorno= null;
		    FileWriter inserindo= null;
		    try {

		        br = new BufferedReader(new FileReader(arquivoCSV));
		        
		        inserindo = new FileWriter(nomeArquivo, true);
		        
		        
		        while ((linha = br.readLine()) != null) {

		            String[] contasaldo = linha.split(csvDivisor);
 
		            
		           String retorno = contasaldo[0] + csvDivisor +
		        		   contasaldo[1] + csvDivisor +
		        		   contasaldo[2] + csvDivisor +
		        		   contasaldo[3] + csvDivisor +	   processaLinha(contasaldo);
		           
		           
		           inserindo.write(retorno+"\n");
		           System.out.println(retorno); 
		         

		        }

		    } catch (FileNotFoundException e) {
		       System.out.println("Arquivo " + arquivoCSV + " não existe"); 
		    //    e.printStackTrace();
		     return;
		    } catch (IOException e) {
		    	System.out.println("Erro ao ler o arquivo " + arquivoCSV);
		      //  e.printStackTrace();
		    	
		    return;
		    } finally {
		    	
		     
		        if (br != null) {
		            try {
		                br.close();
		            } catch (IOException e) {
		            	System.out.println("Erro ao ler o arquivo " + arquivoCSV);
		               // e.printStackTrace();
		            }
		        }
		        
		        if (inserindo != null) {
		            try {
		            	inserindo.close();
		            	
		            	System.out.println("Foi gerado o arquivo " + nomeArquivo);
		            } catch (IOException e) {
		            	System.out.println("Erro ao gravar o arquivo " + nomeArquivo);
		               // e.printStackTrace();
		            }
		        }
		    }
		  }
 
	
	private String processaLinha( String[] contasaldo) {
		
		
		try {
			
			double saldo = Double.parseDouble(contasaldo[2].replace(',','.'));
			
			boolean resposta = receitaService.atualizarConta(contasaldo[0], contasaldo[1], saldo, contasaldo[3]);
			
			return Boolean. toString(resposta);
			
		  } catch( RuntimeException  e) {
			  System.out.println("Erro ao processar o arquivo: " + e.getMessage());
		     return "erro";			    	
		   }catch(InterruptedException ei){
			   System.out.println("Erro ao processar o arquivo: " + ei.getMessage());
			  return "erro";	
		   }
		
		 
	}

}
