package lib;

import model.TipoProduto;

public class ListaTipos<T>
{
	private TipoProduto tipo;
	private No<T> primeiro;
	
	public ListaTipos()
	{
		primeiro = null;		
	}
	
	public ListaTipos(TipoProduto tipo)
	{
		super();
		primeiro = null;
		this.tipo = tipo;
	}
	
	public TipoProduto getTipo()
	{
		return this.tipo;
	}
	
	public void setTipo(TipoProduto tipo)
	{
		this.tipo = tipo;
	}
	
	public void addFirst(T t)
	{
		No<T> elemento = new No<>();
		elemento.dado = t;
		elemento.proximo = primeiro;
		primeiro = elemento;
	}

	public void addLast(T t) throws Exception
	{
		if (isEmpty())
			addFirst(t);
		else {
			int tamanho = size();
			No<T> elemento = new No<>();
			elemento.dado = t;
			elemento.proximo = null;
			No<T> ultimo = getNo(tamanho - 1);
			ultimo.proximo = elemento;
		}
	}

	public void add(T t, int posicao) throws Exception
	{
		int tamanho = size();
		if (posicao < 0 || posicao > tamanho)
			throw new Exception("Posi��o inv�lida");
		if (posicao == 0)
			addFirst(t);
		else if (posicao == tamanho)
			addLast(t);
		else {
			No<T> anterior = getNo(posicao - 1);
			No<T> elemento = new No<>();
			elemento.dado = t;
			elemento.proximo = anterior.proximo;
			anterior.proximo = elemento;
		}
	}

	public void removeFirst() throws Exception
	{
		if (isEmpty())
			throw new Exception("Lista vazia");
		primeiro = primeiro.proximo;
	}

	public void removeLast() throws Exception
	{
		if (isEmpty())
			throw new Exception("Lista vazia");
		int tamanho = size();
		if (tamanho == 1)
			removeFirst();
		else {
			No<T> penultimo = getNo(tamanho - 2);
			penultimo.proximo = null;
		}
	}

	public void remove(int posicao) throws Exception
	{
		if (isEmpty())
			throw new Exception("Lista vazia");
		int tamanho = size();
		if (posicao < 0 || posicao > tamanho -1)
			throw new Exception("Posicao invalida");
		if (posicao == 0)
			removeFirst();
		else if (posicao == tamanho - 1)
			removeLast();
		else {
			No<T> atual = getNo(posicao);
			No<T> anterior = getNo(posicao - 1);
			anterior.proximo = atual.proximo;			
		}
	}

	public boolean isEmpty()
	{
		return (primeiro == null);
	}

	public T get(int posicao) throws Exception
	{
		No<T> no = getNo(posicao);
		return no.dado;
	}

	public int size()
	{
		int cont = 0;
		
		if (!isEmpty()) {
			No<T> auxiliar = primeiro;
			while (auxiliar != null) {
				cont++;
				auxiliar = auxiliar.proximo;
			}
		}
		return cont;
	}
	
	private No<T> getNo(int posicao) throws Exception
	{
		if (isEmpty())
			throw new Exception("Lista vazia");
		int tamanho = size();
		if (posicao < 0 || posicao > tamanho - 1)
			throw new Exception("Posicao invalida");
		No<T> auxiliar = primeiro;
		int cont = 0;
		while (cont < posicao) {
			cont++;
			auxiliar = auxiliar.proximo;
		}
		return auxiliar;
	}
	
	@Override
	public String toString()
	{
		return this.tipo + "(" + size() + ")"; 
	}
}