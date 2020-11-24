package pe.pucp.dduu.tel306;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import pe.pucp.dduu.tel306.Entity.EstadisticasFragment;
import pe.pucp.dduu.tel306.Entity.ListaRespuestasAdapter;
import pe.pucp.dduu.tel306.Entity.Preguntas;

public class DetallesActivity extends AppCompatActivity {

    boolean res = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        obtenerPreguntas(id);
        if(res){
            agregarEstadísticasFragment();
            FrameLayout layoutEstadística= findViewById(R.id.fragmentLayout);
        }else{
            agregarAlternativasFragment();
           FrameLayout layoutAlternativa = findViewById(R.id.fragmentLayout);
//           layoutAlternativa.findViewById();
        }
    }


    public void obtenerPreguntas(String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://34.236.191.118:3000/api/v1/answers/detail?questionid="+ id+"&userid=1" ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("info",response);
                        Gson gson = new Gson();
                        res = gson.fromJson(response,boolean.class);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        requestQueue.add(stringRequest);
    }

    public void agregarAlternativasFragment(){
        AlternativasFragment alternativasFragment = AlternativasFragment.newInstance();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayout,alternativasFragment);
        fragmentTransaction.commit();
    }

    public void agregarEstadísticasFragment(){
        EstadisticasFragment estadisticasFragment = EstadisticasFragment.newInstance();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayout,estadisticasFragment);
        fragmentTransaction.commit();
    }

}