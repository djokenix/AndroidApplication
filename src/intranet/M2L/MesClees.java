package intranet.M2L;

import metier.Cle;
import metier.Service;
import metier.Utilisateur;
import db.CleBDD;
import db.UtilisateurBDD;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MesClees extends Activity {
	private static final String CreateTableClée =null;
	final String Extra_cle_digicode = "cle_digicode";
	final String Extra_cle_wep = "cle_wep";
	

		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(CreateTableClée);
		}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesclees);
        
	    /*CleBDD cleBDD = new CleBDD(this);
	    
	    //Création d'une clée
        Utilisateur utilisateur = null;
        Service service=null;
		Cle cle = new  Cle(Extra_cle_digicode, "374097",utilisateur , service);
        cleBDD.open();
        cleBDD.insertCle(cle);
        Toast.makeText(MesClees.this,cle.getNumCle(),Toast.LENGTH_SHORT).show();
        cleBDD.close();*/
        
        
        Intent intent = getIntent();
        Resources res = getResources();
        String chaine_digicode = res.getString(R.string.cle_digicode,(intent.getStringExtra(Extra_cle_digicode))); //affiche la valeur que l'utilisateur a saisie
        TextView vue = (TextView)findViewById(R.id.digicode);
        //String chaine_wep = res.getString(R.string.cle_wep,(intent.getStringExtra(Extra_cle_wep))); //affiche la valeur que l'utilisateur a saisie
        //TextView vue1 = (TextView)findViewById(R.id.wep);
        vue.setText(chaine_digicode);
        //vue1.setText(chaine_wep);
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



