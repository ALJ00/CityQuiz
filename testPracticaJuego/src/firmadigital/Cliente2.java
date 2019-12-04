package firmadigital;

import com.company.Jugador;

import java.io.*;
import java.net.Socket;
import java.security.*;

public class Cliente2 {

    private Socket socket;
    private ObjectInputStream flujoEntradaObjecto;
    private ObjectOutputStream flujoSalidaObjeto;
    private DataOutputStream flujoSalidaOpcion;
    private DataInputStream flujoEntradaOpcion;
    private Jugador jugador;
    private String opcion;
    private BufferedReader lecturaTeclado;

    //atributos para la firma digital
    KeyPairGenerator keyPairGenerator;
    KeyPair keyPair;
    PrivateKey privateKey;
    PublicKey publicKey;


    public Cliente2() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        socket = new Socket("localhost",5000);

        // creo los flujos de salida
        flujoSalidaObjeto = new ObjectOutputStream(socket.getOutputStream());
        flujoSalidaOpcion = new DataOutputStream(socket.getOutputStream());

        // creo los flujos de entrada
        flujoEntradaObjecto = new ObjectInputStream(socket.getInputStream());
        flujoEntradaOpcion = new DataInputStream(socket.getInputStream());

        // creo la lectura por teclado
        lecturaTeclado = new BufferedReader(new InputStreamReader(System.in));

        //doy la bienvenida al jugador
        System.out.println("Bienvenido al juego, ingrese sus datos");
        System.out.println("Nombre: ");
        String name = lecturaTeclado.readLine();
        System.out.println("Apellido: ");
        String ape = lecturaTeclado.readLine();
        System.out.println("Edad: ");
        String ed = lecturaTeclado.readLine();
        System.out.println("Nick: ");
        String nik = lecturaTeclado.readLine();
        System.out.println("Password: ");
        String pass = lecturaTeclado.readLine();

        // creo el jugador y lo mando
        jugador = new Jugador(name, ape, ed, nik, pass);
        flujoSalidaObjeto.writeObject(jugador);

        System.out.println("******************* Verificación y validación de la clave y firma digital **************************\n");
        // recibo la clave publica del servidor
        PublicKey clave = (PublicKey) flujoEntradaObjecto.readObject();
        System.out.println("La clave recibida es: " + clave);

        String mensaje = flujoEntradaObjecto.readObject().toString();
        System.out.println("Reglas del juego: " + mensaje);
        System.out.println("Verifico firma");
        //mensaje="x";

        //verifico la firma
        Signature verificada = Signature.getInstance("SHA1WITHRSA");
        verificada.initVerify(clave);

        verificada.update(mensaje.getBytes());
        byte[] firma = (byte[]) flujoEntradaObjecto.readObject();
        boolean check = verificada.verify(firma);

        //Compruebo la veracidad de la firma
        if (check){
            System.out.println("El mensaje es auténtico");
        }else{
            System.out.println("Intento de falsificación");
        }

        System.out.println("********************************************************************************************\n");


        do{
            System.out.println("Bienvenido \n" +
                    "\t a) Nueva pregunta\n" +
                    "\t b) Salir\n" +
                    "Seleccione una opción:");
            opcion = lecturaTeclado.readLine();

            switch (opcion){
                case "a":
                    System.out.println(opcion);

                    //envio la opcion
                    flujoSalidaOpcion.writeUTF(opcion);

                    // recibo la pregunta
                    String pregunta = flujoEntradaObjecto.readObject().toString();
                    System.out.println("Capital de: "+pregunta);
                    String respuestApregunta = lecturaTeclado.readLine();

                    // envio la respuesta a la preguntarecibida
                    flujoSalidaObjeto.writeObject(respuestApregunta);

                    // recibo la respuesta del servidor
                    System.out.println(flujoEntradaObjecto.readObject().toString());

                    break;
                case "b":

                    // envio la opcion
                    flujoSalidaOpcion.writeUTF(opcion);

                    Jugador jug = new Jugador();
                    jug = (Jugador)flujoEntradaObjecto.readObject();
                    System.out.println(jug.getPuntuacion());

                    System.out.println("Adios...");

                    break;
                default:
                    System.out.println("Introduzaca una opción válida...");


            }

        }while(!opcion.equalsIgnoreCase("b"));


    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new com.company.Cliente();
    }



}
