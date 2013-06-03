package db;

import metier.Cle;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CleBDD {
	
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "application";
 
	private static final String table_cle="cle";
	private static final String COL_numCle = "numCle";
	private static final int NUM_COL_numCLe = 0;
	private static final String COL_numeroCle = "numeroCLe";
	private static final int NUM_COL_numeroCle = 1;
	private static final String COL_nomCLe = "nomCle";
	private static final int NUM_COL_nomCle = 2;
	private static final String COL_numUtilisateur = "nomUtilisateur";
	private static final int NUM_COL_numUtilisateur = 3;
	private static final String COL_numService = "nomService";
	private static final int NUM_COL_numService = 4;
	
	private SQLiteDatabase bdd;
 
	private  Tables maBaseSQLite;
 
	public CleBDD(Context context){
		//On créer la BDD et la table Utilisateur
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
	public  long insertCle(Cle cle){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_numCle,cle.getNumCle());
		values.put(COL_numeroCle, cle.getNumeroCle());
		values.put(COL_nomCLe, cle.getNomCle());
		values.put(COL_numUtilisateur, cle.getNumUtilisateur());
		values.put(COL_numService, cle.getNumService());
	
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(table_cle, null, values);
	}
 //Mise a jour d'un utilisateur
	public int updateCle(int num_cle, Cle cle){
		/*La mise à jour d'une clé dans la BDD fonctionne plus ou moins comme une insertion
		 il faut préciser quel clé on doit mettre à jour grâce à son numcle*/
		ContentValues values = new ContentValues();
		values.put(COL_numCle,cle.getNumCle());
		values.put(COL_numeroCle, cle.getNumeroCle());
		values.put(COL_nomCLe, cle.getNomCle());
		values.put(COL_numUtilisateur, cle.getNumUtilisateur());
		values.put(COL_numService, cle.getNumService());
		return bdd.update(table_cle, values,COL_numCle+ " = " + num_cle, null);
	}
 //Suppresion d'une clée
	public int removeCleWithNumCle(int num_cle){
		//Suppression d'un utilisateur grace a son num d'utilisateur
		return bdd.delete(table_cle,COL_numCle + " = " +num_cle, null);
	}
 
	public Cle getCleWithNumCle(int num_cle){
		//Récupère dans un Cursor les valeur correspondant à une clé contenu dans la BDD 
		Cursor c = bdd.query(table_cle, new String[] {COL_numCle, COL_numeroCle, COL_nomCLe, COL_numUtilisateur,
				COL_numService}, COL_numCle + " LIKE \"" + num_cle +"\"", null, null, null, null);
		return cursorToCle(c);
	}
 
	//Cette méthode permet de convertir un cursor en un Utilisateur
	private Cle cursorToCle(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;
 
		//Sinon on se place sur le premier élément
		c.moveToFirst();
		//On créé un utilisateur
		Cle cle = new Cle(null, null, null, null);
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		cle.setNumCle(c.getInt(NUM_COL_numCLe));
		cle.setNumeroCle(c.getString(NUM_COL_numeroCle));
		cle.setNomCle(c.getString(NUM_COL_nomCle));
		//cle.setUtilisateur(c.getString(NUM_COL_numUtilisateur));
		//cle.setService(c.getString(NUM_COL_numService));
		
		//On ferme le cursor
		c.close();
 
		//On retourne l'utilisateur
		return cle;
	}
}
