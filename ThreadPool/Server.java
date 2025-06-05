import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Server {

    private final ExecutorService threadPool;

    public Server(int poolSize){
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }
   
  public void handleClient(Socket clienSocket){
    try (PrintWriter toSocket = new PrintWriter(clienSocket.getOutputStream(),true)) {
        toSocket.println("hello from server "+ clienSocket.getInputStream());
        
    } catch (Exception e) {
        e.printStackTrace();
        // TODO: handle exception
    }
  }


    public static void main(String[] args){
         int port =8010;
         int poolSize = 100;
         Server server =new Server(poolSize);
         try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("server is started at port: "+ port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                server.threadPool.execute(()-> server.handleClient(clientSocket));
            }

         } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
         } finally{
            server.threadPool.shutdown();
         }
    }
}
