package app.Client;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    
    public static void main(String[] args) throws IOException {
        try {
            int port = 12345;

            Socket clientSocket = new Socket();
            InetSocketAddress localhost = new InetSocketAddress("localhost", port);

            clientSocket.connect(localhost);
            String publish = "PUBLISH" + "\n";
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()) {
                String textScanner = scanner.nextLine() + "\n";
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(publish.getBytes());
                outputStream.write(textScanner.getBytes());
            }
            scanner.close();
            clientSocket.close();
        }catch(IOException e){
            e.printStackTrace();

        }

    }
}

