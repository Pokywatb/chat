package multiChat;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    public static final int PORT = 5555;
    public static CopyOnWriteArrayList<SClientsRead> clientsList = new CopyOnWriteArrayList<>();
    public static ArrayBlockingQueue<String> msg = new ArrayBlockingQueue<>(10, true);;

    public void work() throws IOException {
        ServerSocket server = new ServerSocket(PORT);


        System.out.println("сервер запущен. Порт:  " + PORT);
        try {
            Socket socket = server.accept();
            SClientsWrite sClientsWrite = new SClientsWrite(socket);
            while (true) {
               Socket socket1 = server.accept();
              // SClientsWrite sClientsWrite = new SClientsWrite(socket);

                try {
                    clientsList.add(new SClientsRead(socket1));
                    System.out.println(clientsList.toString());


                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}

class SClientsRead extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public SClientsRead(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = in.readLine();
                //  if(message.equals("exit")){
                //      break;
                // рассылка всем, кроме себя
                // }
//                for (SClients client : Server.clientsList) {
//                    if (!client.equals(this)) client.send(message);// рассылаем всем кроме отправителя
//                }
                Server.msg.put(message);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("клиент отсоединился");
        }
    }


    public void send(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



class SClientsWrite extends Thread {
    private Socket socket;
   // private BufferedReader in;
    private BufferedWriter out;

    public SClientsWrite(Socket socket) throws IOException {
        this.socket = socket;
        // in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = Server.msg.take();
                Iterator iter = Server.clientsList.iterator();
                while (iter.hasNext()){
                    SClientsRead client = (SClientsRead) iter.next();
                    if (!client.equals(this))  client.send(message);
                    client.send(socket.toString());
                }
                //for (SClientsRead client : Server.clientsList) {
                //    if (!client.equals(this)) client.send(message);// рассылаем всем кроме отправителя
               // }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


//    private void send(String message) {
//        try {
//            out.write(message + "\n");
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

