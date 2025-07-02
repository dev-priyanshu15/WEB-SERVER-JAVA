
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    // Ye method ek Runnable object return karega, jo thread me run hoga
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010;

                try {
                    // 🔹 Server ka IP address (yahan localhost = apna hi system)
                    InetAddress address = InetAddress.getByName("localhost");

                    // 🔹 Client socket create ho gaya, jo server se connect karega
                    Socket socket = new Socket(address, port);

                    try {
                        // 🔸 Server ko message bhejne ke liye
                        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);

                        // 🔸 Server se response lene ke liye
                        BufferedReader fromSocket = new BufferedReader(
                                new InputStreamReader(socket.getInputStream())
                        );

                        // 🔹 Server ko message bhejna
                        toSocket.println("Hello from Client " + socket.getLocalSocketAddress());

                        // 🔹 Server se response lena
                        String line = fromSocket.readLine();
                        System.out.println("📥 Response from Server: " + line);

                    } catch (IOException ex) {
                        ex.printStackTrace(); // agar stream open ya read/write me problem ho
                    }

                } catch (IOException e) {
                    e.printStackTrace(); // agar connection me problem ho
                }
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();

        // 100 threads banayenge jo ek sath server se connect karenge
        for (int i = 0; i < 100; i++) {
            try {
                Thread thread = new Thread(client.getRunnable()); // thread bana
                thread.start(); // thread start kiya
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
