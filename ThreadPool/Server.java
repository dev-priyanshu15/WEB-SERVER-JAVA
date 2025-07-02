
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ExecutorService threadPool;

    // Constructor to initialize thread pool
    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    // Method to handle individual client connection
    public void handleClient(Socket clientSocket) {
        try (
                PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
            // Send message to client
            toSocket.println("Hello from server " + clientSocket.getInetAddress());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Main method to start server
    public static void main(String[] args) {
        int port = 8010;         // Port where server will listen
        int poolSize = 10;       // Number of threads in pool

        Server server = new Server(poolSize); // Moved outside try so finally can access

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(70000); // Optional timeout

            System.out.println("Server is listening on port " + port);

            // Accept clients continuously
            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Use thread pool to handle the client
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Shutdown the thread pool when the server exits
            server.threadPool.shutdown();
        }
    }
}
