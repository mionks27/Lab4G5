package pe.pucp.dduu.tel306.Entity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import pe.pucp.dduu.tel306.DetallesActivity;
import pe.pucp.dduu.tel306.QuestionsActivity;
import pe.pucp.dduu.tel306.R;

public class ListaRespuestasAdapter extends RecyclerView.Adapter<ListaRespuestasAdapter.PreguntasViewHolder> {

    private Preguntas[] listaPreguntas;
    private Context context;

    public ListaRespuestasAdapter(Preguntas[] listaPreguntas, Context context) {
        this.listaPreguntas = listaPreguntas;
        this.context = context;
    }

    @NonNull
    @Override
    public PreguntasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.questionrv,parent,false);
        PreguntasViewHolder preguntasViewHolder =new PreguntasViewHolder(view);
        return preguntasViewHolder;
    }

    @Override
    public void onBindViewHolder(PreguntasViewHolder holder, int position) {
        Preguntas preguntas =listaPreguntas[position];
        String data = preguntas.getQuestionText();
        holder.button.setText(data);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetallesActivity.class);
                intent.putExtra("id",preguntas.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaPreguntas.length;
    }

    public static class PreguntasViewHolder extends RecyclerView.ViewHolder{

        public Button button;

        public PreguntasViewHolder( View itemView) {
            super(itemView);
            this.button = itemView.findViewById(R.id.button);
        }
    }


}
