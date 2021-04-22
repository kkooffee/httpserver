import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args){
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 80), 0);
            server.createContext("/convert", new  Handler(args[0]));
            server.setExecutor(null);
            server.start();
            //logger.info(" Server started on port 8001");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
