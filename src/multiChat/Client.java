package multiChat;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static multiChat.Server.clientsList;

class Client {

    //public static String ip = "localhost";
    //public static int port = 5555;
    private String ip;
    private int port;
    private Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader reader;
    private String name;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        System.out.println("ip: " + ip + " " + "port: " + port);


        try {
            this.clientSocket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader = new BufferedReader(new InputStreamReader(System.in)); // читаем введенное сообщение
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.enterName();
            new Client.ReadMessage().start();
            new Client.WriteMessage().start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enterName() {
        System.out.println("Введите имя");
        try {
            name = reader.readLine();
            System.out.println("имя введено");
            out.write(name + ":" + "Присоединился " + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (!clientSocket.isClosed()) {
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }


    public class ReadMessage extends Thread  {
        @Override
        public void run() {

            String message;
            try {
                while (true) {
                    message = in.readLine();

                    System.out.println(message);
                }
            } catch (IOException e) {
                Client.this.disconnect();
            }
        }
    }

    public class WriteMessage  extends Thread{
        @Override
        public void run() {
            while (true) {
                String message;
                try {

                    message = reader.readLine();
                    if (message.equals("stop")) {
                        out.write("stop" + "\n");
                        System.out.println("клиент отключается");
                       // clientsList.remove(this);// удаляем из листа
                        Client.this.disconnect();//отсоединяем клиента

                        break;
                    } else {
                        out.write(LocalDateTime.now() + " " + name + ": " + message + "\n"); // отправляем на сервер
                    }
                    out.flush();
                } catch (IOException e) {
                    Client.this.disconnect();

                }

            }

        }
    }
}





