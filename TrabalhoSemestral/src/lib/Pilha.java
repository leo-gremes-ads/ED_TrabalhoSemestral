package lib;

public class Pilha<T>
{
	private No<T> topo;
	
	public Pilha()
	{
		topo = null;
	}
	
	public boolean isEmpty()
	{
		return (topo == null);
	}
	
	public void push(T valor)
	{
		No<T> elemento = new No<>();
		elemento.dado = valor;
		elemento.proximo = topo;
		topo = elemento;
	}
	
	public T pop() throws Exception
	{
		if (isEmpty())
			throw new Exception("Pilha vazia");
		T valor = topo.dado;
		topo = topo.proximo;
		return valor;
	}
	
	public T top() throws Exception
	{
		if (isEmpty())
			throw new Exception("Pilha vazia");
		T valor = topo.dado;
		return valor;
	}
	
	public int size()
	{
		int cont = 0;
		No<T> aux = topo;		
		while (aux != null) {
			cont++;
			aux = aux.proximo; 
		}
		return cont;
	}
}