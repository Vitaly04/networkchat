package chatClient;

import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread implements Runnable {
    private final ChatClient client;
    private Socket socket;

    public WriteThread(ChatClient client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        client.writeMessage(socket);
    }
}
