import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsultaSelectiva extends Consulta{
    private LocalDate fechaUmbral;
    private LocalDate fechaCelebracion;
    private List<Integer> preguntasCondicionadas;

    public ConsultaSelectiva(String titulo, int nPreguntas, LocalDate fechaUmbral, LocalDate fechaCelebracion) {
        super(titulo, nPreguntas);
        this.fechaUmbral = fechaUmbral;
        this.fechaCelebracion = fechaCelebracion;
        this.preguntasCondicionadas = new ArrayList<>();
    }

    public ConsultaSelectiva(String titulo, int nPreguntas, LocalDate fechaUmbral) {
        this(titulo,nPreguntas,fechaUmbral,LocalDate.now().plusDays(1));
    }

    public List<Elector> censoAutorizado() {
        return getCenso().stream().filter(elector -> elector.getFechaNacimiento().isBefore(fechaUmbral))
                .collect(Collectors.toList());
    }

    @Override
    protected boolean consultaActiva() {
        return LocalDate.now().isEqual(fechaCelebracion);
    }

    public boolean cambiarFechaCelebracion(LocalDate fecha){
        if (fecha.isAfter(fechaCelebracion)){
            fechaCelebracion = fecha;
            return true;
        }
        return false;
    }
    /*
    public void numerosCondicionados(int... numeros) {
        for (int i = 0; i < getPreguntas().length; i++) {
            if (getPreguntas()[i] != null){
                for (int num:numeros) {
                    if (num-1 == i) preguntasCondicionadas.add(num);
                }
            }
        }
    }*/

    public void preguntasCondicionadas(int... numeros) {
        preguntasCondicionadas.addAll(IntStream.of(numeros)
                .filter(num -> num > 0 && num <= getnPreguntas())
                .map(num -> num)
                .filter(i -> getPreguntas()[i] != null)
                .boxed()
                .toList());
    }

    @Override
    public boolean votar(int dni, List<Respuesta> respuestas) {
        if (getElectorDni(dni).getFechaNacimiento().isAfter(fechaUmbral)){
            boolean comprobar = preguntasCondicionadas.stream()
                    .map(respuestas::get)
                    .allMatch(Respuesta.EN_BLANCO::equals);
            if (!comprobar) return false;
        }
        return super.votar(dni,respuestas);
    }

    @Override
    public boolean votar(int dni, Respuesta... respuestas) {
        return this.votar(dni, List.of(respuestas));
    }

    @Override
    public String toString() {
        return super.toString() + '{' +
                "fechaUmbral=" + fechaUmbral +
                ", fechaCelebracion=" + fechaCelebracion +
                ", preguntasCondicionadas=" + preguntasCondicionadas +
                '}';
    }
}
