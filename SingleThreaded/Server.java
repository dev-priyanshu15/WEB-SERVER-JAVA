
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void run() throws IOException {
        int port = 8010;

        try {
            ServerSocket socket = new ServerSocket(port); // Server socket create kiya
            socket.setSoTimeout(10000); // 10 sec tak client ka wait karega

            while (true) {
                try {
                    System.out.println("Server is listening on port " + port);

                    Socket acceptedConnection = socket.accept(); // Client ko accept kiya

                    System.out.println("Connection accepted from client " + acceptedConnection.getRemoteSocketAddress());

                    PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                    BufferedReader fromClient = new BufferedReader(
                            new InputStreamReader(acceptedConnection.getInputStream())
                    );

                    // Client ko ek message bhej diya
                    toClient.println("Hello from the server");
                    toClient.close();
                    fromClient.close();
                    acceptedConnection.close();

                } catch (IOException ex) {
                    ex.printStackTrace(); // Error agar 10 sec me koi nahi aaya
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace(); // Error agar socket hi nahi bana
        }
    }

    public static void main(String[] args) {
        Server server = new Server();

        try {
            server.run(); // Server chalu karo
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
