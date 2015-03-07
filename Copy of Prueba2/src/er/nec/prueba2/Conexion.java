package er.nec.prueba2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Conexion extends Activity implements constantes{

	private String IP;
	private int port;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_conexion);
	}
	
	public void onClick(View v){
		if(v.getId()==R.id.btnConToSe){
			try{
				extract();
				new Thread(cliente).start();
				Toast.makeText(getApplicationContext(), "Conexion exitosa", Toast.LENGTH_SHORT).show();
				Intent juego = new Intent(this, Juego.class);
				startActivity(juego);
				overridePendingTransition(R.animator.transtion1, R.animator.transition2);
			}catch(Exception e){
				Toast.makeText(getApplicationContext(), "Conexion fallida", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			
		}
	}
	
	private void extract(){
		EditText ip= (EditText) findViewById(R.id.txtIP);
		EditText port= (EditText) findViewById(R.id.txtPort);
		this.IP= ip.getText().toString();
		this.port= Integer.parseInt(port.getText().toString());
		cliente.setIP(this.IP);
		cliente.setPort(this.port);
	}
}
