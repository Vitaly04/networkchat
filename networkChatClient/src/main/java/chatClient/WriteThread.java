package chatClient;

public class WriteThread implements Runnable {
    private final ChatClient client;

    public WriteThread(ChatClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        client.writeMessage();
    }
}
