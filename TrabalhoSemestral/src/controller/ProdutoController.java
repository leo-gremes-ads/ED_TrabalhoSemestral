package controller;

import lib.*;
import model.*;
import javax.swing.JOptionPane;
import java.io.*;

public class ProdutoController
{
	public ProdutoController()
	{
		super();
	}

	public void adicionarProduto(Lista<ListaTipos<Produto>> tipos)
	{
		int cod = Integer.parseInt(
			JOptionPane.showInputDialog("Insira o código do tipo do produto:"));
		int indice = indiceDoTipo(tipos, cod);
		if (indice == -1)
			JOptionPane.showMessageDialog(null, "Tipo não encontrado");
		else {
			try {
				Produto p = criarProduto(tipos, indice);
				tipos.get(indice).addLast(p);
			} catch (Exception e) {
				System.err.println("Erro ao adicionar produto");
			}
		}
	}

	public void listarTodosProdutos(Lista<ListaTipos<Produto>> tipos)
	{
		int tamanhoTipos = tipos.size();
		int tamanhoLista;
		if (tamanhoTipos == 0)
			System.out.println("Não há produtos!");
		try {
			System.out.println("---------------------------");
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				System.out.println(lista.getTipo().tipo);
				tamanhoLista = lista.size();
				if (tamanhoLista == 0)
					System.out.println("\tNão há produtos para esse tipo");
				else {
					for (int j = 0; j < tamanhoLista; j++) {
						Produto p = lista.get(j);
						System.out.println("\t" +
							String.format("%4d", p.id) + ": " + String.format("%-25s", p.nome) +
							" - valor: " + String.format("%7.2f", p.valor) + " - qtd: " +
							String.format("%4d", p.qtd));
					}
				}
			}
			System.out.println("---------------------------");
		} catch (Exception e) {
			System.err.println("Erro ao consultar todos os produtos");
		}
	}

	public void consultarProdutosDoTipo(Lista<ListaTipos<Produto>> tipos)
	{
		int cod = Integer.parseInt(
			JOptionPane.showInputDialog("Insira o código do tipo a ser consultado:"));
		int indice = indiceDoTipo(tipos, cod);
		if (indice == -1)
			JOptionPane.showMessageDialog(null, "Tipo não encontrado");
		else {
			try {
				ListaTipos<Produto> lista = tipos.get(indice);
				System.out.println("---------------------------");
				System.out.println(lista.getTipo().tipo);
				int tamanho = lista.size();
				if (tamanho == 0)
					System.out.println("\tNão há produtos para esse tipo");
				else {
					for (int i = 0; i < tamanho; i++) {
						Produto p = lista.get(0);
						System.out.println("\t" + p.nome + "[qtd: " + p.qtd + " - valor: " + p.valor + "]");
						lista.removeFirst();
						lista.addLast(p);
					}
				}
				System.out.println("---------------------------");
			} catch (Exception e) {
				System.err.println("Erro ao consultar produtos de um tipo");
			}

		}
	}

	public void consultarProdutoPorId(Lista<ListaTipos<Produto>> tipos)
	{
		try {	
			int id = Integer.parseInt(
				JOptionPane.showInputDialog("Insira o id do produto a ser consultado:"));
			int tamanhoTipos = tipos.size();
			int tamanhoLista;
			boolean encontrado = false;
			System.out.println("-----------------------------");
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				tamanhoLista = lista.size();
				for (int j = 0; j < tamanhoLista; j++) {
					Produto p = lista.get(j);
					if (p.id == id) {
						System.out.println(" Id do produto: " + p.id);
						System.out.println("          Nome: " + p.nome);
						System.out.println("     Descrição: " + p.descricao);
						System.out.println("         Valor: R$ " + String.format("%.2f", p.valor));
						System.out.println("Qtd disponível: " + p.qtd);
						System.out.println("          Tipo: " + p.tipo.tipo);
						encontrado = true;
						break;
					}
				}
				if (encontrado) break;
			}
			if (!encontrado) System.out.println("Produto não encontrado");
			System.out.println("-----------------------------");
		} catch (Exception e) {
			System.err.println("Erro ao consultar produto por id");
		}
	}

	public void excluirProdutoPorId(Lista<ListaTipos<Produto>> tipos)
	{
		try {	
			int id = Integer.parseInt(
				JOptionPane.showInputDialog("Insira o id do produto a ser consultado:"));
			int tamanhoTipos = tipos.size();
			int tamanhoLista;
			boolean encontrado = false;
			System.out.println("-----------------------------");
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				tamanhoLista = lista.size();
				for (int j = 0; j < tamanhoLista; j++) {
					Produto p = lista.get(j);
					if (p.id == id) {
						lista.remove(j);
						encontrado = true;
						break;
					}
				}
				if (encontrado) break;
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Produto não encontrado");
			System.out.println("-----------------------------");
		} catch (Exception e) {
			System.err.println("Erro ao consultar produto por id");
		}
	}

	private Produto criarProduto(Lista<ListaTipos<Produto>> tipos, int indice)
	{

		try {
			int id = Integer.parseInt(
				JOptionPane.showInputDialog("Insira o id do produto:"));
			String nome = JOptionPane.showInputDialog("Insira o nome do produto:");
			String desc = JOptionPane.showInputDialog("Insira a descrição do produto:");
			double valor = Double.parseDouble(
				JOptionPane.showInputDialog("Insira o valor do produto:"));
			int qtd = Integer.parseInt(
				JOptionPane.showInputDialog("Insira a quantidade disponível: "));
			TipoProduto tipo = tipos.get(indice).getTipo();
			Produto p = new Produto(id, nome, desc, valor, qtd, tipo);
			return p;
		} catch (Exception e) {
			System.err.println("Erro na criação de produto");
			return null;
		}
	}

	public void escreverProdutosNoCsv(Lista<ListaTipos<Produto>> tipos)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("Produtos.csv"));
			int tamanhoTipos = tipos.size();
			int tamanhoLista;
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				tamanhoLista = lista.size();
				for (int j = 0; j < tamanhoLista; j++) {
					Produto p = lista.get(j);
					w.write(Integer.toString(p.id)); w.write(';'); w.write(p.nome); w.write(';');
					w.write(p.descricao); w.write(';'); w.write(Double.toString(p.valor)); w.write(';');
					w.write(Integer.toString(p.qtd)); w.write(';'); w.write(Integer.toString(p.tipo.codigo));
					w.newLine();
				}
			}
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao escrever produtos no csv");
		}
	}

	public int indiceDoTipo(Lista<ListaTipos<Produto>> tipos, int cod)
	{
		int tamanho = tipos.size();
		if (tamanho == 0)
			return -1;
		ListaTipos<Produto> atual;
		TipoProduto tipo;
		try {
			for (int i = 0; i < tamanho; i++) {
				atual = tipos.get(i);
				tipo = atual.getTipo();
				if (tipo.codigo == cod)
					return i;
		}
		return -1;
		} catch (Exception e) {
			return -1;
		}
	}
	
}
