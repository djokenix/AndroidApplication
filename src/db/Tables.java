package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Tables extends SQLiteOpenHelper {
	
	private String CreateTableUtilisateur="create table utilisateur" +
			"(numUtilisateur integer primary key autoincrement," +
			"login text not null," +
			"mdp1 text not null," +
			"telephone text not null," +
			"ville text not null," +
			"cp text not null," +
			"adr1 text not null," +
			"adr2 text not null," +
			"estAdmin integer not null)";  //bollean n'existe pas
	
	private String CreateTableClée="create table cle" +
			"(numCle integer primary key autoincrement," +
			"numeroCle text not null," +
			"nomCle test not null," + 
			"numUtilisateur integer not null," +
			"numService integer not null)";
	
	private String CreateTableService="create table service" +
			"(numService integer primary key autoincrement," +
			"nomService text not null)";
			
			 
			
	
	public Tables(Context context, String name,CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// On crée les table a partir des  requêtes écritent précedament
		db.execSQL(CreateTableUtilisateur);
		db.execSQL(CreateTableClée);
		db.execSQL(CreateTableService);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// On Supprime la table et on la recréer pour facilité les changement de version
		db.execSQL("DROP TABLE"+"table utilisateur"+";");
		db.execSQL("DROP TABLE"+"table cle"+";");
		db.execSQL("DROP TABLE"+"table service"+";");
		onCreate(db);
	}
	

}
 


