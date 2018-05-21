package chapter1.bio;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

    private static TimeServerHandleExecutePool singleExecutor = new TimeServerHandleExecutePool(50, 10000);

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The server is start on port : " + serverSocket);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();

                //传统BIO
                // useThreadDirect(socket);
                //使用线程池封装一层的伪异步BIO
                useThreadPoolHandleIO(socket);

            }
        } finally {
            IOUtils.closeQuietly(serverSocket);

        }
    }

    // 直接使用多线程的方式接受请求  当有大量请求的情况下，可能会没有足够的内存申请那么多的内存
    private static void useThreadDirect(Socket socket) {
        new Thread(new TimeServerHandle(socket)).start();
    }

    //使用伪异步IO的形式接受请求，创建一个线程池。所有任务通过线程池完成任务执行。
    private static void useThreadPoolHandleIO(Socket socket) {
        singleExecutor.execute(new TimeServerHandle(socket));
    }

}
