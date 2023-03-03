package app.Client;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    private String user;
    private String messageToSend;
    Publisher publish = new Publisher(user,messageToSend);

    public static void main(String[] args) throws IOException {
        try {
            int port = 12345;
            Socket clientSocket = new Socket();
            InetSocketAddress localhost = new InetSocketAddress("localhost", port);

            /*connection serveur*/
            clientSocket.connect(localhost);
            Scanner scanner = new Scanner(System.in);
            System.out.println("entrez votre Identifiant: ");

            /*Identification*/
            String User = '@'+scanner.nextLine();
            while (scanner.hasNextLine()) {
            System.out.println("votre identifiant c'est: "+User);

            /*envoyer message a publier*/

            System.out.println("Entrez votre message: ");
                String textScanner = "PUBLISH "+User+" "+scanner.nextLine() + "\n";
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(textScanner.getBytes());
            }
            /*Ecouter le serveur*/




            scanner.close();
            clientSocket.close();
        }catch(IOException e){
            e.printStackTrace();

        }

    }
}

