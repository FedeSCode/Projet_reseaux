package app;
import app.Server.*;
import app.ServerMaster.ServerMasterTCP;


public class LauncherServer {
    public static void main(String[] args) {
        new ServerMasterTCP().start();

        while(!ServerMasterTCP.ready) {
            System.out.print("");
        }
/*
        for (int i = 0; i < 2; i++) {
            new ServerSlave(ServerMasterTCP.PORT + 1 + i).start();
        }*/
    }
}
