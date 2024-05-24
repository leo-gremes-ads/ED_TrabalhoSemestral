package model;

public class Produto
{
	public int id;
	public String nome;
	public String descricao;
	public double valor;
	public int qtd;
	public TipoProduto tipo;
	
	public Produto()
	{
		super();
	}
	
	public Produto(int id, String nome, String desc, double valor, int qtd, TipoProduto tipo)
	{
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = desc;
		this.valor = valor;
		this.qtd = qtd;
		this.tipo = tipo;
	}
}
