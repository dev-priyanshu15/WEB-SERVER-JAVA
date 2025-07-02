
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    // Ye method ek Runnable object return karega, jo thread ke andar chalega
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                int port = 8010; // Server ka port jahan wo sun raha hai

                try {
                    // Server ka address (localhost matlab apne hi computer par run kar rahe ho)
                    InetAddress address = InetAddress.getByName("localhost");

                    // Socket banaya jo server se connect karega
                    Socket socket = new Socket(address, port);

                    try {
                        // Server se data receive karne ke liye BufferedReader banaya
                        BufferedReader fromSocket = new BufferedReader(
                                new InputStreamReader(socket.getInputStream())
                        );

                        // Server se jab tak data milta rahe, tab tak read karte jao
                        String line;
                        while ((line = fromSocket.readLine()) != null) {
                            System.out.println(Thread.currentThread().getName() + " received: " + line);
                        }

                        // Connection close kar diya jab data milna band ho gaya
                        fromSocket.close();
                        socket.close();

                    } catch (IOException ex) {
                        ex.printStackTrace(); // agar reading ya writing me koi dikkat ho
                    }

                } catch (IOException e) {
                    e.printStackTrace(); // agar server se connection nahi ho paya
                }
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();

        // 100 threads banaye jo ek sath server se connect karenge
        for (int i = 0; i < 100; i++) {
            try {
                // Thread banaya aur usme getRunnable() daala
                Thread thread = new Thread(client.getRunnable(), "Client-" + i);
                thread.start(); // Thread chalu kar diya
            } catch (Exception ex) {
                ex.printStackTrace(); // agar thread banane me error aaye
            }
        }
    }
}
