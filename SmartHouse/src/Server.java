import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		new Server();
	}
	public Server() {
		
		User user1= new User("idan");
		User user2= new User("ghsi");
		User user3= new User("ghsi10");
		
		user1.addDevice(new Lamp());
		user2.addDevice(new Lamp());
		user2.addDevice(new AirConditioner());
		user3.addDevice(new AirConditioner());
		
		users= new ArrayList<User>();
		
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		
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
	private User login(String user) {
		for (int i=0;i<users.size();i++)
			if (users.get(i).getName().compareTo(user)==0)
				return users.get(i);
		return null;
	}
	private class ServerThread extends Thread {
		private User user;
		private Socket socket;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		public ServerThread(Socket socket) {
			this.socket = socket;
			try
			{
				output = new ObjectOutputStream(socket.getOutputStream());
				input  = new ObjectInputStream(socket.getInputStream());
			} catch (Exception e) {
				closeConnection();
			}
		}
		public void run(){
			String[] data;
			boolean StillAlive = true;
			while(StillAlive) {
				try {
					data = ((String)input.readObject()).split(" ");
					if (data[0].compareTo("Login")==0) {
						user=login(data[1]);
						if (user==null) {
							try {
								output.writeObject("You are not logged in"); //fix it
								System.out.println("Unauthenticated request.");
							} catch (IOException e) {}
						}
						else
							System.out.println("new login: "+user.getName());
					}
					else if (user==null) {
						try {
							output.writeObject("You are not logged in");
							System.out.println("Unauthenticated request.");
						} catch (IOException e) {}
					}
					else if (data[0].compareTo("ListDevices")==0) {
						try {
							output.writeObject(user.strGetDevices());
							System.out.println("user: "+user.getName()+" asked for device list.");
						} catch (IOException e) {}
					}
					else if (data[0].compareTo("SetState")==0) {
						Device tmpD = user.getDevice(data[1]);
						if (tmpD==null)
							System.out.println("Bad Request.");
						else if(data[2].compareTo("on")==0) {
							tmpD.Mode(true);
							System.out.println("User: "+user.getName()+" change "+ tmpD.getName()+" mode to ON");
						}
						else if(data[2].compareTo("off")==0) {
							tmpD.Mode(false);
							System.out.println("User: "+user.getName()+" change "+ tmpD.getName()+" mode to OFF");
						}
						else
							System.out.println("Bad Request.");
					}
					else if (data[0].compareTo("SetValue")==0) {
						Device tmpD = user.getDevice(data[1]);
						if (tmpD==null)
							System.out.println("Bad Request.");
						else {
							tmpD.Value(Double.valueOf(data[2]));
							System.out.println("User: "+user.getName()+" change "+ tmpD.getName()+" value to "+data[2]);
						}
					}
					else if (data[0].compareTo("Logoff")==0) {
						StillAlive = false;
					}
				}catch (IOException | ClassNotFoundException e) {
					StillAlive = false; 
					closeConnection();
				}
			}
			closeConnection();
		}
		private void closeConnection() {
			try {
				if(output != null) output.close();
			} catch(Exception e) {}
			try {
				if(input != null) input.close();
			} catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			} catch (Exception e) {}
			clientList.remove(this);
		}
	}
}