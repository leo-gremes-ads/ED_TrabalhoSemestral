package model;

public class ClienteCPF
{
	public String cpf;
	public String nome;
	public String endereco;
	public String celular;
	
	public ClienteCPF()
	{
		super();
	}
	
	public ClienteCPF(String cpf, String nome, String endereco, String celular)
	{
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
		this.celular = celular;
	}
}
