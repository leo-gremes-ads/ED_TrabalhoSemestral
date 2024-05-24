package controller;

import lib.*;
import model.*;
import javax.swing.JOptionPane;

public class TipoController
{
	public TipoController()
	{
		super();
	}
	
	//FALTANDO GRAVAR NO CSV
	public void adicionarTipo(Lista<ListaTipos> tipos)
	{
		int cod = Integer.parseInt(
				JOptionPane.showInputDialog("Insira o código"));
		String nome = JOptionPane.showInputDialog("Insira o nome:");
		String desc = JOptionPane.showInputDialog("Insira a descrição");
		ListaTipos<TipoProduto> listaTipo = 
				new ListaTipos<>(new TipoProduto(cod, nome, desc));
		try {
			tipos.addLast(listaTipo);
		} catch (Exception e) {
			System.err.println("Erro ao adicionar tipo");
		}
	}
	
	public void excluirTipoPorCod(Lista<ListaTipos> tipos, int cod)
	{
		try {
			int tamanho = tipos.size();
			boolean excluido = false;
			for (int i = 0; i < tamanho; i++) {
				ListaTipos<TipoProduto> atual = tipos.get(0);
				tipos.removeFirst();
				TipoProduto tipo = atual.getTipo();
				if (tipo.codigo != cod)
					tipos.addLast(atual);
				else
					excluido = true;
			}
			if (excluido)
				JOptionPane.showMessageDialog(null, "Tipo excluído");
			else
				JOptionPane.showMessageDialog(null, "Tipo não encontrado");
		} catch (Exception e) {
			System.err.println("Erro ao excluir tipo por código");
		}		
	}
	
	public void listarTipos(Lista<ListaTipos> tipos)
	{
		try {
			int tamanho = tipos.size();
			if (tamanho == 0)
				System.out.println("Lista vazia");
			else {
				System.out.println("------------------------------");
				for (int i = 0; i < tamanho; i++) {
					ListaTipos<TipoProduto> lista = tipos.get(0);
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
	
	public void consultarTipoPorCod(Lista<ListaTipos> tipos, int cod)
	{
		try {
			int tamanho = tipos.size();
			boolean encontrado = false;
			for (int i = 0; i < tamanho; i++) {
				ListaTipos<TipoProduto> lista = tipos.get(0);
				TipoProduto tipo = lista.getTipo();
				if (cod == tipo.codigo) {
					encontrado = true;
					System.out.println("---------------------");
					System.out.println("         Código: " + tipo.codigo);
					System.out.println("           Nome: " + tipo.tipo);
					System.out.println("      Descrição: " + tipo.descricao);
					System.out.println("Qtd de Produtos: " + lista.size());
					System.out.println("---------------------");
				}
				tipos.removeFirst();
				tipos.addLast(lista);
			}
			if (!encontrado)
				JOptionPane.showMessageDialog(null, "Tipo não encontrado");
		} catch (Exception e) {
			System.err.println("Erro na consulta de tipo por código");
		}
	}
}