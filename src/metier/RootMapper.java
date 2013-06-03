package metier;

/**
 * Passerelle entre les requetes SQL et les ROOT pour les diff environnements
 */

import java.util.Collection;
interface RootMapper
{
	final static int noKey = -1;
	
	public void save(Utilisateur utilisateur) throws DataAccessException;
	public void save(Service service) throws DataAccessException;
	public void save(Cle cle) throws DataAccessException;
	public void delete(Utilisateur utilisateur) throws DataAccessException;
	public void delete(Service service) throws DataAccessException;
	public void delete(Cle cle) throws DataAccessException;
	//public void loadCle(Utilisateur utilisateur, Service service) throws DataAccessException;
	public Service loadService(int numService) throws DataAccessException;
	public Utilisateur loadUtilisateur(int numUtilisateur) throws DataAccessException;
	public Collection<Cle> loadCle(Utilisateur utilisateur) throws DataAccessException;
	public Collection<Cle> loadCle(Service service) throws DataAccessException;
	public Collection<Utilisateur> loadAllUtilisateurs() throws DataAccessException;
	public Collection<Service> loadAllService() throws DataAccessException;
	public void resetDB() throws DataAccessException;
	public void initDB()  throws DataAccessException;
	public void close() throws DataAccessException;

} 
