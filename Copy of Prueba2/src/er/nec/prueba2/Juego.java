package er.nec.prueba2;

import java.util.List;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class Juego extends Activity implements OnItemSelectedListener, SensorEventListener, constantes{

	private String MensajeOUT="",MensajeIN ="";
	private String frSpaces="N", ScdSpaces="N", shoot="f";
	private int bala=1;
	private double ActX, ActZ, ActY;
    private MediaPlayer sonido;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_juego1);
		Spinner spinner = (Spinner) findViewById(R.id.tipeShoot);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.shoots, android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}
	
	public void onClick(View v){
		if(v.getId()==R.id.btnShoot){
			shoot="t";
			cliente.sendMsj(builderStr());
			shoot="f";
		}
	}
	
	@Override
	public void onBackPressed(){
		cliente.salir();
		finish();
	}
	
 	private String builderStr(){
		sb.append(frSpaces);
		sb.append(",");
		sb.append(ScdSpaces);
		sb.append(",");
		sb.append(shoot);
		sb.append(",");
		sb.append(bala);
		MensajeOUT=sb.toString();
		return MensajeOUT;
	}
	
	public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
		TextView weaponChoose= (TextView) view;
		if((weaponChoose.getText().toString()).equals("Pro"))
			bala=2;
		else if((weaponChoose.getText().toString()).equals("Dispersion"))
			bala=3;
		else if((weaponChoose.getText().toString()).equals("Mayhem"))
			bala=4;
    }

    public void onNothingSelected(AdapterView<?> parent){ 
    
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    	synchronized (this) {
    		ActX = event.values[0];
		    ActY = event.values[1];
		    ActZ = event.values[2];
		    if((ActX - ConsX)>0)
		    	ScdSpaces="i";
		    else if ((ActX - ConsX)<0)
		    	ScdSpaces="d";
		    if((ActZ - ConsZ)<0)
		    	frSpaces="a";
		    else if((ActZ - ConsZ)>0)
		    	frSpaces="A";
		    //cliente.sendMsj(builderStr());
		    System.out.println(frSpaces+ ", "+ ScdSpaces+", "+shoot+", "+bala);
		    try {
				Thread.sleep(dormir);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

	@Override
    protected void onResume(){
        super.onResume();
        SensorManager sm= (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors= sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensors.size()>0){
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
            
        }
    }
	
    @Override
    protected void onStop() {

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE); 
        sm.unregisterListener(this);
        super.onStop();
    }
    

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
