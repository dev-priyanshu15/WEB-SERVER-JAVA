
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    // 🔹 Consumer function banaya jo client ke socket pe kaam karega
    public Consumer<Socket> getConsumer() {

        // ✅ Version 1: Traditional Anonymous Class (Commented Out)
        /*
        return new Consumer<Socket>() {
            @Override
            public void accept(Socket clientSocket) {
                try {
                    // Client ko message bhejna
                    PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                    toClient.println("Hello from the server");

                    // Stream aur socket close kar dena
                    toClient.close();
                    clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
         */
        // ✅ Version 2: Lambda Expression (Simple + Clean)
        return (clientSocket) -> {
            try {
                // Client ko data bhejna using output stream
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server");

                // Output stream aur socket band kar rahe
                toClient.close();
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
    }

    // 🔹 Server ka main run method
    public static void main(String[] args) {
        int port = 8010; // Server port
        try {
            // Server socket banaya
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000); // Timeout set (10 seconds)

            System.out.println("✅ Server is listening on port " + port);

            // Server infinite loop me client accept karega
            while (true) {
                Socket acceptedSocket = serverSocket.accept(); // Client accept
                System.out.println("🔗 Connection accepted from " + acceptedSocket.getRemoteSocketAddress());

                // Client ko handle karne ke liye ek naya thread
                Thread thread = new Thread(() -> {
                    new Server().getConsumer().accept(acceptedSocket);
                });
                thread.start();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
