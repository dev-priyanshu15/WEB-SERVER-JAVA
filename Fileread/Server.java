
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    // Thread pool banaya jo multiple clients handle karega
    private final ExecutorService threadPool;

    // File content ko ek baar read karke memory me store karenge
    private final List<String> fileData;

    // Constructor: Thread pool banayenge aur file read karenge
    public Server(int poolSize, String filePath) throws IOException {
        // Fixed size thread pool banaya (e.g., 100 threads)
        this.threadPool = Executors.newFixedThreadPool(poolSize);

        // File ko read karke line by line list me store kiya
        this.fileData = Files.readAllLines(Paths.get(filePath));
    }

    // Har client ke liye ye method chalega: file bhejna ka kaam yahan hota hai
    public void handleClient(Socket clientSocket) {
        try (
                // Client ko data bhejne ke liye PrintWriter use kiya
                PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // File ke sabhi lines client ko bhej do
            for (String line : fileData) {
                toSocket.println(line);
            }

            System.out.println("File data sent to " + clientSocket.getInetAddress());

        } catch (IOException ex) {
            ex.printStackTrace(); // Error aane par stack trace print karo
        } finally {
            try {
                clientSocket.close(); // Kaam ho gaya to client ka connection close
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method: yahi se server start hota hai
    public static void main(String[] args) {
        int port = 8010;              // Server is port par sunega
        int poolSize = 100;           // Thread pool size set kiya (max 100 clients at a time)
        String filePath = "data.txt"; // File ka naam jo bhejni hai

        try {
            // Server object banaya aur file load karli memory me
            Server server = new Server(poolSize, filePath);

            // Server socket open kiya
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server is listening on port " + port);

                // Server hamesha clients ka wait karega
                while (true) {
                    // Naya client connect ho gaya
                    Socket clientSocket = serverSocket.accept();

                    // Thread pool ka ek thread use karke client ko handle karo
                    server.threadPool.execute(() -> server.handleClient(clientSocket));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Server band hone par thread pool ko shutdown karo
                server.threadPool.shutdown();
            }

        } catch (IOException e) {
            System.out.println("Error loading file: " + filePath);
            e.printStackTrace();
        }
    }
}
