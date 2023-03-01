import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    
    public static void main(String[] args) throws IOException {

        if(args.length != 2){
            throw new IllegalArgumentException("c'est deux argument qu'il faut mettre!!");
        }
        else{
            System.out.println("le clientTCP.py est en commentaire a la fin du ClientTCP.java");
        }
        int port = Integer.parseInt(args[1]);

        Socket clientSocket = new Socket();

        InetSocketAddress localhost= new InetSocketAddress(args[0],port);
        clientSocket.connect(localhost);
        Scanner scanner= new Scanner(System.in);

        while (scanner.hasNextLine()){
            String  textScanner = scanner.nextLine()+"\n";
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(textScanner.getBytes()); 
        }

        scanner.close();
        clientSocket.close();
    }
}

