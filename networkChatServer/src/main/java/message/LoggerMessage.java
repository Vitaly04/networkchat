package message;

import java.io.FileWriter;
import java.io.IOException;

public class LoggerMessage {

    public void logFile(Message message) {
        try (FileWriter writer = new FileWriter("File.log", true)) {
            writer.write(String.valueOf(message));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
