package ait.chat.client.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MessageSender implements Runnable {
    private final Socket socket;

    public MessageSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Socket socket = this.socket) {
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your name");
            String name = br.readLine();
            System.out.println("Enter your message or type exit for quit");
            String message = br.readLine();
            while (!"exit".equalsIgnoreCase(message)) {
                // console.log(`${name} [${time}] ${message}`)
                socketWriter.printf("%s [%s] %s\n", name, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), message);
                message = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
