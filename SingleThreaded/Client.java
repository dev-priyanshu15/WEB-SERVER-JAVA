
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public void run() throws UnknownHostException, IOException {
        int port = 8010; // 🔹 Server ka port jahan client connect karega

        // 🔹 Server address (yahan 'localhost' ka matlab same machine)
        InetAddress address = InetAddress.getByName("localhost");

        // 🔹 Server se connection banaya
        Socket socket = new Socket(address, port);

        // 🔹 Server ko message bhejne ke liye stream banayi
        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);

        // 🔹 Server se message lene ke liye stream banayi
        BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // 🔹 Server ko message bhejna
        toSocket.println("Hello from the client");

        // 🔹 Server se jo response aaya usko read kiya
        String line = fromSocket.readLine();
        System.out.println("📥 Response from the server is: " + line);

        toSocket.close();
        fromSocket.close();
        socket.close();// 🔚 Connection close kar diya
    }

    public static void main(String[] args) {
        try {
            Client client = new Client(); // 🔸 Object banaya
            client.run();                 // 🔸 run method chalu kiya
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
