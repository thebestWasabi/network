package edu.javacourse.net;


import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 8; i++) {
            SimpleClient simpleClient = new SimpleClient(i);
            simpleClient.start();
        }
    }
}

class SimpleClient extends Thread {
    private static final String[] COMMAND = {"HELLO", "MORNING", "DAY", "EVENING"};
    private int cmdNumber;

    public SimpleClient(int cmdNumber) {
        this.cmdNumber = cmdNumber;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("127.0.0.1", 25225);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String command = COMMAND[cmdNumber % COMMAND.length];
            String stringBuilder = command + " " + "Maxim";
            bufferedWriter.write(stringBuilder);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            String answer = bufferedReader.readLine();
            System.out.println("Client got string: " + answer);

            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
