package intranet.M2L;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Accueil extends Activity {

	final String Extra_Login = "user_login";
	final String Extra_Password = "user_password";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        
        final Button service1bouton = (Button) findViewById(R.id.service1);
        final Button service3bouton =(Button) findViewById(R.id.service3);
		 
        service1bouton.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				Intent intent = new Intent(Accueil.this, MonCompte.class);
				startActivity(intent);
				}
			});
        
        service3bouton.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View c) {
				Intent intent2 = new Intent(Accueil.this, MesClees.class);
				startActivity(intent2);
				}
			});	
        
        Intent intent = getIntent();
        Resources res = getResources();
        String chaine = res.getString(R.string.bienvenue,(intent.getStringExtra(Extra_Login))); //affiche la valeur que l'utilisateur a saisie
        TextView vue = (TextView)findViewById(R.id.bienvenue);
        vue.setText(chaine);
        
       
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



