package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class СlientForEchoServer  {

    public static void main(String[] args) throws  IOException {
        System.out.println(("Старт клиента"));
        Socket clientSocket = new Socket("localhost", 8189);
        System.out.println("Клиент запустился.");
        DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream outputSream = new DataOutputStream( clientSocket.getOutputStream());
        getMessageFromServer(inputStream);
        sendMessage(outputSream);
    }

    private static void getMessageFromServer(DataInputStream input) throws IOException {
            new Thread (()->{
                while (true){
                   System.out.println("Зашли в ветку");
                    try {
                        String serverAnswer = input.readUTF();
                        System.out.println("Сервер: " + serverAnswer);
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
                        //System.out.println("По идее, отправили сообщение серверу");
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
