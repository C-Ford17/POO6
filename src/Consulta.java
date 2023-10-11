import java.util.*;

public abstract class Consulta implements  Cloneable{
    private String titulo;
    private List<Elector> censo;
    private int nPreguntas;
    private String[] preguntas;
    private boolean preparada;
    private Map<Integer,List<Respuesta>> votos;

    public int getNVotos(){
        return this.votos.size();
    }

    public int getNElectores(){
        return this.censo.size();
    }

    public double indiceParticipacion(){
        return (double) this.getNVotos() /getNElectores();
    }

    public int getnPreguntas() {
        return nPreguntas;
    }

    public String[] getPreguntas() {
        return preguntas;
    }

    public Consulta(String titulo, int nPreguntas) {
        this.titulo = titulo;
        this.nPreguntas = nPreguntas;
        this.censo = new ArrayList<>();
        this.preparada = false;
        this.preguntas = new String[nPreguntas];
        this.votos = new HashMap<>();
    }

    public boolean isPreparada() {
        if (this.preguntas.length == nPreguntas) return true;
        return false;
    }

    public String getTitulo() {
        return titulo;
    }

    public void agregarElectores(Elector... electores){
        this.censo.addAll(Arrays.asList(electores));
    }

    public boolean agregarPregunta(int n, String pregunta){
        if (n > this.nPreguntas) return false;
        this.preguntas[n-1] = pregunta;
        return true;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "titulo='" + titulo + '\'' +
                ", censo=" + censo +
                ", nPreguntas=" + nPreguntas +
                ", preguntas=" + Arrays.toString(preguntas) +
                ", preparada=" + preparada +
                ", votos=" + votos +
                '}';
    }

    public boolean eliminarPregunta(int n){
        if (this.preguntas[n-1] == null) return false;
        this.preguntas[n-1] = null;
        return true;
    }

    public Elector getElectorDni(int dni){
        return this.censo.stream().filter(elector -> elector.getDni() == dni)
                .findFirst().orElse(null);
    }

    public Map<Integer, List<Respuesta>> getVotos() {
        return votos;
    }

    public List<Elector> getCenso() {
        return censo;
    }

    public boolean electorEnCenso(int dni){
        return this.censo.stream().anyMatch(elector -> elector.getDni() == dni);
    }

    public boolean electorVoto(int dni){
        return this.votos.containsKey(dni);
    }

    public Map<Respuesta,Integer> escrutinioPregunta(int n){
        if (n > nPreguntas) return null;
        Map<Respuesta, Integer> nVotos = new HashMap<>();
        nVotos.put(Respuesta.SI,0);
        nVotos.put(Respuesta.NO,0);
        nVotos.put(Respuesta.EN_BLANCO,0);
        for (List<Respuesta> respuestas:this.votos.values()) {
            Respuesta voto = respuestas.get(n-1);
            nVotos.put(voto, nVotos.get(voto)+1);
        }
        return nVotos;
    }

    public boolean votar(int dni, List<Respuesta> respuestas){
        if (isPreparada() && consultaActiva() && electorEnCenso(dni) &&
                !electorVoto(dni) && respuestas.size() == nPreguntas){
            this.votos.put(dni, respuestas);
            return true;
        }
        return false;
    }

    public boolean votar(int dni, Respuesta... respuestas){
        return this.votar(dni, List.of(respuestas));
    }

    public boolean votar(int dni) {
        return this.votar(dni, Collections.nCopies(nPreguntas,Respuesta.EN_BLANCO));
    }

    protected abstract boolean consultaActiva();


    @Override
    public Consulta clone() throws CloneNotSupportedException {
        Consulta clone = (Consulta) super.clone();
        clone.censo.clear();
        censo.stream().forEach(elector -> {
            try {
                clone.censo.add(elector.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });
        clone.preguntas.clone();
        clone.votos.clear();
        return clone;
    }
}
