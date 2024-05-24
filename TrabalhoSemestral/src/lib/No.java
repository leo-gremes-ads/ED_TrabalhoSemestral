package lib;

public class No<T>
{
	public T dado;
	public No<T> proximo;
	
	@Override
	public String toString()
	{
		return String.valueOf(dado);
	}
}