package metier;

/**
 * Exception pour lancer les erreurs d'ecritures ou de lecture dans la BDD.
 *
 */ 
@SuppressWarnings("serial")
public class DataAccessException extends Throwable
{
	private Exception e;
	
	public Exception getException()
	{
		return e;
	}
	
	DataAccessException(Exception e)
	{
		this.e = e;
	}

	@Override
	public void printStackTrace()
	{
		super.printStackTrace();
		e.printStackTrace();
	}
	
	public String toString()
	{
		return "DataAccessException : throw by " + e.toString(); 
	}
}
