import java.util.ArrayList;
import java.util.List;

public class ConsultaOrdinaria extends Consulta{
    private boolean abierta;

    public ConsultaOrdinaria(String titulo, int nPreguntas) {
        super(titulo, nPreguntas);
        this.abierta = false;
    }

    public void abrirConsulta(){
        this.abierta = true;
    }

    public void cerrarConsulta(){
        this.abierta = false;
    }

    @Override
    protected boolean consultaActiva() {
        return this.abierta;
    }

    @Override
    public boolean votar(int dni, List<Respuesta> respuestas) {
        if (electorVoto(dni)) {
            List<Respuesta> respuestas1 = this.getVotos().remove(dni);
            if (!super.votar(dni, respuestas)){
                this.getVotos().put(dni,respuestas1);
                return false;
            }
            return true;
        }
        return super.votar(dni, respuestas);
    }

    @Override
    public String toString() {
        return super.toString() + '{' + "abierta=" + abierta + '}';
    }
}
