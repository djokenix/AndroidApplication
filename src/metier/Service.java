package metier;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe contenant les méthodes services sans aucune requête SQL
 * @author Fab
 *
 */
/*test*/
public class Service 
{
	/*
	 * Attribut de type Root pour avoir un référencement de la classe
	 * qui contient les requêtes SQL
	 * le numService est initialisé à -1 (pas de clé pour éviter
	 * les conflits entre la BDD et les classes)
	 * Liste des clés sous forme d'arrayList pour pouvoir
	 * en attribuer plusieurs à un unique service (relation 1 à plusieurs)
	 */
	
	private Root root;
	private int numService = RootMapper.noKey;
	private String nomService;
	private ArrayList<Cle>cles;
	 
	/**
	 * Constructeur de la classe service avec attribution d'une clé (visibilité package)
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
	 * Constructeur surchargé de la classe service, creation d'un service (clé attribuée
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
	 * Permet de ne pas changer l'ID si le service en possède déjà 1 dans la BDD
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
	 * Récupérer le nombre de clé d'un service
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
	 * Récupérer une clé d'un service en fonction de son index
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
	 * Méthode booleenne pour savoir si un service possède ou pas des clés
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
	 * Charger toutes les clés d'un service
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
	 * Ajouter une clé à un service
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
	 * Supprimer une clé d'un service
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

