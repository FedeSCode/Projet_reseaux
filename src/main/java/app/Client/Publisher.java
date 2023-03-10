package app.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Publisher {
    private String idUser;
    private String messageToSend;


    public Publisher(Socket clientSocket) {
//        this.clientSocket = clientSocket;
//        clientSocket.connect();
    }

    public void messageToSend(String user, String message) throws IOException {
        String publishMessage = "PUBLISH" + user + message;
//        OutputStream outputStream = clientSocket.getOutputStream();

//        outputStream.write(publishMessage.getBytes(StandardCharsets.UTF_8));

    }










}
