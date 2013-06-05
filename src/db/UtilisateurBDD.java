package db;


import metier.Utilisateur;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;

public class UtilisateurBDD {
	
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "application";
 
	private static final String table_utilisateur="utilisateur";
	private static final String COL_numUtilisateur = "numUtilisateur";
	private static final int NUM_COL_numUtilisateur =0;
	private static final String COL_login = "login";
	private static final int NUM_COL_login =1;
	private static final String COL_mdp1 = "mdp1";
	private static final int NUM_COL_mdp1 =2;
	private static final String COL_telephone = "telephone";
	private static final int NUM_COL_telephone =3;
	private static final String COL_ville ="ville";
	private static final int NUM_COL_ville =4;
	private static final String COL_cp = "cp";
	private static final int NUM_COL_cp =5;
	private static final String COL_adr1 = "adr1";
	private static final int NUM_COL_adr1 =6;
	private static final String COL_adr2 = "adr2";
	private static final int NUM_COL_adr2 =7;
	private static final String COL_estAdmin="estAdmin";
	private static final int NUM_COL_estAdmin =8;
			
	 
       
	
 
	private SQLiteDatabase bdd;
 
	private  Tables maBaseSQLite;
 
	public UtilisateurBDD(Context context){
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
	
 //Insertion d'un utilisateur (mettre en relation avec la bdd exterieure, on n'ajoute pas d'utilisateur sur l'appli android
	public  long insertUtilisateur(Utilisateur utilisateur){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_numUtilisateur,utilisateur.getNumUtilisateur());
		values.put(COL_login, utilisateur.getLogin());
		values.put(COL_mdp1, utilisateur.getMdp1());
		values.put(COL_telephone, utilisateur.getTelephone());
		values.put(COL_ville, utilisateur.getVille());
		values.put(COL_cp, utilisateur.getCp());
		values.put(COL_adr1, utilisateur.getAdr1());
		values.put(COL_adr2, utilisateur.getAdr2());
		values.put(COL_estAdmin, utilisateur.getEstAdmin());
		
		
		
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(table_utilisateur, null, values);
	}
	
 //Mise a jour d'un utilisateur
	public int updateUtilisateur(int num_utilisateur, Utilisateur utilisateur){
		/*La mise à jour d'un utilisateur dans la BDD fonctionne plus ou moins comme une insertion
		 il faut préciser quel utilisateur on doit mettre à jour grâce à son login*/
		ContentValues values = new ContentValues();
		values.put(COL_numUtilisateur,utilisateur.getNumUtilisateur());
		values.put(COL_login, utilisateur.getLogin());
		values.put(COL_mdp1, utilisateur.getMdp1());
		values.put(COL_telephone, utilisateur.getTelephone());
		values.put(COL_ville, utilisateur.getVille());
		values.put(COL_cp, utilisateur.getCp());
		values.put(COL_adr1, utilisateur.getAdr1());
		values.put(COL_adr2, utilisateur.getAdr2());
		values.put(COL_estAdmin, utilisateur.getEstAdmin());
	
		return bdd.update(table_utilisateur, values,COL_numUtilisateur + " = " + num_utilisateur, null);
	}
 //Suppresion d'un utilisateur
	public int removeUtilisateurWithNumUtilisateur(int num_utilisateur){
		//Suppression d'un utilisateur grace a son num d'utilisateur
		return bdd.delete(table_utilisateur,COL_numUtilisateur + " = " +num_utilisateur, null);
	}
 
	public Utilisateur getUtilisateurWithNumUtilisateur(int num_utilisateur){
		//Récupère dans un Cursor les valeur correspondant à un Utilisateur contenu dans la BDD (ici on sélectionne l'utilisateur grace a son num)
		Cursor c = bdd.query(table_utilisateur, new String[] {COL_numUtilisateur, COL_login, COL_mdp1, COL_telephone,
				COL_ville,COL_cp,COL_adr1,COL_adr2,COL_estAdmin}, COL_numUtilisateur + " LIKE \"" + num_utilisateur +"\"", null, null, null, null);
		return cursorToUtilisateur(c);
	}
	public Utilisateur getUtilisateurWithLogin(String login){
		Cursor c = bdd.query(table_utilisateur, new String[] {COL_numUtilisateur, COL_login, COL_mdp1, COL_telephone,
				COL_ville,COL_cp,COL_adr1,COL_adr2,COL_estAdmin}, COL_login + " LIKE \"" + login +"\"", null, null, null, null);
		return cursorToUtilisateur(c);
	}
   
	//Cette méthode permet de convertir un cursor en un Utilisateur
	private Utilisateur cursorToUtilisateur(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;
 
		//Sinon on se place sur le premier élément
		c.moveToFirst();
		//On créé un utilisateur
		Utilisateur utilisateur = new Utilisateur(null, null, null, null, null, null, null, null,false);
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		utilisateur.setNumUtilisateur(c.getInt(NUM_COL_numUtilisateur));
		utilisateur.setLogin(c.getString(NUM_COL_login));
		utilisateur.setMdp1(c.getString(NUM_COL_mdp1));
		utilisateur.setTelephone(c.getString(NUM_COL_telephone));
		utilisateur.setVille(c.getString(NUM_COL_ville));
		utilisateur.setCp(c.getString(NUM_COL_cp));
		utilisateur.setAdr1(c.getString(NUM_COL_adr1));
		utilisateur.setAdr2(c.getString(NUM_COL_adr2));
		utilisateur.setEstAdmin(c.equals(NUM_COL_estAdmin)); // voire si c'est sa get.Boolean
		
		
		//On ferme le cursor
		c.close();
 
		//On retourne l'utilisateur
		return utilisateur;
	}
	
}
