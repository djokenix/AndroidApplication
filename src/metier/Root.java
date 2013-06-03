package metier;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import db.Connect;

/**
 * Classe assurant l'interconnexion entre l'application JAVA et la BDD
 * @author Fab
 *
 */
public class Root 
{
		/*
		 * Connect c : reference type connect pour la connexion
		 * RootMapper mapper : reference type RootMapper pour l'appel methode SQL
		 * Chargement des utilisateurs et services
		 */
		Connect c;
		private RootMapper mapper;
		private boolean allUtilisateursLoaded = false;
		private boolean allServicesLoaded = false;
		
		
		/*
		 * ArrayList d'utilisateur : Liste de tout les utilisateurs
		 * ArrayList de Service : Liste de tout les services
		 * ArrayList de cle : Liste de toutes les cles
		 * HashMap : Charger par num
		 */
		private ArrayList<Utilisateur> utilisateursList = new ArrayList<Utilisateur>();
		private ArrayList<Service> servicesList = new ArrayList<Service>();	
		private ArrayList<Cle> clesList = new ArrayList<Cle>();
		private HashMap<Integer, Utilisateur> utilisateursByNum = new HashMap<Integer, Utilisateur> ();
		private HashMap<Integer, Service> servicesByNum = new HashMap<Integer, Service> ();
		private HashMap<Integer, Cle> clesByNum = new HashMap<Integer, Cle>();
		
		/**
		 * Constructeur de la classe Root permettant d'instancier l'objet connect
		 * instancier le mapper pour methodes SQL
		 * initialise la BDD
		 * @throws SQLException
		 * @throws ClassNotFoundException
		 */
		public Root() throws ClassNotFoundException, DataAccessException, IOException
		{
			c = new Connect();
			mapper = new SQLRootMapper(this);
			mapper.initDB();
		}
		
		/**
		 * Constructeur surcharge pour les diff environnements
		 * initialise la BDD
		 * @param mapper
		 * @throws ClassNotFoundException
		 * @throws DataAccessException
		 * @throws IOException
		 */
		public Root(RootMapper mapper) throws ClassNotFoundException, DataAccessException, IOException 
		{
			this.mapper = mapper;
			mapper.initDB();
		}
		
		/**
		 * reset la database
		 * @throws DataAccessException
		 */
		
		public void resetData() throws DataAccessException
		{
			mapper.resetDB();
		}
		
		/**
		 * initialise la database
		 * @throws DataAccessException
		 */
		void initData() throws DataAccessException
		{
			mapper.initDB();
		}
		
		/**
		 * fermer les connexions
		 * @throws DataAccessException
		 */
		public void close() throws DataAccessException
		{
			mapper.close();
		}
		
		/**
		 * insertion et mise a jour
		 * si pas de num insertion BDD et List
		 * sinon mise à jour
		 * @param utilisateur
		 * @throws DataAccessException
		 */
		public void save(Utilisateur utilisateur) throws DataAccessException
		{
				if (utilisateur.getNumUtilisateur() == mapper.noKey)
				{
					mapper.save(utilisateur);
					utilisateursList.add(utilisateur);
				}
				else
				{
					mapper.save(utilisateur);	
				}			
		}
		
		/**
		 * insertion et mise a jour
		 * si pas de num insertion BDD et list
		 * sinon mise a jour
		 * @param service
		 * @throws DataAccessException
		 */
		public void save(Service service) throws DataAccessException
		{
			
				if (service.getNumService() == mapper.noKey)
				{
					mapper.save(service);
					servicesList.add(service);
				}
				
				else
				{
					mapper.save(service);
				}
				
		}
		/**
		 * insertion et mise a jour
		 * si pas de num insertion BDD avec utilisateur et service et List
		 * sinon mise a jour
		 * @param cle
		 * @throws DataAccessException
		 */
		public void save(Cle cle) throws DataAccessException
		{
			
				if (cle.getNumCle() == mapper.noKey)
				{
					mapper.save(cle.getUtilisateur());
					mapper.save(cle.getService());
					mapper.save(cle);
					clesList.add(cle);
					
				}
				
				else
				{
					mapper.save(cle);
				}	
		}	
		
		/**
		 * Methode permettant de supprimer un utilisateur
		 * On supprime d'abord toutes les cles qu'il possede jusqu'à 0
		 * Une fois fait on supprime l'utilisateur de la BDD et de l'arraylist
		 * @param utilisateur
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		public void delete(Utilisateur utilisateur) throws DataAccessException
		{
			while(utilisateur.getNbCle() != 0)
			{
				Cle cle = utilisateur.getCle(0);
				delete(cle);
			}
				mapper.delete(utilisateur);
				utilisateursList.remove(utilisateur);
		}
		
		/**
		 * Methode permettant de supprimer une cle
		 * On declare l'utilisateur de cette cle à null pour eviter tout conflit
		 * Suppression de la cle
		 * @param cle
		 * @throws DataAccessException 
		 */
		public void delete(Cle cle) throws DataAccessException
		{
				cle.setUtilisateur(null);
				cle.setService(null);
				mapper.delete(cle);
		}
		
