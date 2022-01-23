package message;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LoggerMessage {

    public void logMessage(Message message) {
        try (FileWriter writer = new FileWriter("File1.log", true)) {
            writer.write(String.valueOf(message));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logListMessage(List<Message> list) {
        for (Message message : list) {
            logMessage(message);
        }
    }
}
