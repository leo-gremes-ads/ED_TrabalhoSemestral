package controller;

import java.io.*;
import javax.swing.JOptionPane;
import lib.*;
import model.*;

public class CarrinhoController
{
	public CarrinhoController()
	{
		super();
	}

	public double addicionarAoCarrinho(Lista<ListaTipos<Produto>> tipos, Pilha<Produto> carrinho)
	{
		int cod = Integer.parseInt(
			JOptionPane.showInputDialog("Insira o código do produto:"));
		Produto p = localizarProduto(tipos, cod);
		if (p == null) {
			JOptionPane.showMessageDialog(null, "Produto não encontrado");
			return 0;
		} else {
			int qtd = Integer.parseInt(
				JOptionPane.showInputDialog(
				p.nome + "\nvalor: " + String.format("%.2f", p.valor) +
				"\nQtd disponível: " + p.qtd + "\n\nInsira a quantidade desejada:"));
			carrinho.push(copiarProduto(p, qtd));
			JOptionPane.showMessageDialog(null, "Produto adicionado");
			return p.valor * qtd;
		}
	}

	public void consultarCarrinho(String nome, Pilha<Produto> carrinho)
	{
		try {
			double total = 0;
			Pilha<Produto> aux = new Pilha<>();
			int tamanho = carrinho.size();
			System.out.println(nome + ":");
			if (tamanho == 0)
				System.out.println("\tNão há produtos no carrinho");
			else {
				for (int i = 0; i < tamanho; i++) {
					aux.push(carrinho.pop());
				}
				for (int i = 0; i < tamanho; i++) {
					Produto p = aux.pop();
					total += p.valor * p.qtd;
					System.out.println("\t" + (i + 1) + ": " + String.format("%25s", p.nome) + 
						" - qtd: " + String.format("%4d", p.qtd) + " - Valor unit: R$ " +
						String.format("%7.2f", p.valor) + " - Valor total: R$ " +
						String.format("%9.2f", p.valor * p.qtd));
					carrinho.push(p);
				}
				System.out.println("Total: R$ " + String.format("%10.2f" ,total));
			}
		} catch (Exception e) {
			System.err.println("Erro ao consultar carrinho");
		}
	}

	public void excluirProduto(Pilha<Produto> carrinho)
	{
		try {	
			int item = Integer.parseInt(
				JOptionPane.showInputDialog("Selecione o item que deseja excluir"));
			int tamanho = carrinho.size();
			if (item < 1 || item > tamanho)
				JOptionPane.showMessageDialog(null, "Item inválido");
			else {
				Pilha<Produto> aux = new Pilha<>();
				for (int i = 0; i < tamanho; i++) {
					aux.push(carrinho.pop());
				}
				for (int i = 0; i < tamanho; i++) {
					if (item != i + 1)
						carrinho.push(aux.pop());
					else
						aux.pop();
						
				}
				JOptionPane.showMessageDialog(null, "Produto excluído");
			}
		} catch (Exception e) {
			System.err.println("Erro ao excluir produto do carrinho");
		}
	}

