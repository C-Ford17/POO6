import java.time.LocalDate;

public record Elector(int dni, String nombre, LocalDate fechaNacimiento) implements Cloneable {

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
        return (Elector) super.clone();
    }
}
