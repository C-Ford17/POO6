import java.time.LocalDate;

public class Elector implements  Cloneable {
    private final int dni;
    private final String nombre;
    private final LocalDate fechaNacimiento;


    public Elector(int dni, String nombre, LocalDate fechaNacimiento) {
        this.dni = dni;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Elector{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }


    @Override
    public Elector clone() throws CloneNotSupportedException {
        Elector clone = (Elector) super.clone();
        return clone;
    }
}