	public void escreverNoCsv(Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("Carrinho.csv"));
			int tamanhoPf = pf.size();
			int tamanhoPj = pj.size();
			for (int i = 0; i < tamanhoPf; i++) {
				ClienteCPF cliente = pf.remove();
				w.write(cliente.cpf);
				Pilha<Produto> carrinho = cliente.carrinho;
				Pilha<Produto> aux = new Pilha<>();
				int tamanhoCarrinho = carrinho.size();
				for (int j = 0; j < tamanhoCarrinho; j++) {
					aux.push(carrinho.pop());
				}
				for (int j = 0; j < tamanhoCarrinho; j++) {
					Produto p = aux.pop();
					w.write(';'); w.write(Integer.toString(p.id));
					w.write(';'); w.write(Integer.toString(p.qtd));
					carrinho.push(p);
				}
				w.newLine();
				pf.insert(cliente);
			}
			for (int i = 0; i < tamanhoPj; i++) {
				ClienteCNPJ clienteJ = pj.remove();
				w.write(clienteJ.cnpj);
				Pilha<Produto> carrinho = clienteJ.carrinho;
				Pilha<Produto> aux = new Pilha<>();
				int tamanhoCarrinho = carrinho.size();
				for (int j = 0; j < tamanhoCarrinho; j++) {
					aux.push(carrinho.pop());
				}
				for (int j = 0; j < tamanhoCarrinho; j++) {
					Produto p = aux.pop();
					w.write(';'); w.write(Integer.toString(p.id));
					w.write(';'); w.write(Integer.toString(p.qtd));
					carrinho.push(p);
				}
				w.newLine();
				pj.insert(clienteJ);
			}
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao escrever carrinhos no csv");
		}
	}

	public Produto copiarProduto(Produto p, int qtd)
	{
		Produto copia = new Produto(p.id, p.nome, p.descricao,
			 p.valor, qtd, p.tipo);
		return copia;
	}

	public Produto localizarProduto(Lista<ListaTipos<Produto>> tipos, int cod)
	{
		try {	
			int tamanhoTipos = tipos.size();
			int tamanhoLista;
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				tamanhoLista = lista.size();
				for (int j = 0; j < tamanhoLista; j++) {
					Produto p = lista.get(j);
					if (p.id == cod)
						return p;
				}
			}
			return null;
		} catch (Exception e) {
			System.err.println("Erro ao localizar produto");
			return null;
		}
	}

	public void menuCheckout(Pilha<Produto> carrinho, Lista<ListaTipos<Produto>> tipos,
		String numeroCliente)
	{
		try {	
			int tamanho = carrinho.size();
			Fila<Produto> caixa = new Fila<>();
			if (tamanho == 0) {
				JOptionPane.showMessageDialog(null, "Carrinho vazio");
				return;
			}
			double total = 0;
			int itens = 0;
			for (int i = 0; i < tamanho; i++) {
				Produto p = carrinho.pop();
				Produto est = localizarProduto(tipos, p.id);
				if (p.qtd <= est.qtd) {
					caixa.insert(p);
					total += p.qtd * p.valor;
					itens++;
					System.out.println((i + 1) + ": " + String.format("%25s", p.nome) + 
						" - qtd: " + String.format("%4d", p.qtd) + " - Valor unit: R$ " +
						String.format("%7.2f", p.valor) + " - Valor total: R$ " +
						String.format("%9.2f", p.valor * p.qtd));
				} else {
					p.qtd = est.qtd;
					caixa.insert(p);
					total += p.qtd * p.valor;
					itens++;
					System.out.println((i + 1) + ": " + String.format("%25s", p.nome) + 
						" - qtd: " + String.format("%4d", p.qtd) + " - Valor unit: R$ " +
						String.format("%7.2f", p.valor) + " - Valor total: R$ " +
						String.format("%9.2f", p.valor * p.qtd) + " * Não há disponibilidade total");
				}
			}
			System.out.println("Total: R$ " + String.format("%.2f", total));
			String[] opcs = {"Registrar", "Cancelar"};
			int op = JOptionPane.showOptionDialog(
				null, itens + " itens\nTotal: R$ " + String.format("%.2f", total) +
				"\n\nSelecione a opção desejada", "Sistema",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				opcs, opcs[1]);
			if (op == 1) {
				Pilha<Produto> aux = new Pilha<>();
				for (int i = 0; i < tamanho; i++) {
					aux.push(caixa.remove());
				}
				for (int i = 0; i < tamanho; i++) {
					carrinho.push(aux.pop());
				}
			}
			else
				checkout(caixa, tipos, numeroCliente, total);
		} catch (Exception e) {
			System.err.println("Erro no menu do chekout");
		}
	}

	private void checkout(Fila<Produto> caixa, Lista<ListaTipos<Produto>> tipos,
		String numeroCliente, double total)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("Compras.csv", true));
			w.write(numeroCliente); w.write(";");
			w.write(Double.toString(total));
			int tamanho = caixa.size();
			for (int i = 0; i < tamanho; i++) {
				Produto p = caixa.remove();
				Produto est = localizarProduto(tipos, p.id);
				est.qtd -= p.qtd;
				if (est.qtd < 0)
					est.qtd = 0;
				w.write(";"); w.write(Integer.toString(p.id));
				w.write(";"); w.write(Integer.toString(p.qtd));
			}
			w.newLine();
			w.close();
			new ProdutoController().escreverProdutosNoCsv(tipos);
		} catch (Exception e) {
			System.err.println("Erro no checkout");
		}
	}
}
