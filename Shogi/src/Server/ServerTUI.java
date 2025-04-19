package Server;

import java.io.IOException;

public class ServerTUI {
    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.setPort(8400);
        try {
            server.listen();
        } catch (IOException e) {
            System.out.println("Error starting server");
            e.printStackTrace();
        }

        if (server.isRunning()) {
            System.out.println("listening");
        } else {
            System.out.println("failure");
        }

    }
}

