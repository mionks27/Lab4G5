package pe.pucp.dduu.tel306;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import pe.pucp.dduu.tel306.Entity.ListaRespuestasAdapter;
import pe.pucp.dduu.tel306.Entity.Preguntas;

public class QuestionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        obtenerPreguntas();
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if(msg != null){
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        }
    }

    public void obtenerPreguntas(){
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://34.236.191.118:3000/api/v1/questions";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("info",response);
                            Gson gson = new Gson();
                            Preguntas[] listaPregunatas = gson.fromJson(response,Preguntas[].class);
                            ListaRespuestasAdapter listaRespuestasAdapter = new ListaRespuestasAdapter(listaPregunatas,QuestionsActivity.this);
                            View fragment = findViewById(R.id.fragment);
                            RecyclerView recyclerView = fragment.findViewById(R.id.recyclerViewFragment);
                            recyclerView.setAdapter(listaRespuestasAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(QuestionsActivity.this));
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