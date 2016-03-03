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
		while (true) {
			output.writeObject(sc.nextLine());
		}
	}
}