package model;

public class ClienteCPF
{
	public int cpf;
	public String nome;
	public String endereco;
	public int celular;
	
	public ClienteCPF()
	{
		super();
	}
	
	public ClienteCPF(int cpf, String nome, String endereco, int celular)
	{
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
		this.celular = celular;
	}
}
