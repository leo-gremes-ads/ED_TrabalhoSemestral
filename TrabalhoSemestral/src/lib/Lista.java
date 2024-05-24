package lib;

public class Lista<T>
{
	private No<T> primeiro;
	
	public Lista()
	{
		primeiro = null;		
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
			throw new Exception("Posição inválida");
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
	
	//DEVE IR NO CONTROLLER
	public void consultaLista() throws Exception
	{
		int tamanho = size();
		if (tamanho == 0)
			System.out.println("Lista Vazia");
		else if (tamanho == 1)
			System.out.println(primeiro.dado.toString());
		else {
			for (int i = 0; i < tamanho; i++) {
				T atual = get(0);
				System.out.println(atual.toString());
				removeFirst();
				addLast(atual);				
			}
		}
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
}