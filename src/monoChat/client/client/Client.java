//package monoChat.client.client;
//
//import java.io.*;
//import java.net.Socket;
//
//public class Client {
//    private  Socket clientSocket ;
//    private  BufferedReader reader ;
//    private  BufferedReader in ;
//    private  BufferedWriter out ;
//
//    public void connect() {
//        try{
//            try {
//                clientSocket = new Socket("localhost", 5555);
//                reader = new BufferedReader(new InputStreamReader(System.in));
//                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//                while(true){
//                 System.out.println("Введите сообщение");
//                    String word = reader.readLine();
//                    System.out.println("сообщение введено");
//                    out.write(word + "\n");
//                    if(word.equals("exit")){break;}
//                    out.flush();
//                    String serverMessage = in.readLine();
//                    System.out.println(serverMessage);}
//
//
//            } finally {
//                System.out.println("отключение клиента");
//                clientSocket.close();
//                in.close();
//                out.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
