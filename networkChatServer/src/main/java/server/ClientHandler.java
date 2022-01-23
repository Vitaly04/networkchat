package server;

import message.ConverterToJson;
import message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private final Server server;
    private final Socket socket;
    private PrintWriter out;
    private static final AtomicInteger quantityOfClients = new AtomicInteger();
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private final ConverterToJson converter;

    public ClientHandler(Server server, java.net.Socket socket) {
        this.converter = new ConverterToJson();
        quantityOfClients.incrementAndGet();
        this.server = server;
        this.socket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "output stream error", e);
        }
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream()))) {
            String clientName = in.readLine();
            sendMessage(converter.listToJson(server.getHistoryMessage()));
            server.sendMessageAllClients(new Message("сервер","Новый участник " + clientName + " вошёл в чат!"));
            server.sendMessageAllClients(new Message("сервер","Клиентов в чате = " + quantityOfClients.get()));

            while (true) {
                final String textMessage = in.readLine();
                if (("exit").equals(textMessage)) {
                    sendMessage("bye");
                    break;
                }
                server.sendMessageAllClients(new Message(clientName,  textMessage));
            }
            server.sendMessageAllClients(new Message("сервер","Участик " + clientName + " вышёл из чата!"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "input output stream error", e);
        } finally {
            this.close();
            out.close();
        }
    }

    public void sendMessage(String message) {
        try {
            out.println(message);
            out.flush();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "output stream error");
        }
    }

    private void close() {
        server.removeClient(this);
        quantityOfClients.decrementAndGet();
        server.sendMessageAllClients(new Message("сервер","Клиентов в чате = " + quantityOfClients.get()));
    }
}