package com.ibm.demo;

import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.ibm.demo.model.Conta;
import com.ibm.demo.service.ArquivoCSVService;
import com.ibm.demo.service.ArquivoCSVServiceImpl;
import com.ibm.demo.service.SincronizacaoReceitaService;
import com.ibm.demo.service.SincronizacaoReceitaServiceImpl;

@SpringBootApplication
public class SincronizacaoReceitaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SincronizacaoReceitaApplication.class, args);
	}
	
	@Bean
	public SincronizacaoReceitaService getSincronizacaoReceitaService() {
		return new SincronizacaoReceitaServiceImpl();
	}
	@Bean
	public ArquivoCSVService getArquivoCSVService() {
		return new ArquivoCSVServiceImpl();
	}
	
	@Override
	public void run(String... args) throws Exception {
		if (args.length == 1) {
			ArrayList<Conta> contas = getArquivoCSVService().lerArquivoCSV(args[0]);
			getSincronizacaoReceitaService().enviarArquivoParaReceita(contas);
			getArquivoCSVService().escreverRespostaRecentaNoArquivoCSV(contas);
		} else {
			System.out.println("É necessário passar um arquivo nos argumentos para ser processado!");
		}

	}


}
