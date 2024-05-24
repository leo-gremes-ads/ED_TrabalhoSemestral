package lib;

public class Fila<T>
{
	private No<T> inicio;
	private No<T> fim;
	
	public Fila()
	{
		super();
		this.inicio = null;
		this.fim = null;
	}
	
	public boolean isEmpty()
	{
		return (inicio == null && fim == null);
	}
	
	public void insert(T valor)
	{
		No<T> elemento = new No<>();
		
		elemento.dado = valor;
		elemento.proximo = null;
		if (isEmpty()) {
			inicio = elemento;
			fim = inicio;
		} else {
			fim.proximo = elemento;
			fim = elemento;
		}
	}
	
	public T remove() throws Exception
	{
		if (isEmpty())
			throw new Exception("Fila Vazia!");
		T valor = inicio.dado;
		if (inicio == fim && inicio != null) {
			inicio = null;
			fim = null;
		} else
			inicio = inicio.proximo;
		return valor;
	}
	
	public void list() throws Exception
	{
		if (isEmpty())
			throw new Exception("Fila Vazia!");
		No<T> aux = inicio;
		while (aux != null) {
			System.out.println(aux.toString());
			System.out.println(" -> ");
			aux = aux.proximo;
		}
		System.out.println("Null");
	}
	
	public int size()
	{
		int i = 0;
		
		if (!isEmpty() ) {
			No<T> aux = inicio;
			while (aux != null) {
				i++;
				aux = aux.proximo;
			}
		}
		return i;
		
	}
	
	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		No<T> aux = inicio;
		while (aux != null) {
			buffer.append(aux.toString());
			buffer.append(" -> ");
			aux = aux.proximo;
		}
		buffer.append("Null");
		return buffer.toString();
	}
	
}