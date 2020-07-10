package com.ibm.demo.service;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import com.ibm.demo.model.Conta;
import com.ibm.demo.receita.service.ReceitaService;

/**
 * Classe criada para ser o ponto central para toda a sincronização e envios de dados
 * para o servidor da receita.
 * 
 * @author bprates
 * @since 10/07/2020
 *
 */
public class SincronizacaoReceitaServiceImpl implements SincronizacaoReceitaService {
	@Bean
	public ReceitaService getReceitaService() {
		return new ReceitaService();
	}
	
	/**
	 * Método central para envio das contas para o servidor da receita
	 * @param ArrayList<Conta> Lista de todas as contas a serem processadas
	 * @throws Exceção na comunicação, na máquina virtual ou interrupção durante o processamento
	 */
	@Override
	public void enviarArquivoParaReceita(ArrayList<Conta> contas) {
		System.out.print("Executando.");
		contas.forEach(conta -> {
			try {
				boolean resposta = getReceitaService()
									.atualizarConta(conta.getAgencia(),
											conta.getContaSemCaracteres(), 
											conta.getSaldo(),
											conta.getStatus());
				conta.setResultadoReceita(resposta);
				System.out.print(".");
			} catch (RuntimeException | InterruptedException e) {
				System.out.print("Erro: "+ e.getMessage());
			}
		});
		System.out.print("Fim.");
	}

}
