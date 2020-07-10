package com.ibm.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import com.ibm.demo.model.Conta;
/**
 * Interface para <code>ArquivoCSVService<code>
 * @author bprates
 * @since 10/07/2020
 * @see com.ibm.demo.service.ArquivoCSVServiceImpl
 *
 */
public interface ArquivoCSVService {
	
	public ArrayList<Conta> lerArquivoCSV(String path) throws IOException;

	public void escreverRespostaRecentaNoArquivoCSV(ArrayList<Conta> contas);
}
