package metier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Classe contenant les méthodes utilisateurs sans aucune requête SQL
 * @author Fab
 *
 */
/*test*/

public class Utilisateur
{
	/*
	 * Attribut de type Root pour avoir un référencement de la classe
	 * qui contient les requêtes SQL
	 * le numUtilisateur est initialisé à -1 (pas de clé pour éviter
	 * les conflits entre la BDD et les classes)
	 * Liste des clés sous forme d'arrayList pour pouvoir
	 * en attribuer plusieurs à un unique utilisateur (relation 1 à plusieurs)
	 */ 
	private Root root;
	private int numUtilisateur = RootMapper.noKey;
	private String login;
	private String mdp1;
	private String telephone;
	private String adr1;
	private String adr2;
	private String cp;
	private String ville;
	private boolean estAdmin;
	private ArrayList<Cle> cles;

	/**
	 * Constructeur de la classe Utilisateur avec attribution d'une clé (visibilité package)
	 * @param root
	 * @param numUtilisateur
	 * @param login
	 * @param mdp1
	 * @param telephone
	 * @param adr1
	 * @param adr2
	 * @param ville
	 * @param cp
	 * @param estAdmin
	 */
	Utilisateur(Root root, int numUtilisateur, String login, String mdp1, String telephone, String adr1, String adr2, String cp, String ville, boolean estAdmin) 
	{
		this.root = root;
		setNumUtilisateur(numUtilisateur);
		setLogin(login);
		setMdp1(mdp1);
		setTelephone(telephone);
		setAdr1(adr1);
		setAdr2(adr2);
		setCp(cp);
		setVille(ville);
		setEstAdmin(estAdmin);
	}
	
	/**
	 * Constructeur surchargé de la classe utilisateur pour la création d'un utilisateur
	 * primary key attribué par la BDD
	 * @param root
	 * @param login
	 * @param mdp1
	 * @param telephone
	 * @param adr1
	 * @param adr2
	 * @param ville
	 * @param cp
	 * @param estAdmin
	 */
	public Utilisateur(Root root, String login, String mdp1, String telephone, String adr1, String adr2, String cp, String ville, boolean estAdmin) 
	{
		this(root, RootMapper.noKey, login, mdp1, telephone, adr1, adr2, cp, ville, estAdmin);
	}
	
	public int getNumUtilisateur() {
		return numUtilisateur;
	}

	/**
	 * Permet de ne pas changer l'ID si le contact en possède déjà 1 dans la BDD
	 * @param numUtilisateur
	 */
	public void setNumUtilisateur(int numUtilisateur) 
	{
		if (this.numUtilisateur != RootMapper.noKey)
			throw new RuntimeException("Cannot change DB ID");
		this.numUtilisateur = numUtilisateur;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp1() {
		return mdp1;
	}

	public void setMdp1(String mdp1) {
		this.mdp1 = mdp1;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAdr1() {
		return adr1;
	}

	public void setAdr1(String adr1) {
		this.adr1 = adr1;
	}

	public String getAdr2() {
		return adr2;
	}

	public void setAdr2(String adr2) {
		this.adr2 = adr2;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public boolean getEstAdmin() {
		return estAdmin;
	}

	public void setEstAdmin(boolean estAdmin) {
		this.estAdmin = estAdmin;
	}
	
	/**
	 * récupérer le nombre de clé par utilisateur
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
	 * Récuperer une clé particulière en fonction de son index
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
	 * Méthode booleenne permettant de savoir si un utilisateur
	 * possède ou pas des clés
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
	 * Méthode permettant de charger toutes les clés d'un utilisateur
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
	 * Méthode permettant d'ajouter des clés à un utilisateur
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
			cle.setUtilisateur(this);
		}
	}
	
	/**
	 * Méthode permettant de supprimer certaines clés d'un utilisateur
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
			cle.setUtilisateur(null);
		}
	}

}
