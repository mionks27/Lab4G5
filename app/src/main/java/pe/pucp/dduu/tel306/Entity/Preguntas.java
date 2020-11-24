package pe.pucp.dduu.tel306.Entity;

import com.google.gson.annotations.SerializedName;

public class Preguntas {
    @SerializedName(value = "id")
    private int id;
    private String questionText;
    private String questionDate;
    private Respuestas[] answers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(String questionDate) {
        this.questionDate = questionDate;
    }

    public Respuestas[] getAnswers() {
        return answers;
    }

    public void setAnswers(Respuestas[] answers) {
        this.answers = answers;
    }
}
