package Er.net.connectserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * aplicacion para crear un cliente thread para conectarse al servidor 
 * @author Ellioth
 *
 */
public class MainActivity extends Activity {
	
	private Socket socket;
    //private DataInputStream InData; //nos crea el decodificador de mensajes recibidos por el server
    private DataOutputStream OutData; //nos crea el codificador de mensajes enviados al server
    /**
     * variables para que almacenen
     * los mensajes que salen
     * los mensajes que se reciben
     * el puerto con que va a operar
     * la IP del servidor
     */
	private String mensajeOut=""; 
	//private String mensajeIn=""; 
	private int port=0;
	private String IP="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	}	
	/**
	 * nos extrae de los EditText las frases que tengamos 
	 * y las almacenas en las variables de IP y puerto
	 */
	public void extract(){
		EditText textoIP = (EditText) findViewById(R.id.InIP);
		EditText textoPort = (EditText) findViewById(R.id.InPort);
		IP= textoIP.getText().toString();
		port= Integer.parseInt(textoPort.getText().toString());
	}
	/**
	 *capta los click en los id especificados
	 *el cuerpo del metodo, ejemplo: 
	 *btnconnect, detecta si este boton se toco
	 * @param v
	 */
	
	public void onClick(View v){
		if(v.getId()== R.id.BtnConnect){
			try{
				extract();
				new Thread(new ConnectToServer()).start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		/**
		 * esta parte del codigo va a extraer el mensaje y lo manda 
		 * a codificar y lo envia al socket para que se remita al server
		 *
		 */
		else if(v.getId()==R.id.SndMsj){
			try {
				mensajeOut= ((EditText)findViewById(R.id.InMsj)).getText().toString();
				OutData= new DataOutputStream(socket.getOutputStream());
				OutData.writeUTF(mensajeOut);
				OutData.flush();
				System.out.println(mensajeOut);
				if(mensajeOut.equals(((EditText)findViewById(R.id.InMsj)).getText().toString()))
					//InData.close();
					OutData.close();
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * clase que implementa el runnable y arranca un thread
	 * para levantar cliente.
	 * @author Ellioth
	 *
	 */
	
	public class ConnectToServer implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				/**
				 * convierte el string de ip a un tipo InetAdress para el socket
				 */
				InetAddress ip= InetAddress.getByName(IP);
				socket= new Socket(ip,port);
				System.out.println("pass");
				Toast.makeText(getApplicationContext(), "connection aprove", Toast.LENGTH_SHORT).show();
				//InData = new DataInputStream(socket.getInputStream());
				//mensajeIn= InData.readUTF().toString();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("error host desconocido");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("error IO");
			}	
		}	
	}
	
	/**
	 * 
	 * <-----------------------------------------prueba abajo-------------------------------------------------------->
	 * este es el codigo anterior que estube usando para tratar de arrancar el server, por alguna razón nunca sirvio
	 * tuve que montar uno desde cero.
	 * 
	private void Connect(){
		try{
            btnConnect.setOnClickListener(new View.OnClickListener() {
                Socket s = new Socket(IP, port);
                String message = msg.getText().toString();
                @Override
                public void onClick(View v) {
                    String serverMsg;

                    try {
                        outp = new PrintWriter(s.getOutputStream(), true);
                        serverMsg = inp.readLine();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    convo.append(serverMsg + "\n");

                    if (mensaje != null) {
                        if (msg.getText().toString().trim() == "QUIT") {
                            try {
                                s.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            status.setText("Disconnected from server.");

                        } else {
                                try {

                                    convo.append(mensaje + "\n");
                                    outp.println(mensaje); 
                                    serverMsg = inp.readLine();
                                    convo.append(serverMsg + "\n");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                        }

                    }
                    else
                        System.out.println("conexion fallida");
                }
            });
		}catch (Exception e) {
            e.printStackTrace();
        }  
	}
	
	public void extract(){
		EditText textoIP = (EditText) findViewById(R.id.InIP);
		EditText textoPort = (EditText) findViewById(R.id.InPort);
		Button btnConnect = (Button) findViewById(R.id.BtnConnect);
		IP= textoIP.getText().toString();
		port =Integer.valueOf(textoPort.getText().toString());
	}
	
	
	
	public void listend(View v){
		if(v.getId()==R.id.BtnConnect){
			/*try{
				extract();
				System.out.println("sustrae textfields");
				socket= new Socket(IP,port);
				Toast.makeText(getApplicationContext(), "Pass", Toast.LENGTH_SHORT).show();
				System.out.println("se conecta a servidor");
				if(socket.isConnected())
					System.out.println("conexion realizada");
			}catch(Exception e ){
				Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
				System.out.println(e);
			}
		}
		else if(v.getId()== R.id.SndMsj){
			SendMsj();
		}
	}
	

	public void SendMsj(){
		try{
			EditText textoMsj= (EditText)findViewById(R.id.SendMjs);
			mensaje= textoMsj.getText().toString();
			System.out.println("entra en metodo de envio de mensaje");
			OutData= new DataOutputStream(socket.getOutputStream());
			if(mensaje.equals("out")&& socket.isConnected()){
				OutData.writeUTF(mensaje);
				OutData.close();
				socket.close();
			}
			else if (!mensaje.equals("out") && socket.isConnected())
				OutData.writeUTF(mensaje);
			
		}catch(Exception e){
			Toast.makeText(getApplicationContext(), "fail in .start()", Toast.LENGTH_SHORT).show();
			System.out.println(e);
		}
	}*/
}