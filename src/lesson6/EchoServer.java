package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    private static final int SERVER_PORT = 8189;

    public static void main(String[] args) {
        System.out.println(("Старт сервера"));
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Север запущен, ожидаем подключение...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            getMessageFromClient(inputStream);
            sendMessage(outputStream);
        } catch (IOException e) {
            System.err.println("Port " + SERVER_PORT + "занят.");
            e.printStackTrace();
        }
    }

    private static void getMessageFromClient(DataInputStream _input) {
         new Thread (()->{
             while (true){
                System.out.println("Зашли в ветку");
                try {
                    String serverAnswer = _input.readUTF();
                    System.out.println("Клиент: " + serverAnswer);
                    if(serverAnswer.equals("/end")) {
                        System.exit(0); //это подглядела, вставила, когда пыталась понять, почему мой вариант криво работает
                    }
                } catch (IOException e) {
                    System.err.println("Соеднинение с сервером закрыто.");
                    e.printStackTrace();
                    break;
                }
             }
         }).start();
    }

    private static void sendMessage(DataOutputStream _outputSream){
        new Thread (()-> {
        Scanner sc = new Scanner(System.in);
             while(true) {
                  try {
                     System.out.print("Ваше сообщение: \n");
                     String clientMessage = sc.next();
                     _outputSream.writeUTF(clientMessage);
                     if (clientMessage.equals("/end")) {
                        System.out.println("Работа завершена клиентом.");
                        break;
                     }
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
             }
        }).start();
    }
}
