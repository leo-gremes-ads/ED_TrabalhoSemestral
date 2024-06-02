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
		Fila<ClienteCNPJ> pj = csvc.adicionarClientePJ();
		Fila<ClienteCPF> pf = csvc.adicionarClientePF();
		Lista<ListaTipos<Produto>> tipos = csvc.lerCsvTipos();
		
		while (true) {
			int op = menuPrincipal();
			if (op == 0)
				menuTipos(tc, tipos);
			else if (op == 1)
				menuProdutos(pc, tipos);
			else if (op == 2)
				menuClientes(cc, pf, pj);
			else
				break;
		}
		JOptionPane.showMessageDialog(null, "Aplicação encerrada");
	}

	private static int menuPrincipal()
	{
		String[] opcs = {"Tipos", "Produtos", "Clientes", "Sair"};
		return JOptionPane.showOptionDialog(
			null, "Selecione a op��o desejada", "Sistema",
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
			opcs, opcs[3]);
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
			else
				break;
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
}
