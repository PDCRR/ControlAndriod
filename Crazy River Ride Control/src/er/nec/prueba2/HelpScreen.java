package er.nec.prueba2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
/**
 * clase para implementar las ayudas al usuario en cuanto
 * al uso de la aplicacion
 * @author Ellioth
 *
 */
public class HelpScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_help_screen);
	}
	
}
