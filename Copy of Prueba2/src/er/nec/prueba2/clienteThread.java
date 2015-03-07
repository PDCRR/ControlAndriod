package er.nec.prueba2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class clienteThread implements Runnable {

	private String IP;
	private int port;
	private Socket socket;
	//private DataInputStream InData; //nos crea el decodificador de mensajes recibidos por el server
    private DataOutputStream OutData; //nos crea el codificador de mensajes enviados al server
    private boolean bandera; //indentificador para conexion positiva
    
    /**
     * inicializa el thread
     */
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			InetAddress ip= InetAddress.getByName(IP);
			socket= new Socket(ip,port);
			System.out.println("pass");
			bandera=true;
			//Toast.makeText(getApplicationContext(), "connection aprove", Toast.LENGTH_SHORT).show();
			//InData = new DataInputStream(socket.getInputStream());
			//mensajeIn= InData.readUTF().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bandera=false;
			System.out.println("error host desconocido");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bandera=false;
			System.out.println("error IO");
		}
	}
	
	public void sendMsj(String mensaje){
		try{
			OutData= new DataOutputStream(socket.getOutputStream());
			OutData.writeUTF(mensaje);
			OutData.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void salir(){
		try {
			OutData.close();
			//InData.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setIP(String ip){
		this.IP=ip;
	}
	
	public void setPort(int port){
		this.port=port;
	}

	public boolean getState(){
		return bandera;
	}
}
