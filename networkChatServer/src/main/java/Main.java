import server.Server;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int port = 55333;
        Server server = new Server(port);
        writeSettings("hostname = localhost , port = " + port);
        server.execute();
    }

    private static void writeSettings(String string) {
        try (FileWriter writer = new FileWriter("C://study//netology.ry//networkChat//settings.txt")) {
            writer.write(string);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
