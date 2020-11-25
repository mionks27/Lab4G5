package pe.pucp.dduu.tel306.Entity;

public class EstadisticasDto {
    private Preguntas question;
    private Estadisticas[] answerstats;

    public Preguntas getQuestion() {
        return question;
    }

    public void setQuestion(Preguntas question) {
        this.question = question;
    }

    public Estadisticas[] getAnswerstats() {
        return answerstats;
    }

    public void setAnswerstats(Estadisticas[] answerstats) {
        this.answerstats = answerstats;
    }
}
