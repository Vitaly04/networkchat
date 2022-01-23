package chatClient;

import message.ConverterFromJson;
import message.LoggerMessage;
import message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient {
    private final String userName;
    private LoggerMessage loggerMessage;
    private Scanner scanner;
    private ConverterFromJson converter;
    private List<Message> historyMessage;
    private static final Logger logger = Logger.getLogger(ChatClient.class.getName());
    private final String hostname;
    private final int port;

    public ChatClient(String hostname, int port, String userName) {
        this.historyMessage = new ArrayList<>();
        this.converter = new ConverterFromJson();
        this.scanner = new Scanner(System.in);
        this.loggerMessage = new LoggerMessage();
        this.userName = userName;
        this.hostname = hostname;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            ReadThread readThread = new ReadThread(this, socket);
            WriteThread writeThread = new WriteThread(this, socket);
            Thread thread1 = new Thread(readThread);
            Thread thread2 = new Thread(writeThread);
            thread1.start();
            thread2.start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "socket connection error", e);
        }
    }

    public void readMessage(Socket socket) {
        try (socket;
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {
            String listHistoryChat = reader.readLine();
            historyMessage = converter.jsonToList(listHistoryChat);
            loggerMessage.logListMessage(historyMessage);

            while (true) {
                String response = reader.readLine();
                if (response.equals("bye")) {
                    reader.close();
                    break;
                }
                Message message = converter.createMessage(response);
                System.out.println(message.getClientName() + " : " + message.getTextMessage());
                loggerMessage.logMessage(message);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "input stream error", e);
        }
    }

    public void writeMessage(Socket socket) {
        try (socket;
             PrintWriter writer = new PrintWriter(
                     socket.getOutputStream(), true)) {
            writer.println(userName);
            String text;
            do {
                text = (scanner.nextLine());
                writer.println(text);
            } while (!text.equals("exit"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "output stream error", e);
        }
    }
}