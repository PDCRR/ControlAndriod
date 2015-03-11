package er.nec.prueba2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
/**
 * clase para pantalla principal
 * @author Ellioth
 *
 */
public class MainActivity extends Activity {

	private Intent Screen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	}
	/**
	 * metodo tipo listener para los botones cuando sean apretados
	 * @param v
	 */
	
	public void onClick(View v){
		if(v.getId()==R.id.btnLinkScreen){
			Screen = new Intent(this, Conexion.class );
			startActivity(Screen);
			overridePendingTransition(R.animator.transtion1, R.animator.transition2);
		}
		else if(v.getId()== R.id.btnHelp){
			Screen = new Intent(this, HelpScreen.class);
			startActivity(Screen);
			overridePendingTransition(R.animator.transtion1, R.animator.transition2);
		}
	}
	
	/**
	 * metodo para indicar que hacer cuando se aprete el boton <back>
	 */
	@Override
	public void onBackPressed(){
		finish();
	}
}
