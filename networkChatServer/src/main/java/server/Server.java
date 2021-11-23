package server;

import message.ConverterToJson;
import message.LoggerMessage;
import message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final int port;
    private final ArrayList<ClientHandler> clients = new ArrayList<>();
    private final ArrayList<Message> historyMessage = new ArrayList<>();

    private final LoggerMessage logger;
    private final ConverterToJson converter;


    public Server(int port) {
        this.converter = new ConverterToJson();
        this.port = port;
        this.logger = new LoggerMessage();
    }

    public ArrayList<Message> getHistoryMessage() {
        return historyMessage;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler( this, socket);
                clients.add(client);
                Thread thread1 = new Thread(client);
                thread1.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageAllClients(Message message) {
        historyMessage.add(message);
        logger.logFile(message);
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(converter.createJson(message));
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}
