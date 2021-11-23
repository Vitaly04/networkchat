import chatClient.ChatClient;
import chatClient.ReadThread;
import chatClient.WriteThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя для отображения в чате: ");
        String userName = scanner.nextLine();
        List<String> settings = readSettings();
        System.out.println("Настройки для подключения к серверу : " + "\nhostname = " + settings.get(2) + ", port = " + settings.get(6));
        ChatClient client = new ChatClient(settings.get(2), Integer.parseInt(settings.get(6)), userName);
        ReadThread readThread = new ReadThread(client);
        WriteThread writeThread = new WriteThread(client);
        Thread thread1 = new Thread(readThread);
        Thread thread2 = new Thread(writeThread);
        thread1.start();
        thread2.start();
    }

    private static List<String> readSettings() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("C://study//netology.ry//networkChat//settings.txt"));
        List<String> list = new ArrayList<>();
        while(sc.hasNext()) {
            String s = sc.next();
            list.add(s);
        }
        return list;
    }
}
