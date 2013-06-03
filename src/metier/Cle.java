package metier;
/**
* Classe contenant les méthodes services sans aucune requête SQL
* @author Fab
*
*/
/*test*/
public class Cle
{
	/*
	 * Attribut de type utilisateur et service pour le référencement des classes
	 * numCle à noKey (-1) pour éviter les conflits avec la BDD
	 */
	private int numCle = RootMapper.noKey;
	private String nomCle = null;
	private Utilisateur utilisateur;
	private Service service;
	private String numeroCle = null;
	
	/**
	 * Constructeur de la classe Cle avec l'attribution à un service et un utilisateur
	 * @param numCle
	 * @param nomCle
	 * @param numeroCle
	 * @param utilisateur
	 * @param service
	 */ 
	Cle(int numCle, String nomCle, String numeroCle, Utilisateur utilisateur, Service service) 
	{
		setNumCle(numCle);
		setNomCle(nomCle);
		setNumeroCle(numeroCle);
		setUtilisateur(utilisateur);
		setService(service);
	}
	
	/**
	 * Constructeur surchargé de la classe Clé avec attribution d'une primary key par
	 * la BDD
	 * @param nomCle
	 * @param numeroCle
	 * @param utilisateur
	 * @param service
	 */
	public Cle(String nomCle, String numeroCle, Utilisateur utilisateur, Service service) 
	{
		this(RootMapper.noKey, nomCle, numeroCle, utilisateur, service);
	}

	public String getNomCle() 
	{
		return nomCle;
	}
	
	public void setNomCle(String nomCle) 
	{
		this.nomCle = nomCle;
	}
	
	public int getNumUtilisateur()
	{
		return utilisateur.getNumUtilisateur();
	}
	
	public Utilisateur getUtilisateur()
	{
		return utilisateur;
	}
	
	public void setUtilisateur(Utilisateur utilisateur)
	{
		this.utilisateur = utilisateur;
	}
	
	public int getNumService()
	{
		return service.getNumService();
	}
	public Service getService()
	{
		return service;
	}
	
	public void setService(Service service)
	{
		this.service = service;
	}
	
	public String getNumeroCle() 
	{
		return numeroCle;
	}
	
	public void setNumeroCle(String numeroCle) 
	{
		this.numeroCle = numeroCle;
	}
	
	/**
	 * Permet de ne pas changer l'ID si le contact en possède déjà 1 dans la BDD
	 * @param numCle
	 */
	public void setNumCle(int numCle) 
	{
		if (this.numCle != RootMapper.noKey)
			throw new RuntimeException("Cannot change DB ID");
		this.numCle = numCle;
	}
	
	public int getNumCle() 
	{
		return numCle;
	}
	
}


