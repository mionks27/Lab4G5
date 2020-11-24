package pe.pucp.dduu.tel306;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View loginFragment = findViewById(R.id.principalFragmentContainer);

        /*
        Button buttonLogin = loginFragment.findViewById(R.id.button2);
        if(buttonLogin != null){
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = loginFragment.findViewById(R.id.editTextTextPersonName);
                    String email = editText.getText().toString();
                    EditText editText1 = loginFragment.findViewById(R.id.editTextTextPersonName2);
                    String password = editText1.getText().toString();

                    String data = "{\"email\":" + email + ",\"password\":" + password + "\"}";
                    Log.d("infoApp",data);
                    Submit(data);
                }
            });
        }
        */
    }
    //CON ESTO SE MUESTRA EL FRAGMENT DE LOGIN
    public void agregarLoginFragment(){
        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.principalFragmentContainer,loginFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CON ESTO SE MUESTRA EL FRAGMENT DE REGISTRO
    public void agregarRegisterFragment(){
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.principalFragmentContainer,registerFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ///////////////////////////////




    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //JSON
    private RequestQueue requestQueue;
    private void Submit(String data)
    {
        final String savedata= data;
        String URL="http://34.236.191.118:3000/api/v1/users/login";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),objres.toString(),Toast.LENGTH_LONG).show();
                    Log.d("infoApp","consulta CORRECTA");
                    Log.d("infoApp","ID : " + objres.get("id").toString());
                    Log.d("infoApp","NAME : " + objres.get("name").toString());
                    Log.d("infoApp","EMAIL : " + objres.get("email").toString());
                    Log.d("infoApp","PASSWORD : " + objres.get("password").toString());

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
                }
                //Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("infoApp","LA KGASTE");
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.d("Unsupported Encoding while trying to get the bytes", data);
                    Log.d("infoApp","ABC");
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}