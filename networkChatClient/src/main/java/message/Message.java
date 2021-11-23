package message;

public class Message {
    private String clientName;
    private String textMessage;
    private String time;

    public String getClientName() {
        return clientName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    @Override
    public String toString() {
        return  "name = " + clientName +
                ", textMessage = " + textMessage +
                ", time = " + time + "\n";
    }
}
