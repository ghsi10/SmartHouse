import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static final int PORT = 1234;
	private  ArrayList<User> users; 
	private ServerSocket serverSock;
	private  ArrayList<ServerThread> clientList;
	private boolean keepAlive;
	public static void main(String[] args) {
		//add users...
		new Server();
	}
	public Server() {
		keepAlive=true;
		clientList = new ArrayList<ServerThread>();
		try {
			serverSock = new ServerSocket(PORT);
			System.out.println("Server is up");
			while(keepAlive){
				try {
					Socket tmpSock = serverSock.accept();
					ServerThread tmp = new ServerThread(tmpSock);
					clientList.add(tmp);
					tmp.start();
				} catch (IOException e) {}
			}
			serverSock.close();
		} catch(Exception e) {
			System.out.println("server crash - " + e);
		}
	}
	private boolean loginCheck(String user) {
		for (int i=0;i<users.size();i++)
			if (users.get(i).getName().compareTo(user)==0)
				return true;
		return false;
	}
	public class ServerThread extends Thread {
		private String user;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		public ServerThread(Socket socket) {
			try
			{
				output = new ObjectOutputStream(socket.getOutputStream());
				input  = new ObjectInputStream(socket.getInputStream());
				user = (String) input.readObject();
				if (!loginCheck(user)) {
					System.out.println("User Rejected: " + user);
					this.interrupt();
				}
				System.out.println("New user logged in: " + user);
			} catch (Exception e) {
				clientList.remove(this);
			}
		}
		public void run(){ 
		}
	}
}