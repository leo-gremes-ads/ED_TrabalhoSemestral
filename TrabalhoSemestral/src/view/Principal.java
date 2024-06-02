package view;

import lib.*;
import model.*;
import controller.*;
import javax.swing.JOptionPane;

public class Principal
{
	public static void main(String[] args)
	{
		CsvController csvc = new CsvController();
		TipoController tc = new TipoController();
		ProdutoController pc = new ProdutoController();
		ClienteController cc = new ClienteController();
		CarrinhoController carc = new CarrinhoController();
		Fila<ClienteCNPJ> pj = csvc.adicionarClientePJ();
		Fila<ClienteCPF> pf = csvc.adicionarClientePF();
		Lista<ListaTipos<Produto>> tipos = csvc.lerCsvTipos();
		csvc.adicionarCarrinhos(pf, pj, carc, tipos);		
		while (true) {
			int op = menuPrincipal();
			if (op == 0)
				menuTipos(tc, tipos);
			else if (op == 1)
				menuProdutos(pc, tipos);
			else if (op == 2)
				menuClientes(cc, pf, pj);
			else if (op == 3)
				menuCarrinho(carc, pf, pj, tipos);
			else
				break;
		}
		JOptionPane.showMessageDialog(null, "Aplicação encerrada");
	}

	private static int menuPrincipal()
	{
		String[] opcs = {"Tipos", "Produtos", "Clientes", "Carrinho", "Sair"};
		return JOptionPane.showOptionDialog(
			null, "Selecione a op��o desejada", "Sistema",
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
			opcs, opcs[4]);
	}

	private static void menuProdutos(ProdutoController pc, Lista<ListaTipos<Produto>> tipos)
	{
		String[] opcs = {"Adicionar Produto", "Consultar Produtos por tipo",
			"Listar Produtos", "Consultar Produto", "Excluir Produto", "Voltar"};
		while (true) {
			int op = JOptionPane.showOptionDialog(
				null, "Selecione a op��o desejada", "Sistema",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				opcs, opcs[2]);
			if (op == 0)
				pc.adicionarProduto(tipos);
			else if (op == 1)
				pc.consultarProdutosDoTipo(tipos);
			else if (op == 2)
				pc.listarTodosProdutos(tipos);
			else if (op == 3)
				pc.consultarProdutoPorId(tipos);
			else if (op == 4)
				pc.excluirProdutoPorId(tipos);
			else {
				pc.escreverProdutosNoCsv(tipos);
				break;
			}
		}
	}
	
	private static void menuTipos(TipoController tc, Lista<ListaTipos<Produto>> tipos)
	{
		String[] opcs = {"Adicionar Tipo", "Listar Tipos", "Excluir Tipos",
			"Consultar por C�digo", "Voltar"};
		while (true) {
			int op = JOptionPane.showOptionDialog(
				null, "Selecione a op��o desejada", "Sistema",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				opcs, opcs[3]);
			if (op == 0) tc.adicionarTipo(tipos);
			else if (op == 1) tc.listarTipos(tipos);
			else if (op == 2) {
				int cod = Integer.parseInt(
						JOptionPane.showInputDialog(
								"Informe o c�digo do tipo a ser exclu�do:"));
				tc.excluirTipoPorCod(tipos, cod);
			} else if (op == 3) {
				int cod = Integer.parseInt(
						JOptionPane.showInputDialog(
								"Informe o c�digo do tipo a ser consultado:"));
				tc.consultarTipoPorCod(tipos, cod);
			} else
				break;
		}
	}

	private static void menuClientes(ClienteController cc, Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj)
	{
		String[] opcs = {"Adicionar Cliente PF", "Adicionar Cliente PJ",
			"Listar Clientes", "Consultar Cliente", "Excluir Cliente", "Voltar"};
		while (true) {
			int op = JOptionPane.showOptionDialog(
				null, "Selecione a op��o desejada", "Sistema",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				opcs, opcs[5]);
			if (op == 0)
				cc.adicionarClientePF(pf);
			else if (op == 1)
				cc.adicionarClientePJ(pj);
			else if (op == 2)
				cc.listarClientes(pf, pj);
			else if (op == 3)
				cc.consultaClientePorNumero(pf, pj);
			else if (op == 4)
				cc.excluiClientePorNumero(pf, pj);
			else
				break;
		}
	}

	public static void menuCarrinho(CarrinhoController carc, Fila<ClienteCPF> pf, 
		Fila<ClienteCNPJ> pj, Lista<ListaTipos<Produto>> tipos)
	{
		Pilha<Produto> carrinho;
		String nome;
		String cod = JOptionPane.showInputDialog("Informe o CPF ou CNPJ:");
		if (cod.length() == 11) {
			ClienteCPF cliente = localizaClientePF(pf, cod);
			if (cliente == null) return;
			carrinho = cliente.carrinho;
			nome = cliente.nome;
		}
		else if (cod.length() == 14) {
			ClienteCNPJ cliente = localizaClientePJ(pj, cod);
			if (cliente == null) return;
			carrinho = cliente.carrinho;
			nome = cliente.nome;
		}
		else {
			JOptionPane.showMessageDialog(null, "Número inválido");
			carrinho = null;
			nome = "";
			return;
		}
		String[] opcs = {"Adicionar produto", "Consultar carrinho",
			"Excluir Produto", "Checkout", "Voltar"};
		while (true) {
			int op = JOptionPane.showOptionDialog(
				null, "Carrinho de " + nome, "Sistema",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				opcs, opcs[4]);
			if (op == 0)
				carc.addicionarAoCarrinho(tipos, carrinho);
			else if (op == 1)
				carc.consultarCarrinho(nome, carrinho);
			else if (op == 2)
				carc.excluirProduto(carrinho);
			else if (op == 3)
				carc.menuCheckout(carrinho, tipos, cod);
			else {
				carc.escreverNoCsv(pf, pj);
				break;
			}
		}		
	}
	
	public static ClienteCPF localizaClientePF(Fila<ClienteCPF> pf, String cpf)
	{
		try {	
			ClienteCPF retorno = null;
			int tamanho = pf.size();
			if (tamanho > 0) {
				for (int i = 0; i < tamanho; i++) {
					ClienteCPF cliente = pf.remove();
					if (cpf.equals(cliente.cpf)) {
						retorno = cliente;
					}
					pf.insert(cliente);
				}
			}
			if (retorno == null) {
				JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
				return null;
			}
			else return retorno;
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
			return null;
		}
	}

	public static ClienteCNPJ localizaClientePJ(Fila<ClienteCNPJ> pj, String cnpj)
	{
		try {	
			ClienteCNPJ retorno = null;
			int tamanho = pj.size();
			if (tamanho > 0) {
				for (int i = 0; i < tamanho; i++) {
					ClienteCNPJ cliente = pj.remove();
					if (cnpj.equals(cliente.cnpj)) {
						retorno = cliente;
					}
					pj.insert(cliente);
				}
			}
			if (retorno == null) {
				JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
				return null;
			}
			else return retorno;
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
			return null;
		}
	}
}
