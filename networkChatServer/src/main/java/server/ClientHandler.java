package server;

import message.ConverterToJson;
import message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Server server;
    private final Socket socket;
    private PrintWriter out;
    private static int clients_count = 0;
    private final ConverterToJson converter;

    public ClientHandler(Server server, java.net.Socket socket) {
        this.converter = new ConverterToJson();
        clients_count++;
        this.server = server;
        this.socket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
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
            server.sendMessageAllClients(new Message("сервер","Клиентов в чате = " + clients_count));

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
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    public void sendMessage(String message) {
        try {
            out.println(message);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void close() {
        server.removeClient(this);
        clients_count--;
        server.sendMessageAllClients(new Message("сервер","Клиентов в чате = " + clients_count));
    }
}