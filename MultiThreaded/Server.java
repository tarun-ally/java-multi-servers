import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
   
    public Consumer<Socket> getConsumer(){
        return new Consumer<Socket>() {
            @Override
            public void accept(Socket clientSocket){
                   try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("hello from multi server ");
                toClient.close();
                clientSocket.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            }
        };
        // return(clientSocket)->{
        //     try {
        //         PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
        //         toClient.println("hello from multi server ");
        //         toClient.close();
        //         clientSocket.close();
        //     } catch (Exception e) {
        //         // TODO: handle exception
        //         e.printStackTrace();
        //     }
        // };
    }
    public static void main(String[] args){
         int port =8010;
         Server server =new Server();
         try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("server is started at port: "+ port);
            while (true) {
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            }

         } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
         }
    }
}
