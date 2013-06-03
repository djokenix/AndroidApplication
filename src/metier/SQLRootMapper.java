package metier;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


import db.Connect;

/**
 * Classe contentant tout le SQL
 * @author Fab
 *
 */

public class SQLRootMapper implements RootMapper
{
	/*
	 * Objet de type root pour l'appel des methodes
	 * Objet de type connect pour la connexion
	 */
	private Root root;
	Connect c;
	
	/*
	 * supprimer les tables
	 */
	private static final String[] RESET_SCRIPT = new String[] {
		"drop table utilisateur ", "drop table service ", "drop table cle "
		
	};
	
	/*
	 * initialiser le script de creation de table
	 */
	private static final String[] INIT_SCRIPT = new String[] {
		
		"create table utilisateur" +
		"(numUtilisateur integer primary key auto_increment,\n" +
		"login varchar(50) not null,\n" +
		"mdp1 varchar(50) not null,\n" +
		"telephone varchar(10) not null,\n" +
		"ville varchar(50) not null,\n" +
		"cp varchar(5) not null,\n" +
		"adr1 varchar(50) not null,\n" +
		"adr2 varchar(50) not null,\n" +
		"estAdmin boolean not null\n)",
		
		 
		"create table cle" +
		"(numCle integer primary key auto_increment,\n" +
		"numeroCle varchar(64) not null,\n" +
		"nomCle varchar(64) not null,\n" + 
		"numUtilisateur int not null,\n" +
		"numService int not null\n)",
		
		 
		"create table service" +
		"(numService integer primary key auto_increment,\n" +
		"nomService varchar(50) not null)"};
	
	/**
	 * Constructeur de la classe SQLRootMapper
	 * choisir le root
	 * @param root
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws DataAccessException
	 */
	SQLRootMapper(Root root) throws ClassNotFoundException, IOException, DataAccessException
	{
		this.root = root;
		c = new Connect();
	}
	
