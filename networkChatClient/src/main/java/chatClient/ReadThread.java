package chatClient;

import java.io.BufferedReader;
import java.net.Socket;

public class ReadThread implements Runnable {
    private final ChatClient client;
    private final Socket socket;

    public ReadThread(ChatClient client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        client.readMessage(socket);
    }
}
