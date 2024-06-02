package model;

public class ClienteCNPJ
{
	public String cnpj;
	public String nome;
	public String endereco;
	public String telefone;
	public String email;
	
	public ClienteCNPJ()
	{
		super();
	}
	
	public ClienteCNPJ(String cnpj, String nome, String endereco, String telefone, String email)
	{
		super();
		this.cnpj = cnpj;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.email = email;
	}
}
