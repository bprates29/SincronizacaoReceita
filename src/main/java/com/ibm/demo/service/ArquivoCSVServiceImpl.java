package com.ibm.demo.service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.ibm.demo.model.Conta;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

/**
 * Classe criada para ser o ponto central para todo o tipo de tratamento em arquivos CSV.
 * Principalmente para leitura e escrita de arquivos no formato CSV.
 * 
 * @author bprates
 * @since 10/07/2020
 *
 */
public class ArquivoCSVServiceImpl implements ArquivoCSVService {
	//Constantes criadas para organização de arquivos CSV, os nome já identificam para o que servem
	public static final String NOME_AQUIVO_DE_SAIDA = "RespostaReceita.csv";
	public static final char DELIMITADOR_DE_COLUNAS = ';';
	int contLinhas; 
	/**
	 * Principal método para ler a entrada de um arquivo CSV de acordo com o padrão definido
	 * fiz um tratamento de erro de acordo com a linha
	 * @param path Nome do arquivo lido
	 * @return <code>ArrayList<Conta><code> A lista de contas retiradas do CSV
	 * @throws Exceção caso não consiga abrir o arquivo ou algum problema de leitura
	 */
	@Override
	public ArrayList<Conta> lerArquivoCSV(String path) {
		ArrayList<Conta> contas = new ArrayList<Conta>();
		CSVParser parser = new CSVParserBuilder().withSeparator(DELIMITADOR_DE_COLUNAS).build();
		contLinhas = 1; //Variável usada para imprimir linhas com erros, inicia na 1 para descontar o cabeçalho
		try (Reader br = Files.newBufferedReader(Paths.get(path));
				CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).withSkipLines(1).build()) {
			List<String[]> linhas = reader.readAll();
			linhas.forEach(linha -> {
				contLinhas=contLinhas+1;
				if (linha.length == 4) { //verifica se existem 4 elementos na linha agencia, conta, saldo, status
					Conta conta = new Conta(linha[0], 
							linha[1], 
							Double.parseDouble(linha[2].replace(',', '.')),  //Como está convertendo para double tem que trocar a vírgula pelo ponto
							linha[3]);
					contas.add(conta);
				}
				else {
					System.out.println("Erro na linha " + contLinhas 
							+ ". Favor verificar.");
				}
				});
		} catch (IOException | SecurityException e) {
			System.out.println("Erro ao abrir o arquivo. Erro: " + e.getCause() 
								+ ". Mensagem: " + e.getMessage());
		}
		return contas;
	}
	
	/**
	 * Esse método é utilizado para escrever em um arquivo CSV as contas que foram processadas.
	 * @param <code>ArrayList<Conta><code> Uma lista com as contas processadas
	 * @throws Exceção caso não consiga abrir um arquivo para escrever, problemas de permição 
	 */
	@Override
	public void escreverRespostaRecentaNoArquivoCSV(ArrayList<Conta> contas) {
		 try (Writer writer = Files.newBufferedWriter(Paths.get(NOME_AQUIVO_DE_SAIDA));
				 CSVWriter csvWriter = new CSVWriter(writer,
						 	DELIMITADOR_DE_COLUNAS,
		                    CSVWriter.NO_QUOTE_CHARACTER,
		                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
		                    CSVWriter.DEFAULT_LINE_END);
		        ) {
		            String[] cabecalho = Conta.cabecalhoCSV();
		            csvWriter.writeNext(cabecalho);
		            contas.forEach(conta -> 
		            				csvWriter.writeNext(conta.getArrayValues()));
		        } catch (IllegalArgumentException | IOException | UnsupportedOperationException | SecurityException e) {
		        	System.out.println("Erro ao escrever o arquivo CSV. Erro: " + e.getCause() 
										+ ". Mensagem: " + e.getMessage());
				}
	}

}
