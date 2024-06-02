package controller;

import lib.*;
import model.*;
import javax.swing.JOptionPane;
import java.io.*;

public class TipoController
{
	public TipoController()
	{
		super();
	}
	
	//FALTANDO GRAVAR NO CSV
	public void adicionarTipo(Lista<ListaTipos<Produto>> tipos)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("Tipos.csv", true));
			int cod = Integer.parseInt(
					JOptionPane.showInputDialog("Insira o c�digo"));
			String nome = JOptionPane.showInputDialog("Insira o nome:");
			String desc = JOptionPane.showInputDialog("Insira a descri��o");
			w.write(Integer.toString(cod)); w.write(';'); w.write(nome); w.write(';'); w.write(desc);
			w.newLine();		
			ListaTipos<Produto> listaTipo = 
			new ListaTipos<>(new TipoProduto(cod, nome, desc));
			tipos.addLast(listaTipo);
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao adicionar tipo");
		}
	}
	
	public void excluirTipoPorCod(Lista<ListaTipos<Produto>> tipos, int cod)
	{
		try {
			int tamanho = tipos.size();
			boolean excluido = false;
			for (int i = 0; i < tamanho; i++) {
				ListaTipos<Produto> atual = tipos.get(0);
				tipos.removeFirst();
				TipoProduto tipo = atual.getTipo();
				if (tipo.codigo != cod)
					tipos.addLast(atual);
				else
					excluido = true;
			}
			if (excluido) {
				excluirDoCsv(cod);
				JOptionPane.showMessageDialog(null, "Tipo exclu�do");
			}
			else
				JOptionPane.showMessageDialog(null, "Tipo n�o encontrado");
		} catch (Exception e) {
			System.err.println("Erro ao excluir tipo por c�digo");
		}		
	}

	private void excluirDoCsv(int cod)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("Tipos.csv"));
			BufferedWriter w = new BufferedWriter(new FileWriter("tmp.txt"));
			String linha = r.readLine();
			while (linha != null) {
				w.write(linha); w.newLine();
				linha = r.readLine();
			}
			r.close();
			w.close();
			BufferedReader r2 = new BufferedReader(new FileReader("tmp.txt"));
			BufferedWriter w2 = new BufferedWriter(new FileWriter("Tipos.csv"));
			linha = r2.readLine();
			while (linha != null) {
				String[] termos = linha.split(",");
				if (Integer.parseInt(termos[0]) != cod) {
					w2.write(linha); w2.newLine();
				}
				linha = r2.readLine();
			}
			r2.close();
			w2.close();
			File del = new File("tmp.txt");
			del.delete();
		} catch (Exception e) {
			System.err.println("Erro ao excluir tipo do csv");
		}
	}
	
	public void listarTipos(Lista<ListaTipos<Produto>> tipos)
	{
		try {
			int tamanho = tipos.size();
			if (tamanho == 0)
				System.out.println("Lista vazia");
			else {
				System.out.println("------------------------------");
				for (int i = 0; i < tamanho; i++) {
					ListaTipos<Produto> lista = tipos.get(0);
					TipoProduto tipo = lista.getTipo();
					System.out.println(tipo.tipo + "(id = " + tipo.codigo + ")");
					tipos.removeFirst();
					tipos.addLast(lista);
				}
				System.out.println("------------------------------");
			}
		} catch (Exception e) {
			System.err.println("Erro na listagem de tipos");
		}
	}
	
	public void consultarTipoPorCod(Lista<ListaTipos<Produto>> tipos, int cod)
	{
		try {
			int tamanho = tipos.size();
			boolean encontrado = false;
			for (int i = 0; i < tamanho; i++) {
				ListaTipos<Produto> lista = tipos.get(0);
				TipoProduto tipo = lista.getTipo();
				if (cod == tipo.codigo) {
					encontrado = true;
					System.out.println("---------------------");
					System.out.println("         C�digo: " + tipo.codigo);
					System.out.println("           Nome: " + tipo.tipo);
					System.out.println("      Descri��o: " + tipo.descricao);
					System.out.println("Qtd de Produtos: " + lista.size());
					System.out.println("---------------------");
				}
				tipos.removeFirst();
				tipos.addLast(lista);
			}
			if (!encontrado)
				JOptionPane.showMessageDialog(null, "Tipo n�o encontrado");
		} catch (Exception e) {
			System.err.println("Erro na consulta de tipo por c�digo");
		}
	}
}