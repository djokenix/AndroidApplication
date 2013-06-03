package intranet.M2L;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MonCompte extends Activity {

	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moncompte);
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