	/**
	 * Surcharge
	 * choisir la connexion et le root
	 * @param root
	 * @param c
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	SQLRootMapper(Root root, Connect c) throws ClassNotFoundException, IOException
	{
		this.root = root;
		this.c = c;
	}
	
	/**
	 * Insertion d'un utilisateur  si pas de cle sinon mise a jour
	 */
	@Override
	public void save(Utilisateur utilisateur) throws DataAccessException 
	{
		try
		{
			if (utilisateur.getNumUtilisateur() == noKey)
			{
				int id;
				id = c.sqlInsert("INSERT INTO utilisateur (login, mdp1, telephone, ville, cp, adr1, adr2, estAdmin)" +
						"VALUES('" + utilisateur.getLogin() + "', '" + utilisateur.getMdp1() + "', '" + utilisateur.getTelephone() 
						+ "', '" + utilisateur.getVille() + "', '" + utilisateur.getCp()
						+ "', '" + utilisateur.getAdr1() + "', '" + utilisateur.getAdr2() + "', '" + (utilisateur.getEstAdmin() ? 1 : 0) + "')");	
				utilisateur.setNumUtilisateur(id);
			}
			else
			{
				c.executeUpdate("UPDATE utilisateur SET " +
						"login = '" + utilisateur.getLogin() +
						"', mdp1  = '" + utilisateur.getMdp1() +
						"', telephone  = '" + utilisateur.getTelephone() +
						"', ville  = '" + utilisateur.getVille() +
						"', cp  = '" + utilisateur.getCp() +
						"', adr1  = '" + utilisateur.getAdr1() +
						"', adr2  = '" + utilisateur.getAdr2() +
						"', estAdmin  = '" + utilisateur.getEstAdmin() + "' " +
						"where numUtilisateur = " + utilisateur.getNumUtilisateur());	
			}		
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
		
	}

	/**
	 * insertion d'un service si pas de cle sinon mise a jour
	 */
	@Override
	public void save(Service service) throws DataAccessException
	{
		try
		{
			if (service.getNumService() == noKey)
			{
				int id = c.sqlInsert("INSERT INTO service (nomService)"
						+ " VALUES('" + service.getNomService() + "')");
				service.setNumService(id);
			}
			
			else
			{
				c.executeUpdate( "UPDATE service SET nomService " +
						" = '" + service.getNomService() + " WHERE numService = '" + service.getNumService() + "'");
			}
		}
		
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
		
	}

	/**
	 * insertion de cle si pas de num sinon mise a jour
	 */
	@Override
	public void save(Cle cle) throws DataAccessException
	{
		try
		{
			if (cle.getNumCle() == noKey)
			{
				int id = c.sqlInsert("INSERT INTO cle (numeroCle, nomCle, numUtilisateur, numService)"
						+ " VALUES('" + cle.getNumeroCle() + "', '" + cle.getNomCle() + "', " + cle.getNumUtilisateur()
						+ ", " + cle.getNumService() + ")");
				cle.setNumCle(id);
			}
			
			else
			{
				c.executeUpdate("UPDATE cle SET nomCle = '" 
						+ cle.getNomCle() + 
						"', numeroCle  = '" + cle.getNumeroCle() + "' " +
						"where numCle = " + cle.getNumCle()); 
			}
		}
		
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
		
		
	}

	/**
	 * supprimer un utilisateur
	 */
	@Override
	public void delete(Utilisateur utilisateur) throws DataAccessException
	{
		try
		{
			while(utilisateur.getNbCle() != 0)
			{
				Cle cle = utilisateur.getCle(0);
				delete(cle);
			}
				c.executeUpdate("DELETE FROM utilisateur WHERE numUtilisateur = " 
						+ utilisateur.getNumUtilisateur());
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
		
		
	}

	/**
	 * supprimer un service
	 */
	@Override
	public void delete(Service service) throws DataAccessException 
	{
		try
		{
			while(service.getNbCle() != 0)
			{
				Cle cle = service.getCle(0);
				delete(cle);
			}
				c.executeUpdate("DELETE FROM service WHERE numService" + 
						"= '" + service.getNomService() + "'");
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
	}

	/**
	 * supprimer une cle
	 */
	@Override
	public void delete(Cle cle) throws DataAccessException
	{
		try
		{
			c.executeUpdate("DELETE FROM cle WHERE numCle = " 
					+ cle.getNumCle());
			cle.setUtilisateur(null);
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
		
		
	}
/*
	@Override
	public void loadCle(Utilisateur utilisateur, Service service) throws DataAccessException 
	{
		try
		{
			ResultSet rs = c.select("select * " +
					"from cle " +
					"where numUtilisateur = " + utilisateur.getNumUtilisateur() + 
					"and numService = " + service.getNumService() + 
					"order by numUtilisateur");

				new Cle(
						rs.getInt("numCle"), 
						rs.getString("nomCle"),
						rs.getString("numeroCle"),
						utilisateur,
						service);
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
		
		
	}
*/
	/**
	 * charger un service en fonction de son num
	 */
	@Override
	public Service loadService(int numService) throws DataAccessException 
	{
		try
		{
			ResultSet rs = c.select("select * from service where numService = " + numService);
			
			if (rs.next())
				return new Service(
					root,
					rs.getInt("numService"), 
					rs.getString("nomService"));
			else
				return null;
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
			
	}

	/**
	 * charger un utilisateur en fonction de son num
	 */
	@Override
	public Utilisateur loadUtilisateur(int numUtilisateur) throws DataAccessException 
	{
		try
		{
			ResultSet rs = c.select("select * from utilisateur where numUtilisateur = " + numUtilisateur);
			
			if (rs.next())
				return new Utilisateur(
					root,
					rs.getInt("numUtilisateur"), 
					rs.getString("login"), 
					rs.getString("mdp1"),
					rs.getString("telephone"),
					rs.getString("adr1"),
					rs.getString("adr2"),
					rs.getString("cp"),
					rs.getString("ville"),
					rs.getBoolean("estAdmin"));
			else
				return null;
		}
			catch (SQLException e)
			{
				throw new DataAccessException(e);
			}

	}

	/**
	 * charger les cles d'un utilisateur
	 */
	@Override
	public Collection<Cle>loadCle(Utilisateur utilisateur) throws DataAccessException 
	{
		ArrayList<Cle> cles = new ArrayList<Cle>();
		try
		{
			ResultSet rs = c.select("select * " +
					"from cle " +
					"where numUtilisateur = " + utilisateur.getNumUtilisateur() +  
					" order by numUtilisateur");
			while(rs.next())
			{
				cles.add(new Cle(
						rs.getInt("numCle"), 
						rs.getString("nomCle"),
						rs.getString("numeroCle"),
						utilisateur,
						loadService(rs.getInt("numService")) 
						));
				
			}
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
		return cles;
		
	}

	/**
	 * charger les cles d'un service
	 */
	@Override
	public Collection<Cle> loadCle(Service service) throws DataAccessException 
	{
		ArrayList<Cle> cles = new ArrayList<Cle>();
		try
		{
			ResultSet rs = c.select("select * " +
					"from cle " +
					"where numService = " + service.getNumService() +  
					" order by numService");
			while(rs.next())
			{
				new Cle(
						rs.getInt("numCle"), 
						rs.getString("nomCle"),
						rs.getString("numeroCle"),
						loadUtilisateur(rs.getInt("numUtilisateur")),
						service
						);
			}
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
		return cles;
	}
 
	/**
	 * charger tout les utilisateurs de la BDD
	 */
	@Override
	public Collection<Utilisateur> loadAllUtilisateurs() throws DataAccessException 
	{
		Collection<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		try
		{
			ResultSet rs = c.select("select * from utilisateur order by numUtilisateur");
			while(rs.next())
			{
				utilisateurs.add(new Utilisateur(
						root,
						rs.getInt("numUtilisateur"), 
						rs.getString("login"), 
						rs.getString("mdp1"),
						rs.getString("telephone"),
						rs.getString("adr1"),
						rs.getString("adr2"),
						rs.getString("cp"),
						rs.getString("ville"),
						rs.getBoolean("estAdmin")));
			}
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}	
		
		return utilisateurs;
		
	}

	/**
	 * charger tout les services de la BDD
	 */
	@Override
	public Collection<Service> loadAllService() throws DataAccessException
	{
		Collection<Service> services = new ArrayList<Service>();
		try
		{
			ResultSet rs = c.select("select * from service order by numService");
			while(rs.next())
			{
				services.add(new Service(
						root,
						rs.getInt("numService"), 
						rs.getString("nomService"))); 
			}
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}	
		
		return services;
		
	}
	/**
	 * fermer la connexion
	 */
	public void close() throws DataAccessException
	{
		try
		{
			c.close();
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
	}

	/**
	 * remise a 0 de la BDD
	 */
	@Override
	public void resetDB() throws DataAccessException 
	{
		try
		{
			c.sqlBatch(RESET_SCRIPT);
			c.sqlBatch(INIT_SCRIPT);
		}
		catch (SQLException e)
		{
			throw new DataAccessException(e);
		}
		
	}

	/**
	 * intialisation de la BDD
	 */
	@Override
	public void initDB() throws DataAccessException 
	{
		try
		{
			c.sqlBatch(INIT_SCRIPT);
		}
		catch (SQLException e)
		{
			System.out.println("La base de donnees est deja  creee.");
		}
	}

}
