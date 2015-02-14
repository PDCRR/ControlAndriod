package com.workteam.android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends Activity implements SensorEventListener{
    
    private long AntActualizacion=0, AntMovimiento=0;
    private float AntX=0, AntY=0, AntZ=0;
    private float ActX=0, ActY=0, ActZ=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this){
            long tiempoActual = event.timestamp;
            ActX = event.values[0];
            ActY = event.values[1];
            ActZ = event.values[2];
            
            if (AntX == 0 && AntY == 0 && AntZ == 0) {
                AntActualizacion = tiempoActual;
                AntMovimiento = tiempoActual;
                AntX = ActX;
                AntY = ActY;
                AntZ = ActZ;
            }
            long DifTiempo = tiempoActual - AntActualizacion;
            if (DifTiempo > 0) {
                float movimiento = Math.abs((ActX + ActY + ActZ) - (AntX - AntY - AntZ)) / DifTiempo;
                int limite = 1500;
                float MinMovimiento = 1E-6f;
                if (movimiento > MinMovimiento) {
                    if (tiempoActual- AntMovimiento >= limite) {
                        Toast.makeText(getApplicationContext(), "Hay movimiento de " + movimiento, Toast.LENGTH_SHORT).show();
                    }
                    AntMovimiento = tiempoActual ;
                }
                AntX = ActX;
                AntY = ActY;
                AntZ = ActZ;
                AntActualizacion = tiempoActual;
            }
            ((TextView) findViewById(R.id.txtPosX)).setText("Acelerómetro X: " + ActX);
            ((TextView) findViewById(R.id.txtPosY)).setText("Acelerómetro Y: " + ActY);
            ((TextView) findViewById(R.id.txtPosZ)).setText("Acelerómetro Z: " + ActZ);
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
    
    public void onAcActacyChanged(Sensor sensor, int acActacy) {}

    public void onAccuracyChanged(Sensor sensor, int i) {}
}
