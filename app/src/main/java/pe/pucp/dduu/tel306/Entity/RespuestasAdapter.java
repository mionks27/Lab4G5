package pe.pucp.dduu.tel306.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pe.pucp.dduu.tel306.R;

public class RespuestasAdapter extends RecyclerView.Adapter<RespuestasAdapter.EstadisticaViewHolder>{

    private Estadisticas[] estadisticas;
    private Context context;

    public RespuestasAdapter(Estadisticas[] estadisticas, Context context) {
        this.estadisticas = estadisticas;
        this.context = context;
    }

    @NonNull
    @Override
    public EstadisticaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.estadisticarv,parent,false);
        RespuestasAdapter.EstadisticaViewHolder estadisticaViewHolder = new RespuestasAdapter.EstadisticaViewHolder(view);
        return estadisticaViewHolder;
    }

    @Override
    public void onBindViewHolder(EstadisticaViewHolder holder, int position) {
        Estadisticas estadistica = estadisticas[position];
        String alternativa = estadistica.getAnswer().getAnswerText() + " ---->";
        String porcentaje = estadistica.getCount() + " ";
        holder.textView.setText(alternativa);
        holder.textView2.setText(porcentaje);
    }

    @Override
    public int getItemCount() {
        return estadisticas.length;
    }

    public static class EstadisticaViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public TextView textView2;

        public EstadisticaViewHolder( View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.textViewAlternativa);
            this.textView2 = itemView.findViewById(R.id.textViewPorcen);
        }
    }
}
