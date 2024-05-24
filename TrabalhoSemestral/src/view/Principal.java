package view;

import lib.*;
//import model.*;
import controller.*;
import javax.swing.JOptionPane;

public class Principal
{
	public static void main(String[] args)
	{
		TipoController tc = new TipoController();
		Lista<ListaTipos> tipos = new Lista<>();
		while (true) {
			int opt = menu();
			if (opt == 0) tc.adicionarTipo(tipos);
			else if (opt == 1) tc.listarTipos(tipos);
			else if (opt == 2) {
				int cod = Integer.parseInt(
						JOptionPane.showInputDialog(
								"Informe o código do tipo a ser excluído:"));
				tc.excluirTipoPorCod(tipos, cod);
			} else if (opt == 3) {
				int cod = Integer.parseInt(
						JOptionPane.showInputDialog(
								"Informe o código do tipo a ser consultado:"));
				tc.consultarTipoPorCod(tipos, cod);
			} else
				break;
		}
	}
	
	private static int menu()
	{
		String[] opcs = {"Adicionar Tipo", "Listar Tipos", "Excluir Tipos",
				"Consultar por Código", "Sair"};
		return JOptionPane.showOptionDialog(
			null, "Selecione a opção desejada", "Impressora",
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
			opcs, opcs[3]);
	}
}
