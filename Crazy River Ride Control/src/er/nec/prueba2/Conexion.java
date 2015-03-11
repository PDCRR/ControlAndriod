package er.nec.prueba2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * clase para realizar la conexion con el la palla para el juego
 * @author Ellioth
 *
 */
public class Conexion extends Activity implements constantes{

	private String IP;
	private int port;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_conexion);
	}
	
	/**
	 * metodo para saber que hacer cuando se presiona un boton en la pantalla
	 * @param v
	 */
	public void onClick(View v){
		if(v.getId()==R.id.btnConToSe){
			try{
				extract();
				new Thread(cliente).start();
				if(cliente.ifConnetion()){
					Toast.makeText(getApplicationContext(), "Conexion exitosa", Toast.LENGTH_SHORT).show();
					}
			}catch(Exception e){
				Toast.makeText(getApplicationContext(), "Conexion fallida", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			
		}
		if(v.getId()==R.id.btnPlay){
			if(cliente.ifConnetion()){
			Intent juego = new Intent(this, Juego.class);
			startActivity(juego);
			overridePendingTransition(R.animator.transtion1, R.animator.transition2);
			finish();
			}
		}
	}
	
	/**
	 * metodo para extraer los datos de los editText
	 */
	private void extract(){
		EditText ip= (EditText) findViewById(R.id.txtIP);
		EditText port= (EditText) findViewById(R.id.txtPort);
		this.IP= ip.getText().toString();
		this.port= Integer.parseInt(port.getText().toString());
		cliente.setIP(this.IP);
		cliente.setPort(this.port);
	}
}
