package com.ibm.demo.model;

import java.text.DecimalFormat;

/**
 * Essa classe é o model usado para armazenar as contas 
 * recebidas pelo arquivo CSV durante toda a vida útil do programa
 * 
 * @author bprates
 * @since 10/07/2020
 * 
 *
 */

public class Conta {
	private String agencia;
	private String conta;
	private double saldo;
	private String status; //seria interessante documentar quais o valores essa variável pode receber
	private boolean resultadoReceita;
	
	/**
	 * Construtor que recebe o arquivo CSV inicial com os seguinte parâmetro:
	 * 
	 * @param agencia <code>String<code> Apenas 4 números no formato 0000 (Regra do Banco Central)
	 * @param conta <code>String<code> Recebe a conta no formato xxxxx-x, importante retirar o "-" 
	 * 			quando for mandar para a Receita Federal
	 * @param saldo <code>double<code> Saldo do usuário
	 * @param status <code>String<code> Aceitar os valores: (estou "chutando")
	 * 									A - Aberta
	 * 									B - Bloqueada
	 * 									I - Inativa
	 * 									...
	 */
	public Conta(String agencia, String conta, double saldo, String status) {
		this.agencia = agencia;
		this.conta = conta;
		this.saldo = saldo;
		this.status = status;
	}
	
	/**
	 * Construtor caso seja necessário criar uma outra lista de objetos do CSV recebido pela receita
	 * 
	 * @param resultadoReceita <code>boolean<code> Resultado recebido do servidor da receita federal.
	 */
	public Conta(String agencia, String conta, double saldo, String status, boolean resultadoReceita) {
		this(agencia, conta, saldo, status);
		this.resultadoReceita = resultadoReceita;
	}
	
	/**
	 * Setters and Getters dos atributos da classe
	 * 
	 * @return Cada classe retorna seu valor
	 */
	
	public String getAgencia() {
		return this.agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	/**
	 * A conta é salta com "-" na classe, mas o sistema da Receita só aceita números,
	 * então a função <code>replaceAll<code> retira os caracteres e letras, deixando apenas números
	 * @see java.util.regex.Pattern
	 * @return <code>String<code>
	 * 
	 */
	public String getContaSemCaracteres() {
		return this.getConta().replaceAll("[\\D]", "");
	}
	public String getConta() {
		return this.conta;
	}
	
	public void setConta(String conta) {
		this.conta = conta;
	}
	public double getSaldo() {
		return this.saldo;
	}
	public String getSaldoFormatadoString() {
		return String.format("%.2f", getSaldo()).replace('.', ',');
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isResultadoReceita() {
		return this.resultadoReceita;
	}
	public String getResultadoReceitaString() {
		return String.valueOf(isResultadoReceita());
	}
	public void setResultadoReceita(boolean resultadoReceita) {
		this.resultadoReceita = resultadoReceita;
	}
	
	/**
	 * Caso seja necessário enviar uma string única no formato do CSV
	 * @return <code>String<code> Formato: <code>"agencia; conta; saldo; status;"<code>
	 */
	public String toStringParaReceita() {
		return this.agencia + ";" +
				this.conta + ";" +
				this.saldo + ";" +
				this.status;
	}
	
	/**
	 * Caso seja necessário enviar uma string única com a resposta da Receita no formato do CSV
	 * @return <code>String<code> Formato: <code>"agencia; conta; saldo; status; resultadoReceita"<code>
	 */
	public String toStringRespostaReceita() {
		return toStringParaReceita() + ";" +
				this.resultadoReceita;
	}
	
	public String toString() {
        return "CsvPessoa{agencia='" + this.agencia + "\',"
        		+ " conta=" + this.conta + 
        		", saldo='" + this.saldo + "\'}";
    }
	
	/**
	 * Método da classe para gerar o cabeçalho do arquivo CSV
	 * @return <code>String[]<code>
	 */
	public static String[] cabecalhoCSV() {
		return new String[] { "agencia","conta","saldo", "status", "resultadoReceita"}; 
	}
	
	/**
	 * Método para obter os valores na geração do arquivo CSV
	 * @return <code>String[]<code>
	 */
	public String[] getArrayValues() {
		return new String[] {getAgencia(), 
							getConta(), 
							getSaldoFormatadoString(),
							getStatus(),
							getResultadoReceitaString()};
	}
}
