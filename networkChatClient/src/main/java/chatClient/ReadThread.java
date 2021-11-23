package chatClient;

public class ReadThread implements Runnable {
    private final ChatClient client;

    public ReadThread(ChatClient client) {
        this.client = client;

    }

    @Override
    public void run() {
        client.readMessage();

    }
}
