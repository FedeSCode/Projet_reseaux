package app.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Rcv_ids extends Request{
    String author, tag, since_id,limit;

    public Rcv_ids(BufferedReader userIn) throws IOException {
        System.out.println("QELS FILTRES");
            boolean isFiltered = true;
        while(isFiltered) {
            System.out.println("1.author \n2.tag \n3.since_id \n4.limit\n5.ENVOYER");
            switch (userIn.readLine()) {
                case "1" -> {
                    System.out.println("ecrivez le nom de l'utilisateur commencent avec un '@' ");
                    author = userIn.readLine();
                }
                case "2" -> {
                    System.out.println("ecrivez le tag commencent avec un '#' ");
                    tag = userIn.readLine();
                }
                case "3" -> {
                    System.out.println("since_id ");
                    since_id = userIn.readLine();
                }
                case "4" -> {
                    System.out.println("nombre des messages ");
                    limit = userIn.readLine();
                }
                default -> isFiltered = false;
            }
        }
    }

    @Override
    String entete(){
        String output = "RCV_IDS ";
        if(author != null){
            output += "author:"+author+" ";
        }
        if(tag != null){
            output += "tag:"+tag + " ";
        }
        if(since_id != null){
            output += "since_id:"+since_id + " ";
        }
        if(limit!= null){
            output +="limit:"+limit + " ";
        }
        return output;
    }

}
