//package monoChat.client.server;
//
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.time.LocalDateTime;
//
//public class Server {
//
//    public  Socket clientSocket ;
//    public  ServerSocket serverSocket ;
//    public  BufferedReader in ;
//    public  BufferedWriter out;
//
//
//    public void work() {
//
//
//            try {
//                try {
//                    serverSocket = new ServerSocket(5555);
//                    System.out.println("Запуск сервера");
//                    clientSocket = serverSocket.accept();
//                    try {
//
//                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//                        while (true){
//                         String word = in.readLine();
//                            System.out.println( LocalDateTime.now() + " " + word);
//                            out.write("Сообщение" + " " + word + " принято" + "\n");
//                            if(word.equals("exit")){break;}
//                            out.flush();
//                        }
//
//
//                    } finally {
//                        System.out.println("Клиент отсоединился");
//                        clientSocket.close();
//                        in.close();
//                        out.close();
//                    }
//
//
//                } finally {
//                    System.out.println("Сервер закрыт!");
//                    serverSocket.close();
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
