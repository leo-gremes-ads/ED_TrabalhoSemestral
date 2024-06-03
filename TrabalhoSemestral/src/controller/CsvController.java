package controller;

import model.*;
import view.Principal;
//import controller.*;
import lib.*;
import java.io.*;
import javax.swing.JOptionPane;

public class CsvController
{
	public CsvController()
	{
		super();
	}

	public Lista<ListaTipos<Produto>> lerCsvTipos()
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader ("Tipos.csv"));
			Lista<ListaTipos<Produto>> tipos = new Lista<>();
			String linha = br.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				TipoProduto t = new TipoProduto(
					Integer.parseInt(termos[0]), termos[1], termos[2]);
				ListaTipos<Produto> lista = new ListaTipos<>(t);
				tipos.addLast(lista);
				linha = br.readLine();
			}
			br.close();
			adicionarProdutosCsv(tipos);
			return tipos;
		} catch (Exception e) {
			//System.err.println("Erro ao ler tipos.csv");
			return new Lista<ListaTipos<Produto>>();
		}
	}

	public void adicionarProdutosCsv(Lista<ListaTipos<Produto>> tipos)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("Produtos.csv"));
			ProdutoController pc = new ProdutoController();
			String linha = r.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				int indice = pc.indiceDoTipo(tipos, Integer.parseInt(termos[5]));
				TipoProduto t = tipos.get(indice).getTipo();
				Produto p = new Produto(Integer.parseInt(termos[0]), termos[1],
					termos[2], Double.parseDouble(termos[3]), Integer.parseInt(termos[4]), t);
				tipos.get(indice).addLast(p);
				linha = r.readLine();
			}
			r.close();
		} catch (Exception e) {
			System.err.println("Erro ao adicionar produtos do csv");
		}
	}

	public Fila<ClienteCPF> adicionarClientePF()
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader ("ClientesCPF.csv"));
			Fila<ClienteCPF> pf = new Fila<>();
			String linha = br.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				ClienteCPF cl = new ClienteCPF(termos[0], termos[1], termos[2],
					termos[3]);
				pf.insert(cl);
				linha = br.readLine();
			}
			br.close();
			return pf;
		} catch (Exception e) {
			System.err.println("Erro ao ler tipos.csv");
			return new Fila<ClienteCPF>();
		}
	}

	public Fila<ClienteCNPJ> adicionarClientePJ()
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader ("ClientesCNPJ.csv"));
			Fila<ClienteCNPJ> pj = new Fila<>();
			String linha = br.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				ClienteCNPJ cl = new ClienteCNPJ(termos[0], termos[1], termos[2],
					termos[3], termos[4]);
				pj.insert(cl);
				linha = br.readLine();
			}
			br.close();
			return pj;
		} catch (Exception e) {
			System.err.println("Erro ao ler tipos.csv");
			return new Fila<ClienteCNPJ>();
		}
	}

	public void adicionarCarrinhos(Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj,
		CarrinhoController c, Lista<ListaTipos<Produto>> tipos)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("Carrinho.csv"));
			String linha = r.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				if (termos[0].length() == 11) {
					ClienteCPF clienteF = Principal.localizaClientePF(pf, termos[0]);
					int i = 1;
					while (i < termos.length) {
						Produto p = c.localizarProduto(tipos, Integer.parseInt(termos[i++]));
						Produto add = c.copiarProduto(p, Integer.parseInt(termos[i++]));
						clienteF.carrinho.push(add);
					}
				}
				if (termos[0].length() == 14) {
					ClienteCNPJ clienteJ = Principal.localizaClientePJ(pj, termos[0]);
					int i = 1;
					while (i < termos.length) {
						Produto p = c.localizarProduto(tipos, Integer.parseInt(termos[i++]));
						Produto add = c.copiarProduto(p, Integer.parseInt(termos[i++]));
						clienteJ.carrinho.push(add);
					}
				}
				linha = r.readLine();
			}
			r.close();
		} catch (Exception e) {
			System.err.println("Erro ao ler carrinhos csv");
		}
	}

	public void listarCompras(Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("Compras.csv"));
			String linha = r.readLine();
			String nome;
			int i = 1;
			if (linha == null) {
				System.out.println("Não há compras registradas:");
				r.close();
				return;
			}
			System.out.println("-----------------------");
			while (linha != null) {
				String[] termos = linha.split(";");
				if (termos[0].length() == 11) {
					nome = Principal.localizaClientePF(pf, termos[0]).nome;
				} else {
					nome = Principal.localizaClientePJ(pj, termos[0]).nome;
				}
				System.out.println(String.format("%3d", i++) + ": " +
					String.format("%25s", nome) + " - " + 
					String.format("%3d", ((termos.length - 2) / 2)) +
					" itens - Total: R$ " + String.format("%7.2f", Double.parseDouble(termos[1])));
				linha = r.readLine();
			}
			System.out.println("-----------------------");
			r.close();
		} catch (Exception e) {
			System.err.println("Erro ao listar compras");
		}
	}

	public void consultarCompra(Lista<ListaTipos<Produto>> tipos, 
		Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj, CarrinhoController cc)
	{
		try {
			int num = Integer.parseInt(
				JOptionPane.showInputDialog("Informe o número da compra para consulta:"));
			boolean encontrado = false;
			String nome;
			BufferedReader r = new BufferedReader(new FileReader("Compras.csv"));
			String linha = r.readLine();
			int i = 1;
			while (linha != null && i <= num) {
				if (i == num) {
					String[] termos = linha.split(";");
					if (termos[0].length() == 11) {
						nome = Principal.localizaClientePF(pf, termos[0]).nome;
					} else {
						nome = Principal.localizaClientePJ(pj, termos[0]).nome;
					}
					System.out.println("Cliente: " + nome);
					System.out.println(String.format("%3d", ((termos.length - 2) / 2)) +
					" itens - Total: R$ " + String.format("%7.2f", Double.parseDouble(termos[1])));
					int tamanho = termos.length;
					int j = 2;
					while (j < tamanho) {
						Produto p = cc.localizarProduto(tipos, Integer.parseInt(termos[j++]));
						System.out.println(String.format("%25s", p.nome) +
							" - " + String.format("%3d", Integer.parseInt(termos[j])) +
							" x R$ " + String.format("%7.2f", p.valor) + " = R$ " +
							String.format("%10.2f", p.valor * Integer.parseInt(termos[j++])));
					}
					encontrado = true;
				}
				linha = r.readLine();
				i++;
			}
			if (!encontrado)
				JOptionPane.showMessageDialog(null, "Compra não encontrada");
			r.close();
		} catch (Exception e) {
			System.err.println("Erro ao consultar compra");
		}
	}
}
