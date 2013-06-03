package intranet.M2L;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Presentation extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_presentation);
		
		final Button entrerbouton = (Button) findViewById(R.id.entrer);
		  entrerbouton.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				Intent intent = new Intent(Presentation.this, Identification.class);
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
