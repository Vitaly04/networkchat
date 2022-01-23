import chatClient.ChatClient;

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
        client.execute();
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
