package Model;

public class Horario {
    private Aula aula;
    private String dia;
    private String horas;


    public Horario(Aula aula, String dia, String horas) {
        this.aula = aula;
        this.dia = dia;
        this.horas = horas;
    }
}
