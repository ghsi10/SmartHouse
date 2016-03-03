import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static final int PORT = 1234;
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", PORT);
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		Scanner sc = new Scanner(System.in);
		new Thread(){public void run(){
			try {
				while (true) 
					System.out.println((String)input.readObject());
			} catch (ClassNotFoundException | IOException e) {}
			}}.start();
		while (true) {
			output.writeObject(sc.nextLine());
		}
	}
}