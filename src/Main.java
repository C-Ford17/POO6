import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Elector elector1 = new Elector(17456789,"Juan Martínez", LocalDate.of(1998,12,16));
        Elector elector2 = new Elector(34567890,"Pedro López", LocalDate.of(1998,3,19));
        Elector elector3 = new Elector(23456812,"Ana Abenza", LocalDate.of(1995,5,1));
        Elector elector4 = new Elector(23754612,"María Gómez", LocalDate.of(1994,9,1));
        ConsultaOrdinaria consultaOrdinaria = new ConsultaOrdinaria("Sobre los exámenes en la universidad",2);
        consultaOrdinaria.agregarPregunta(1,"¿Debemos volver a la convocatoria de septiembre?");
        consultaOrdinaria.agregarPregunta(2,"¿Se deben hacer parciales en todas las cuatrimestrales?");
        ConsultaSelectiva consultaSelectiva = new ConsultaSelectiva("Sobre las fiestas patronales",2,LocalDate.of(1997,12,31), LocalDate.now());
        consultaSelectiva.agregarPregunta(1,"¿Se debe cerrar el centro el día del patrón?");
        consultaSelectiva.agregarPregunta(2,"¿Se deben recuperar las clases que se pierden en las fiestas?");
        consultaSelectiva.preguntasCondicionadas(1);
        List<Consulta> consultas = new ArrayList<>();
        consultas.add(consultaOrdinaria);
        consultas.add(consultaSelectiva);
        for (Consulta consulta:consultas) {
            consulta.agregarElectores(elector1,elector2,elector3,elector4);
            if (consulta instanceof ConsultaOrdinaria) ((ConsultaOrdinaria) consulta).abrirConsulta();
            System.out.println(consulta.isPreparada() + " preparada");
            System.out.println(consulta.consultaActiva() + " activa");
            System.out.println(consulta.electorEnCenso(elector1.getDni()) + " elector en censo");
            System.out.println(consulta.electorEnCenso(elector2.getDni()) + " elector en censo");
            System.out.println(consulta.electorEnCenso(elector3.getDni()) + " elector en censo");
            System.out.println(consulta.electorEnCenso(elector4.getDni()) + " elector en censo");
            System.out.println(consulta.electorVoto(elector1.getDni()) + " elector voto");
            System.out.println(consulta.electorVoto(elector2.getDni()) + " elector voto");
            System.out.println(consulta.electorVoto(elector3.getDni()) + " elector voto");
            System.out.println(consulta.electorVoto(elector4.getDni()) + " elector voto");
            System.out.println(consulta.votar(elector1.getDni(),Respuesta.SI,Respuesta.SI));
            System.out.println(consulta.votar(elector2.getDni()));
            System.out.println(consulta.votar(elector3.getDni(),Respuesta.SI,Respuesta.NO));
            System.out.println(consulta.votar(elector4.getDni(),Respuesta.NO,Respuesta.NO));
            System.out.println(consulta.getTitulo());
            if (consulta instanceof ConsultaSelectiva) System.out.println(((ConsultaSelectiva) consulta).censoAutorizado());
            System.out.println(consulta.getNVotos());
            for (int i = 0; i < consulta.getnPreguntas(); i++) {
                System.out.println(consulta.escrutinioPregunta(i+1));
            }
        }
        List<Consulta> copias = new ArrayList<>();
        copias = Stream.of(consultaOrdinaria,consultaSelectiva).toList();
        System.out.println(copias.toString());
    }
}