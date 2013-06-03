package db;

import metier.Service;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ServiceBDD {
	
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "application";
 
	private static final String table_service="service";
	private static final String COL_numService = "numService";
	private static final int NUM_COL_numService = 0;
	private static final String COL_nomService = "nomService";
	private static final int NUM_COL_nomService = 1;
	
	private SQLiteDatabase bdd;
 
	private  Tables maBaseSQLite;
 
	public ServiceBDD(Context context){
		//On créer la BDD et la table Service
		maBaseSQLite = new Tables(context,NOM_BDD,null,VERSION_BDD);
	}
 
	public  void open(){
		//on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public  void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
 //Insertion d'une clé (mettre en relation avec la bdd exterieure, 
	public  long insertService(Service service){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		values.put(COL_numService,service.getNumService());
		values.put(COL_nomService, service.getNomService());
		
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(table_service, null, values);
	}

	public int updateService(int num_service, Service service){
		/*La mise à jour d'une clé dans la BDD fonctionne plus ou moins comme une insertion
		 il faut préciser quel service on doit mettre à jour grâce à son num*/
		ContentValues values = new ContentValues();
		values.put(COL_numService,service.getNumService());
		values.put(COL_nomService, service.getNomService());
		
		return bdd.update(table_service, values,COL_numService+ " = " + num_service, null);
	}
 //Suppresion d'un service
	public int removeServiceWithNumService(int num_service){
		
		return bdd.delete(table_service,COL_numService + " = " +num_service, null);
	}
 
	public Service getServiceWithNumService(int num_service){
		//Récupère dans un Cursor les valeur correspondant à une clé contenu dans la BDD 
		Cursor c = bdd.query(table_service, new String[] {COL_numService, COL_nomService}, COL_numService + " LIKE \"" + num_service +"\"", null, null, null, null);
		return cursorToService(c);
	}
 
	//Cette méthode permet de convertir un cursor en un Utilisateur
	private Service cursorToService(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;
 
		//Sinon on se place sur le premier élément
		c.moveToFirst();
		Service service = new Service(null, null);
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		service.setNumService(c.getInt(NUM_COL_numService));
		service.setNomService(c.getString(NUM_COL_nomService));
		
		//cle.setUtilisateur(c.getString(NUM_COL_numUtilisateur));
		//cle.setService(c.getString(NUM_COL_numService));
		
		//On ferme le cursor
		c.close();
 
		//On retourne l'utilisateur
		return service;
	}
}
