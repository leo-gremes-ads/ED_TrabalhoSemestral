package model;

public class TipoProduto
{
	public int codigo;
	public String tipo;
	public String descricao;
	
	public TipoProduto()
	{
		super();
	}
	
	public TipoProduto(int cod, String tipo, String desc)
	{
		super();
		this.codigo = cod;
		this.tipo = tipo;
		this.descricao = desc;
	}
	
	@Override
	public String toString()
	{
		return this.tipo + " (" + this.codigo + ") - " + this.descricao;
	}
}
