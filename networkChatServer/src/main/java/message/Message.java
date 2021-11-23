package message;

import java.util.Date;

public class Message {
    private String clientName;
    private String textMessage;
    private String time;

    public Message(String clientName, String textMessage) {
        this.clientName = clientName;
        this.textMessage = textMessage;
        Date dateTime = new Date();
        this.time = dateTime.toString();
    }

    @Override
    public String toString() {
        return  "name = " + clientName +
                ", textMessage = " + textMessage +
                ", time = " + time + "\n";
    }

}
