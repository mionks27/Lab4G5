package pe.pucp.dduu.tel306.Entity;

import androidx.annotation.NonNull;

public class Respuestas {
    private int id;
    private String answerText;

    @NonNull
    @Override
    public String toString() {
        return answerText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
