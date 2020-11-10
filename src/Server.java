import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    //сокет для общения
    private static Socket clientSocket;
    // серверсокет
    private static ServerSocket server;
    // поток чтения из сокета
    private static BufferedReader in;
    // поток записи в сокет
    private static BufferedWriter out;

    //колода-сброс
    private static ArrayList<Card> serverDeck;

    public static void main(String[] args) {
        //работа с Gson
        Gson gson = new Gson();

        //создаем колоду с одной картой (красная 2)
        serverDeck = new ArrayList<>();
        serverDeck.add(new Card(Card.colours[1], Card.nums[2] ));
        try {
            try {
                // серверсокет прослушивает порт 4004
                server = new ServerSocket(4004);
                // хорошо бы серверу объявить о своем запуске
                System.out.println("Игра началась!");
                try {
                    while (true) {
                        // accept() будет ждать пока кто-нибудь не захочет подключиться
                        clientSocket = server.accept();
                        // установив связь и воссоздав сокет для общения с клиентом можно перейти
                        // к созданию потоков ввода/вывода. теперь мы можем принимать сообщения
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        // и отправлять
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                        System.out.println("Новый игрок подключен! Отправляем ему последнюю карту с сервера");
                        //получаем последнюю карту с сервера
                        Card lastCard = serverDeck.get(serverDeck.size() - 1);
                        String jsonLastCard = gson.toJson(lastCard);

                        //и отправляем ее игроку
                        out.write(jsonLastCard + "\n");
                        // выталкиваем все из буфера
                        out.flush();

                        // ждём, чем ответит клиент - картой или null
                        String jsonTryCard = in.readLine();
                        //Преобразовываем ее в обьект
                        Card tryCard = gson.fromJson(jsonTryCard, Card.class);

                        if (tryCard != null) {
                            System.out.println("Карту " + lastCard + " удалось побить картой " + tryCard);
                            //делаем ее последней в стопке сервера
                            serverDeck.add(tryCard);
                        } else {
                            System.out.println("Карту " + lastCard + " побить не удалось");
                        }

                        //проверяем, закончилась ли игра
                        String gameContinue = in.readLine();
                        if (gameContinue.equals("stop_game")) {
                            System.out.println("Сервер закрывает свою работу");
                            break;
                        }

                        // закрываем сокеты и потоки и ждем нового соединения
                        clientSocket.close();
                        in.close();
                        out.close();
                    }

                } finally {
                    // в любом случае сокет и потоки будут закрыты
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Игра закончилась!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
