package intranet.M2L;


import db.UtilisateurBDD;
import metier.Root;
import metier.Utilisateur;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Identification extends Activity {
	private static final String CreateTableUtilisateur = null;
	final String Extra_Login = "user_login";
	final String Extra_Password = "user_password";
	final int Nombre_user=0;
	
	//BDD	
	public void onCreate(SQLiteDatabase db) {
		//on créé la table à partir de la requête écrite dans la variable CreateTableUtilisateur 
		db.execSQL(CreateTableUtilisateur);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_identification);
	//BDD
		 //Création d'une instance de ma classe UtilisateurBDD
	    UtilisateurBDD utilisateurBDD = new UtilisateurBDD(this);
	    
	    //Création d'un Utilisateur
        Utilisateur utilisateur = new  Utilisateur(null,"Jonathan","mdp","0672232612","adrs1","adrs2","91240","ville", false);
        Utilisateur Fabien=new Utilisateur(null,"Fabien","mdp1","telephone","adresseF","","codepostaleF","villeF",true);
        //On ouvre la base de données pour écrire dedans
       
        Log.v("","");
        utilisateurBDD.open();
        //On insère l'utilisateur que l'on vient de créer
        utilisateurBDD.insertUtilisateur(utilisateur);
        utilisateurBDD.insertUtilisateur(Fabien);
        
        
        //Utilisateur utilisateurFromBdd = utilisateurBDD.getUtilisateurWithNumUtilisateur(utilisateur.getNumUtilisateur());
        //Toast.makeText(this, utilisateurFromBdd.toString(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, utilisateur.toString(), Toast.LENGTH_LONG).show();
        //Toast.makeText(Identification.this,Fabien.getLogin(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(Identification.this,utilisateur.getMdp1(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(Identification.this,utilisateur.getTelephone(),Toast.LENGTH_SHORT).show();
        Toast.makeText(Identification.this,utilisateur.getCp(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(Identification.this,utilisateur.getVille(),Toast.LENGTH_SHORT).show();
        utilisateurBDD.close();
	      
        
	    final EditText login = (EditText) findViewById(R.id.login);
	    final EditText pass = (EditText) findViewById(R.id.password);
	    
	    final Button connexion = (Button) findViewById(R.id.connexion);
	    connexion.setOnClickListener(new OnClickListener() {
		
	    	
		@Override
		public void onClick(View v) {
			//Teste si les champs sont nuls ou pas
			final String loginTxt = login.getText().toString();
			final String passTxt = pass.getText().toString();

			if (loginTxt.equals("")) 
				{Toast.makeText(Identification.this,
					"Veuillez saisir votre login",
					Toast.LENGTH_SHORT).show();
				return;
				}
			if (passTxt.equals(""))
				{Toast.makeText(Identification.this,
					"Veuillez saisir votre mot de passe",
					Toast.LENGTH_SHORT).show();
				return;
				}
			
						
		Intent intent = new Intent(Identification.this,Accueil.class);
		intent.putExtra(Extra_Login, login.getText().toString());
		intent.putExtra(Extra_Password, pass.getText().toString());
		startActivity(intent);
		
		}
	    });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.presentation, menu);
		return true;
	}
		
       @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    
		    switch (item.getItemId()) {
			    case R.id.action_settings_parametre:
			    	Toast.makeText(this, "Parametre", Toast.LENGTH_SHORT).show();
			        return true;
			    case R.id.action_settings_propos:
			    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://djokenix.byethost17.com/ppeAndroid/Apropos.html"));
			    	startActivity(intent);
			        return true;
			    default:
			        return super.onOptionsItemSelected(item);
		    }
		}
	
}