		/**
		 * Methode permettant de supprimer un service
		 * On supprime d'abord toutes les cles de ce service
		 * Ensuite on supprime le service de la BDD et de l'arraylist 
		 * @param service
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		public void delete(Service service) throws DataAccessException
		{
			while(service.getNbCle() != 0)
			{
				Cle cle = service.getCle(0);
				delete(cle);
			}
				mapper.delete(service);
				servicesList.remove(service);
		}
		
		/**
		 * Methode permetant de charger toutes les cles par rapport a un Utilisateur
		 * et un service
		 * 
		 * @param utilisateur
		 * @param service
		 * @throws SQLException
		 */
		/*
		void loadCle(Utilisateur utilisateur, Service service) throws SQLException
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
			
		}*/
		/**
		 * Permet de charger un service
		 * Recup du service dans HashMap
		 * Si n'existe pas alors charger service et ajouter dans list et hashmap
		 * @param numService
		 * @return
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		
		public Service loadService(int numService) throws DataAccessException 
		{
			Service service = servicesByNum.get(numService); 
			if (service == null)
			{
				service = mapper.loadService(numService);
					servicesList.add(service);
					servicesByNum.put(numService, service);
			}
			return service;
		}

		/**
		 * Permet de charger un utilisateur
		 * Recup du service dans HashMap
		 * Si n'existe pas alors charger service et ajouter dans list et hashmap
		 * @param numUtilisateur
		 * @return
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		public Utilisateur loadUtilisateur(int numUtilisateur) throws DataAccessException
		{
			Utilisateur utilisateur = utilisateursByNum.get(numUtilisateur); 
			if (utilisateur == null)
			{
				utilisateur = mapper.loadUtilisateur(numUtilisateur);
					utilisateursList.add(utilisateur);
					utilisateursByNum.put(numUtilisateur, utilisateur);
			}
			return utilisateur;
		}

		/**
		 * Permet de charger une cle en fonction d'un utilisateur
		 * @param utilisateur
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		void loadCle(Utilisateur utilisateur) throws DataAccessException
		{
			mapper.loadCle(utilisateur);
		}
		
		/**
		 * Permet de charger une cle en fonction d'un service
		 * @param service
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		void loadCle(Service service) throws DataAccessException
		{
			mapper.loadCle(service);
		}
		/**
		 * Methode permettant de charger tout les utilisateurs de la BDD
		 * dans une collection 
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		private void loadAllUtilisateurs() throws DataAccessException
		{
				if (!allUtilisateursLoaded)
				{
					Collection<Utilisateur> utilisateursLoaded = mapper.loadAllUtilisateurs();
					for(Utilisateur utilisateur : utilisateursLoaded)
					{
						int numUtilisateur = utilisateur.getNumUtilisateur();
						if (utilisateursByNum.get(numUtilisateur) == null)
						{
							utilisateursList.add(utilisateur);
							utilisateursByNum.put(numUtilisateur, utilisateur);
						}
					}
					allUtilisateursLoaded = true;
				}
		}
		
		/**
		 * Methode permettant de charger tout les services de la BDD
		 * dans une collection
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		private void loadAllService() throws DataAccessException
		{
			if (!allServicesLoaded)
			{
				Collection<Service> servicesLoaded = mapper.loadAllService();
				for(Service service : servicesLoaded)
				{
					int numService = service.getNumService();
					if (servicesByNum.get(numService) == null)
					{
						servicesList.add(service);
						servicesByNum.put(numService, service);
					}
				}
				allUtilisateursLoaded = true;
			}
		}
		
		/**
		 * Methode permettant de recupérer le nombre d'utilisateur
		 * On charge grace à la methode loadall et on recupere la taille
		 * @return
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		public int getNbUtilisateurs() throws DataAccessException
		{
			loadAllUtilisateurs();
			return utilisateursList.size();		
		}
		
		/**
		 * Methode permettant de recuperer le nombre de service
		 * On charge grace à la methode loadall et on recupere la taille
		 * @return
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		public int getNbServices() throws DataAccessException
		{
			loadAllService();
			return servicesList.size();
		}
		
		/**
		 * Methode permettant de recuperer un utilisateur par rapport a son index
		 * @param index
		 * @return
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		public Utilisateur getUtilisateurByIndex(int index) throws DataAccessException
		{
			loadAllUtilisateurs();
			return utilisateursList.get(index);
		}	
		
		/** 
		 * Methode permettant de recuperer un service par rapport a son index
		 * @param index
		 * @return
		 * @throws SQLException
		 * @throws DataAccessException 
		 */
		public Service getServiceByIndex(int index) throws DataAccessException
		{
			loadAllService();
			return servicesList.get(index);
		}
		 
		/*
		public String CreateUniKey(String nouvelleCle) throws Exception, SQLException
		{
			 String key = UUID.randomUUID().toString();
			 Cle randomKey = new Cle(nouvelleCle, key, loadUtilisateur(-1), loadService(-1));
			 save(randomKey);
			 return key;
		}
			
		
		public boolean userConnexion(String login, String mdp) throws SQLException
		{  
			c.select("SELECT login FROM utilisateur WHERE login = '" + login + "' AND mdp1 = '" + mdp + "' AND estAdmin = 1");
			if (userConnexion(login, mdp))
			{
				return true;
			}
			else
				return false;
		}
		
		
		public void createMDP(Utilisateur utilisateur) throws SQLException
		{
			String mdp = UUID.randomUUID().toString();
			c.executeUpdate("UPDATE utilisateur SET " +
					"', mdp1  = '" + mdp +
					"where numUtilisateur = " + utilisateur.getNumUtilisateur());
			
		}
		*/
		
		
		//TODO CREER UNE METHODE POUR GENERER UNE CLE
		//TODO CREER UNE METHODE POUR ACCEDER AU RESEAU WIFI
		//TODO CREER UNE METHODE POUR MODIFIER LA CLE
		
		
	}

