package multijugador;

import com.company.Jugador;
import com.company.Servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HiloServer3 extends Thread {

    private Socket socket;
    private ServerSocket serverSocket;
    private ObjectInputStream flujoEntradaObjecto;
    private ObjectOutputStream flujoSalidaObjeto;
    private DataOutputStream flujoSalidaOpcion;
    private DataInputStream flujoEntradaOpcion;
    private String opcion;

    //atributos para el control del juego
    private HashMap<String, String> capitalCities = new HashMap<String, String>();
    private Jugador jugador;
    List<String> valuesList;

    public HiloServer3(Socket socket1) throws IOException, ClassNotFoundException {


        socket = socket1;

        // añado keys y values (pais, ciudad)
        capitalCities.put("England", "London");
        capitalCities.put("Germany", "Berlin");
        capitalCities.put("Norway", "Oslo");
        capitalCities.put("USA", "Washington");


        valuesList = new ArrayList<String>(capitalCities.keySet());


        // creo los flujos de salida
        flujoSalidaObjeto = new ObjectOutputStream(socket.getOutputStream());
        flujoSalidaOpcion = new DataOutputStream(socket.getOutputStream());

        // creo los flujos de entrada
        flujoEntradaObjecto = new ObjectInputStream(socket.getInputStream());
        flujoEntradaOpcion = new DataInputStream(socket.getInputStream());

        //recibo el nuevo jugador
        jugador = (Jugador) flujoEntradaObjecto.readObject();
        System.out.println("Nombre: " + jugador.getNombre() + "\n" +
                "Apellido: " + jugador.getApellido() + "\n" +
                "Edad:" + jugador.getNick() + "\n" +
                "Password: " + jugador.getPassword());


    }


    @Override
    public void run() {

        try {

            do {
                // recibo la opcion del cliente
                opcion = flujoEntradaOpcion.readUTF();
                System.out.println("Opción del cliente : " + opcion);

                switch (opcion) {
                    case "a":
                        System.out.println(opcion);

                        // obtengo una nueva pregunta aleatoria
                        int randomIndex = new Random().nextInt(valuesList.size());
                        String randomValue = valuesList.get(randomIndex);

                        // envio una pregunta
                        System.out.println("Envio la pregunta " + randomValue);
                        flujoSalidaObjeto.writeObject(randomValue);

                        // recibo la respuesta de la pregunta enviada
                        String respuestaCliente = flujoEntradaObjecto.readObject().toString();
                        System.out.println("Respuesta del cliente " + respuestaCliente);

                        // compruebo la respuesta del cliente
                        String c = capitalCities.get(randomValue);

                        if (c.equalsIgnoreCase(respuestaCliente)) {

                            flujoSalidaObjeto.writeObject("respuesta correcta ha obtenido un punto");
                            int puntos = jugador.getPuntuacion();
                            jugador.setPuntuacion(puntos + 1);


                        } else {

                            flujoSalidaObjeto.writeObject("respuesta incorrecta");

                        }


                        break;
                    case "b":

                        flujoSalidaObjeto.writeObject(jugador);

                        System.out.println("Adios...");
                        break;
                    default:
                        System.out.println("Introduzaca una opción válida...");


                }

            } while (!opcion.equalsIgnoreCase("b"));


            // cierro flujos y socket
            socket.close();


        } catch (IOException | ClassNotFoundException e) {

            e.getMessage();
        }

    }


}
