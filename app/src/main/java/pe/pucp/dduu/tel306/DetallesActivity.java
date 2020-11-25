package pe.pucp.dduu.tel306;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import pe.pucp.dduu.tel306.Entity.EstadisticasDto;
import pe.pucp.dduu.tel306.Entity.RespuestasAdapter;

public class DetallesActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Intent intent = getIntent();
        String id = String.valueOf(intent.getIntExtra("id",0));
        Log.d("aaaaaaaaaaaaa",id);
        TextView textView = findViewById(R.id.textViewboolean);
        textView.setVisibility(View.GONE);
        obtenerEstadistica(id);
        saberSiRespondioPregunta(id);

        boolean res = Boolean.parseBoolean(textView.getText().toString());
        View altenativa = findViewById(R.id.fragmentAlternativa);
        altenativa.setVisibility(View.GONE);
        Log.d("aaaaaaaaaaaa", String.valueOf(res));
//        if(res){
//
//        }else{
//        }
    }


    public void saberSiRespondioPregunta(String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://34.236.191.118:3000/api/v1/answers/detail?questionid="+ id+"&userid=1" ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("info",response);
                        Gson gson = new Gson();
                        boolean aaa = gson.fromJson(response,boolean.class);
                        TextView textView = findViewById(R.id.textViewboolean);
                        textView.setText(String.valueOf(aaa));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        requestQueue.add(stringRequest);
    }



    public void obtenerEstadistica(String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://34.236.191.118:3000/api/v1/answers/stats?questionid="+id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("info",response);
                        Gson gson = new Gson();
                        EstadisticasDto estadisticasDto = gson.fromJson(response,EstadisticasDto.class);
                        RespuestasAdapter respuestasAdapter = new RespuestasAdapter(estadisticasDto.getAnswerstats(),DetallesActivity.this);
                        View fragment = findViewById(R.id.fragmentEstadistica);
                        RecyclerView recyclerView = fragment.findViewById(R.id.recyclerViewEstadisticas);
                        recyclerView.setAdapter(respuestasAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DetallesActivity.this));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(stringRequest);
    }

}