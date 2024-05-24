package model;

public class ClienteCNPJ
{
	public int cnpj;
	public String nome;
	public String endereco;
	public int telefone;
	public String email;
	
	public ClienteCNPJ()
	{
		super();
	}
	
	public ClienteCNPJ(int cnpj, String nome, String endereco, int telefone, String email)
	{
		super();
		this.cnpj = cnpj;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
	}
}
