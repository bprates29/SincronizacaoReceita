package com.ibm.demo.service;

import java.util.ArrayList;
import com.ibm.demo.model.Conta;

/**
 * Interface para <code>SincronizacaoReceitaService<code>
 * @author bprates
 * @since 10/07/2020
 * @see com.ibm.demo.service.SincronizacaoReceitaServiceImpl
 *
 */
public interface SincronizacaoReceitaService {
	
	public void enviarArquivoParaReceita(ArrayList<Conta> contas);
}
