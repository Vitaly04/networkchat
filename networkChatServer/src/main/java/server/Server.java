package server;

import message.ConverterToJson;
import message.LoggerMessage;
import message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private final int port;
    private final Map<ClientHandler, ClientHandler> clients = new ConcurrentHashMap();
    private final Map<Message, Message> historyMessage = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private final LoggerMessage loggerMessage;
    private final ConverterToJson converter;


    public Server(int port) {
        this.converter = new ConverterToJson();
        this.port = port;
        this.loggerMessage = new LoggerMessage();
    }

    public List<Message> getHistoryMessage() {
        return new ArrayList<>(historyMessage.values());
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler( this, socket);
                clients.put(client, client);
                Thread thread1 = new Thread(client);
                thread1.start();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "server socket connection error", e);
        }
    }

    public void sendMessageAllClients(Message message) {
        historyMessage.put(message, message);
        loggerMessage.logFile(message);
        for (ClientHandler clientHandler : clients.keySet()) {
            clientHandler.sendMessage(converter.createJson(message));
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}
