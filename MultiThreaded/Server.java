import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer(){
        // return new Consumer<Socket>() {
        //     @Override
        //     public void accept(Socket clientSocket) {
        //         try {
        //             PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
        //             toClient.println("Hello from server");
        //             toClient.close();
        //             clientSocket.close();
        //         } catch (IOException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // };

        return (clientSocket) -> {
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println("Hello from server " + clientSocket.getInetAddress());
                
            } catch (IOException e){
                e.printStackTrace();
            }
        };

    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port : "+port);
            while(true){
                Socket clientSocket = serverSocket.accept();

                Thread thread = new Thread(()-> server.getConsumer().accept(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
