package multiChat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server  {
    public static final int PORT = 5555;
    public static LinkedList<SClients> clientsList = new LinkedList<>();

    public void work () throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("сервер запущен. Порт:  " + PORT);
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    clientsList.add(new SClients(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}

class SClients extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public SClients(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        String message;
        try{
            while(true){
                message = in.readLine();
                if(message.equals("exit")){
                    break;
                    // рассылка всем, кроме себя
                } for(SClients client : Server.clientsList){
                    if (!client.equals(this)) client.send(message);// рассылаем всем кроме отправителя
                                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// TODO сообщение пока просто стринг. Заменить на объект + сериализацию позже
    private void send(String message){
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}