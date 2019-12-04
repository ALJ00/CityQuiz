package firmadigital;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class Server2 {

    private ServerSocket serverSocket;
    private Socket socket;

    public Server2() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {

        serverSocket = new ServerSocket(5000);
        System.out.println("Servidor iniciado");

        while (true){

            System.out.println("Esperando conexión con el cliente...");
            socket = serverSocket.accept();
            System.out.println("Cliente "+socket.getInetAddress().getHostAddress()+" conectado.");

            HiloServer2 hilo = new HiloServer2(socket);
            hilo.start();

        }


    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        new Server2();
    }
}
