package metier;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe contenant les m�thodes services sans aucune requ�te SQL
 * @author Fab
 *
 */
/*test*/
public class Service 
{
	/*
	 * Attribut de type Root pour avoir un r�f�rencement de la classe
	 * qui contient les requ�tes SQL
	 * le numService est initialis� � -1 (pas de cl� pour �viter
	 * les conflits entre la BDD et les classes)
	 * Liste des cl�s sous forme d'arrayList pour pouvoir
	 * en attribuer plusieurs � un unique service (relation 1 � plusieurs)
	 */
	
	private Root root;
	private int numService = RootMapper.noKey;
	private String nomService;
	private ArrayList<Cle>cles;
	 
	/**
	 * Constructeur de la classe service avec attribution d'une cl� (visibilit� package)
	 * @param root
	 * @param numService
	 * @param nomService
	 */
	Service(Root root, int numService, String nomService) 
	{
		this.root = root;
		setNumService(numService);
		setNomService(nomService);
	}
	
	/**
	 * Constructeur surcharg� de la classe service, creation d'un service (cl� attribu�e
	 * par la BDD)
	 * @param root
	 * @param nomService
	 */
	public Service(Root root, String nomService) 
	{
		this(root, RootMapper.noKey, nomService);
	
	}
	public String getNomService() 
	{
		return nomService;
	}
	
	public void setNomService(String nomService) 
	{
		this.nomService = nomService;
	}
	
	
	/**
	 * Permet de ne pas changer l'ID si le service en poss�de d�j� 1 dans la BDD
	 * @param numService
	 */
	public void setNumService(int numService) 
	{
		if (this.numService != RootMapper.noKey)
			throw new RuntimeException("Cannot change DB ID");
		this.numService = numService;
	}
	
	public int getNumService() 
	{
		return numService;
	}
	
	/** 
	 * R�cup�rer le nombre de cl� d'un service
	 * @return
	 * @throws SQLException
	 * @throws DataAccessException 
	 */
	public int getNbCle() throws DataAccessException
	{
		loadAllCles();
		return cles.size();		
	}
	
	/**
	 * R�cup�rer une cl� d'un service en fonction de son index
	 * @param index
	 * @return
	 * @throws SQLException
	 * @throws DataAccessException 
	 */
	public Cle getCle(int index) throws DataAccessException
	{
		loadAllCles();
		return cles.get(index);
	}
	
	/**
	 * M�thode booleenne pour savoir si un service poss�de ou pas des cl�s
	 * @param cle
	 * @return
	 * @throws SQLException
	 * @throws DataAccessException 
	 */
	private boolean possedeCle(Cle cle) throws DataAccessException 
	{
		loadAllCles();
		return cles.contains(cle);
	}
	
	/**
	 * Charger toutes les cl�s d'un service
	 * @throws SQLException
	 * @throws DataAccessException 
	 */
	private void loadAllCles() throws DataAccessException
	{
		if (cles == null)
		{
			cles = new ArrayList<Cle>();
			root.loadCle(this);
		}
	}
	
	/**
	 * Ajouter une cl� � un service
	 * @param cle
	 * @throws SQLException
	 * @throws DataAccessException 
	 */
	public void addKey(Cle cle) throws DataAccessException 
	{
		loadAllCles();
		if(!possedeCle(cle))
		{
			cles.add(cle);
			cle.setService(this);
		}
	}
	
	/**
	 * Supprimer une cl� d'un service
	 * @param cle
	 * @throws SQLException
	 * @throws DataAccessException 
	 */
	public void removeCle(Cle cle) throws DataAccessException 
	{
		loadAllCles();
		if(possedeCle(cle))
		{
			cles.remove(cle);
			cle.setService(null);
		}
	}
}

