package chatClient;

import message.ConverterFromJson;
import message.LoggerMessage;
import message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient {
    private final String userName;
    protected LoggerMessage logger;
    private BufferedReader reader;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter writer;
    private ConverterFromJson converter;
    private ArrayList<Message> historyMessage;

    public ChatClient(String hostname, int port, String userName) {
        this.historyMessage = new ArrayList<>();
        this.converter = new ConverterFromJson();
        this.scanner = new Scanner(System.in);
        this.logger = new LoggerMessage();
        this.userName = userName;
        try {
            this.socket = new Socket(hostname, port);
            InputStream input = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            this.writer = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() {
        try {
            String listHistoryChat = reader.readLine();
            historyMessage = (ArrayList<Message>) converter.jsonToList(listHistoryChat);
            logger.logListMessage(historyMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                String response = reader.readLine();
                if (response.equals("bye")) {
                    reader.close();
                    break;
                }
                Message message = converter.createMessage(response);
                System.out.println(message.getClientName() + " : " + message.getTextMessage());
                logger.logMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessage() {
        writer.println(userName);
        String text;
        do {
            text = (scanner.nextLine());
            writer.println(text);

        } while (!text.equals("exit"));

        writer.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
