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
    private boolean bandera;
    
    /**
     * inicializa el thread
     */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			InetAddress ip= InetAddress.getByName(IP);
			socket= new Socket(ip,port);
			bandera=true;
			System.out.println("pass");
			OutData= new DataOutputStream(socket.getOutputStream());
			//Toast.makeText(getApplicationContext(), "connection aprove", Toast.LENGTH_SHORT).show();
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
	
	/**
	 * cada vez que se crea un mensaje en la clase de 
	 * @param mensaje
	 */
	public void sendMsj(String mensaje){
		try{
			OutData.writeUTF(mensaje);
			OutData.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * metodo para saber que hacer cuando se quiere cerrar el thread
	 */
	public void salir(){
		try {
			sendMsj("out");
			OutData.close();
			//InData.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * cofirma si la conexion es positiva o negativa
	 * @return
	 */
	public boolean ifConnetion(){
		return bandera;
	}
	
	/**
	 * establece el IP del socket
	 * @param ip
	 */
	public void setIP(String ip){
		this.IP=ip;
	}
	
	/**
	 * establece el puerto del socket
	 * @param port
	 */
	public void setPort(int port){
		this.port=port;
	}
}
