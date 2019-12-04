package multijugador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3 {

    private ServerSocket serverSocket;
    private Socket socket;

    public Server3() throws IOException, ClassNotFoundException {

        serverSocket = new ServerSocket(5000);
        System.out.println("Servidor iniciado");

        while (true){

            System.out.println("Esperando conexión con el cliente...");
            socket = serverSocket.accept();
            System.out.println("Cliente "+socket.getInetAddress().getHostAddress()+" conectado.");

            HiloServer3 hilo = new HiloServer3(socket);
            hilo.start();

        }


    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Server3();
    }
}
