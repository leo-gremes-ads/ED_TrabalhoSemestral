package controller;

import model.*;
//import controller.*;
import lib.*;
import java.io.*;
import javax.swing.JOptionPane;

public class ClienteController
{
	public ClienteController()
	{
		super();
	}	

	public void adicionarClientePF(Fila<ClienteCPF> clientes)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("ClientesCPF.csv", true));
			String cpf = JOptionPane.showInputDialog("Informe o CPF:");
			if (cpf.length() != 11) JOptionPane.showMessageDialog(null, "CPF inválido");
			else {
				String nome = JOptionPane.showInputDialog("Informe o nome:");
				String end = JOptionPane.showInputDialog("Informe o endereço:");
				String tel = JOptionPane.showInputDialog("Informe o celular:");
				ClienteCPF cl = new ClienteCPF(cpf, nome, end, tel);
				clientes.insert(cl);
				w.write(cpf); w.write(','); w.write(nome); w.write(',');
				w.write(end); w.write(','); w.write(tel); w.newLine();
			}
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao adicionar cliente cpf");
		}
	}

	public void adicionarClientePJ(Fila<ClienteCNPJ> clientes)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("ClientesCNPJ.csv", true));
			String cnpj = JOptionPane.showInputDialog("Informe o CNPJ:");
			if (cnpj.length() != 14) JOptionPane.showMessageDialog(null, "CPF inválido");
			else {
				String nome = JOptionPane.showInputDialog("Informe o nome:");
				String end = JOptionPane.showInputDialog("Informe o endereço:");
				String tel = JOptionPane.showInputDialog("Informe o telefone:");
				String email = JOptionPane.showInputDialog("Informe o e-mail:");
				ClienteCNPJ cl = new ClienteCNPJ(cnpj, nome, end, tel, email);
				clientes.insert(cl);
				w.write(cnpj); w.write(','); w.write(nome); w.write(',');
				w.write(end); w.write(','); w.write(tel); w.write(',');
				w.write(email); w.newLine();
			}
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao adicionar cliente cnpj");
		}
	}

	public void listarClientes(Fila<ClienteCPF> cpf, Fila<ClienteCNPJ> cnpj)
	{
		try {			
			int tamanhoPF = cpf.size();
			int tamanhoPJ = cnpj.size();
			System.out.println("------------------------");
			System.out.println("Clientes CPF");
			if (tamanhoPF == 0) System.out.println("Não há clientes");
			else {
				for (int i = 0; i < tamanhoPF; i++) {
					ClienteCPF cliente = cpf.remove();
					System.out.println("\t" + String.format("%14s", cliente.cpf) +
						" - " + cliente.nome);
					cpf.insert(cliente);
				}
			}
			System.out.println("Clientes CNPJ");
			if (tamanhoPJ == 0) System.out.println("Não há clientes");
			else {
				for (int i = 0; i < tamanhoPJ; i++) {
					ClienteCNPJ cliente = cnpj.remove();
					System.out.println("\t" + String.format("%14s", cliente.cnpj) +
						" - " + cliente.nome);
					cnpj.insert(cliente);
				}
			}
			System.out.println("------------------------");
		} catch (Exception e) {
			System.err.println("Erro ao listar todos clientes");
		}
	}

	public void consultaClientePorNumero(Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj)
	{
		String cod = JOptionPane.showInputDialog("Informe o CPF ou CNPJ:");
		if (cod.length() == 11) consultaClientePF(pf, cod);
		else if (cod.length() == 14) consultaClientePJ(pj, cod);
		else JOptionPane.showMessageDialog(null, "Número inválido");
	}
	
	private void consultaClientePF(Fila<ClienteCPF> pf, String cpf)
	{
		try {	
			int tamanho = pf.size();
			boolean encontrado = false;
			if (tamanho > 0) {
				for (int i = 0; i < tamanho; i++) {
					ClienteCPF cliente = pf.remove();
					if (cpf.equals(cliente.cpf)) {
						System.out.println("-----------------");
						System.out.println("     CPF: " + cliente.cpf);
						System.out.println("    Nome: " + cliente.nome);
						System.out.println("Endereço: " + cliente.endereco);
						System.out.println(" Celular: " + cliente.celular);
						System.out.println("-----------------");
						encontrado = true;
					}
					pf.insert(cliente);
				}
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
		}
	}

	private void consultaClientePJ(Fila<ClienteCNPJ> pj, String cnpj)
	{
		try {	
			int tamanho = pj.size();
			boolean encontrado = false;
			if (tamanho > 0) {
				for (int i = 0; i < tamanho; i++) {
					ClienteCNPJ cliente = pj.remove();
					if (cnpj.equals(cliente.cnpj)) {
						System.out.println("-----------------");
						System.out.println("    CNPJ: " + cliente.cnpj);
						System.out.println("    Nome: " + cliente.nome);
						System.out.println("Endereço: " + cliente.endereco);
						System.out.println("Telefone: " + cliente.telefone);
						System.out.println("  E-mail: " + cliente.email);
						System.out.println("-----------------");
						encontrado = true;
					}
					pj.insert(cliente);
				}
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cnpj");
		}
	}

	public void excluiClientePorNumero(Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj)
	{
		String cod = JOptionPane.showInputDialog("Informe o CPF ou CNPJ:");
		if (cod.length() == 11) excluiClientePF(pf, cod);
		else if (cod.length() == 14) excluiClientePJ(pj, cod);
		else JOptionPane.showMessageDialog(null, "Número inválido");
	}

	private void excluiClientePF(Fila<ClienteCPF> pf, String cpf)
	{
		try {	
			int tamanho = pf.size();
			boolean encontrado = false;
			for (int i = 0; i < tamanho; i++) {
				ClienteCPF cliente = pf.remove();
				if (!cpf.equals(cliente.cpf))
					pf.insert(cliente);
				else
					encontrado = true;
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
			else {
				JOptionPane.showMessageDialog(null, "cliente removido");
				excluirDoCsvPF(cpf);
			}
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
		}
	}
	
	private void excluirDoCsvPF(String cpf)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("ClientesCPF.csv"));
			BufferedWriter w = new BufferedWriter(new FileWriter("tmp.txt"));
			String linha = r.readLine();
			while (linha != null) {
				w.write(linha); w.newLine();
				linha = r.readLine();
			}
			r.close();
			w.close();
			BufferedReader r2 = new BufferedReader(new FileReader("tmp.txt"));
			BufferedWriter w2 = new BufferedWriter(new FileWriter("ClientesCPF.csv"));
			linha = r2.readLine();
			while (linha != null) {
				String[] termos = linha.split(",");
				if (!termos[0].equals(cpf)) {
					w2.write(linha); w2.newLine();
				}
				linha = r2.readLine();
			}
			r2.close();
			w2.close();
			File del = new File("tmp.txt");
			del.delete();
		} catch (Exception e) {
			System.err.println("Erro ao excluir cliente cpf do csv");
		}
	}

	private void excluiClientePJ(Fila<ClienteCNPJ> pj, String cnpj)
	{
		try {	
			int tamanho = pj.size();
			boolean encontrado = false;
			for (int i = 0; i < tamanho; i++) {
				ClienteCNPJ cliente = pj.remove();
				if (!cnpj.equals(cliente.cnpj))
					pj.insert(cliente);
				else
					encontrado = true;
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
			else {
				JOptionPane.showMessageDialog(null, "cliente removido");
				excluirDoCsvPJ(cnpj);
			}
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
		}
	}

	private void excluirDoCsvPJ(String cnpj)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("ClientesCPF.csv"));
			BufferedWriter w = new BufferedWriter(new FileWriter("tmp.txt"));
			String linha = r.readLine();
			while (linha != null) {
				w.write(linha); w.newLine();
				linha = r.readLine();
			}
			r.close();
			w.close();
			BufferedReader r2 = new BufferedReader(new FileReader("tmp.txt"));
			BufferedWriter w2 = new BufferedWriter(new FileWriter("ClientesCPF.csv"));
			linha = r2.readLine();
			while (linha != null) {
				String[] termos = linha.split(",");
				if (!termos[0].equals(cnpj)) {
					w2.write(linha); w2.newLine();
				}
				linha = r2.readLine();
			}
			r2.close();
			w2.close();
			File del = new File("tmp.txt");
			del.delete();
		} catch (Exception e) {
			System.err.println("Erro ao excluir cliente cpf do csv");
		}
	}
}
