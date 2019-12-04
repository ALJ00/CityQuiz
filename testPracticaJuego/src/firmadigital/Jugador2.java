package firmadigital;

import java.io.Serializable;

public class Jugador2 implements Serializable {

    private String nombre;
    private String apellido;
    private String edad;
    private String nick;
    private String password;
    private int puntuacion;

    public Jugador2(String nombre, String apellido, String edad, String nick, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nick = nick;
        this.password = password;
    }

    public Jugador2() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
