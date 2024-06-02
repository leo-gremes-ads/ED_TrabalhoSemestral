package controller;

import model.*;
//import controller.*;
import lib.*;
import java.io.*;

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
				String[] termos = linha.split(",");
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
				String[] termos = linha.split(",");
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
				String[] termos = linha.split(",");
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
				String[] termos = linha.split(",");
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
}
