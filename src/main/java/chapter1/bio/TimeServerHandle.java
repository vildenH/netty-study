package chapter1.bio;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandle implements Runnable {

    private Socket socket;

    public TimeServerHandle(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("The time server receive order :" + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "Bad Order";
                out.println(currentTime);

            }
        } catch (IOException e) {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(socket);
        }

    }
}
