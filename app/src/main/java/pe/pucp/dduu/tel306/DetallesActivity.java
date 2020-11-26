package pe.pucp.dduu.tel306;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import pe.pucp.dduu.tel306.Entity.EstadisticasDto;
import pe.pucp.dduu.tel306.Entity.Preguntas;
import pe.pucp.dduu.tel306.Entity.Respuestas;
import pe.pucp.dduu.tel306.Entity.RespuestasAdapter;

public class DetallesActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Intent intent = getIntent();
        String id = String.valueOf(intent.getIntExtra("id",0));
        String idUser = obtenerID();
        saberSiRespondioPregunta(id,idUser);

    }


    public void saberSiRespondioPregunta(String idQuestion, String idUser){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://34.236.191.118:3000/api/v1/answers/detail?questionid="+ idQuestion+"&userid="+idUser ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("info",response);
                        Gson gson = new Gson();
                        boolean aaa = gson.fromJson(response,boolean.class);
                        Log.d("aaaaaaaaa", aaa + "    afaffsfsggsgsdggs");
                        if(aaa){
                            View fragmentAlternative = findViewById(R.id.fragmentAlternativa);
                            fragmentAlternative.setVisibility(View.GONE);
                            obtenerEstadistica(idQuestion);
                        }else{
                            View fragmentEstadistica = findViewById(R.id.fragmentEstadistica);
                            fragmentEstadistica.setVisibility(View.GONE);
                            obtenerAlternativas(idQuestion);

                        }
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

    public String obtenerID(){
        String id = null;
        try(FileInputStream fileInputStream = openFileInput("archivo.json");
            FileReader fileReader = new FileReader(fileInputStream.getFD());// FileReader lee caracter, no es muy practico asi q lo complmentamos con Buffer
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line = null;
            while ((line = bufferedReader.readLine())!=null){
                try {
                    JSONObject objres = new JSONObject(line);
                    id = objres.get("id").toString();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
                    Log.d("infoLine","F");
                    Log.d("infoApp",line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("infoLine","NO EXISTE EL ARCHIVO");
        }
        return id;
    }

    public void obtenerAlternativas(String id){
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://34.236.191.118:3000/api/v1/questions/"+ id;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("info",response);

                            Gson gson = new Gson();
                            Preguntas preguntas = gson.fromJson(response, Preguntas.class);
                            View alterFragment = findViewById(R.id.fragmentAlternativa);
                            TextView textView = alterFragment.findViewById(R.id.textViewAlt);
                            textView.setText(preguntas.getQuestionText());
                            ArrayAdapter<Respuestas> adapter = new ArrayAdapter<>(DetallesActivity.this, android.R.layout.simple_spinner_dropdown_item,
                                    preguntas.getAnswers());
                            Spinner spinner = alterFragment.findViewById(R.id.spinnerAlternativas);
                            spinner.setAdapter(adapter);

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