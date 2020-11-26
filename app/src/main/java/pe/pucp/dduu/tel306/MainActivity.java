package pe.pucp.dduu.tel306;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try(FileInputStream fileInputStream = openFileInput("archivo.json");
        ) {
            Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
            startActivity(intent);
            Log.d("infoApp","YA ESTAS LOGEADO");
            Log.d("infoApp","ID : " + obtenerDato("id"));

            //ESTO TE PUEDE SERVIR
            //String id = obtenerID();

        } catch (IOException e) {
            e.printStackTrace();
            agregarLoginFragment();
        }
    }
    public void volvemosAlLogin(View view){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //AHORA BUSCAMOS EL FRAGMENTO SI REALMENTE EXISTE
        RegisterFragment registerFragment = (RegisterFragment) supportFragmentManager.findFragmentById(R.id.principalFragmentContainer);
        //SI LO ENCUENTRA EL blankFragment ES DIFERENTE DE NULO, ES DECIR QUE EL FRAGMENTO YA ESTA AHI, POR LO TANTO HAY QUE BORRARLO
        if (registerFragment != null){
            //QUE BORRE EL FRAGMENTO
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.remove(registerFragment);
            fragmentTransaction.commit();
        }
        agregarLoginFragment();
    }
    public void vamosAlRegister(View view){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //AHORA BUSCAMOS EL FRAGMENTO SI REALMENTE EXISTE
        LoginFragment loginFragment = (LoginFragment) supportFragmentManager.findFragmentById(R.id.principalFragmentContainer);
        //SI LO ENCUENTRA EL blankFragment ES DIFERENTE DE NULO, ES DECIR QUE EL FRAGMENTO YA ESTA AHI, POR LO TANTO HAY QUE BORRARLO
        if (loginFragment != null){
            //QUE BORRE EL FRAGMENTO
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.remove(loginFragment);
            fragmentTransaction.commit();
        }
        agregarRegisterFragment();
    }
    public void consultaLogin(View view){
        EditText editText1 = findViewById(R.id.editTextEmailLogin);
        String email = editText1.getText().toString();
        EditText editText2 = findViewById(R.id.editTextPasswordLogin);
        String password = editText2.getText().toString();
        //String email = "todoMeLlegaAlPNCHO@pucp.pe";
        //String password = "321";
        Log.d("infoApp","infoApp : " + email);
        Log.d("infoApp","infoApp : " + password);
        String data = "{\"email\": \""+ email +"\",\"password\": \"" + password + "\"}";
        Log.d("infoApp"," JSON LOGIN : " + data);
        SubmitLogin(data);

        FragmentManager supportFragmentManager = getSupportFragmentManager();

        //AHORA BUSCAMOS EL FRAGMENTO SI REALMENTE EXISTE
        LoginFragment loginFragment = (LoginFragment) supportFragmentManager.findFragmentById(R.id.principalFragmentContainer);

        //SI LO ENCUENTRA EL blankFragment ES DIFERENTE DE NULO, ES DECIR QUE EL FRAGMENTO YA ESTA AHI, POR LO TANTO HAY QUE BORRARLO

        if (loginFragment != null){
            //QUE BORRE EL FRAGMENTO
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.remove(loginFragment);
            fragmentTransaction.commit();
        }
        Log.d("infoApp","YA ESTAS LOGEADO");
        Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
        startActivity(intent);//ESTO TE PUEDE SERVIR
        //String id = obtenerID();
        //Log.d("infoApp","ID : " + obtenerID("name"));

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void registrarUsuario(View view){
        EditText editText1 = findViewById(R.id.editTextNombreUsuario);
        String name = editText1.getText().toString();
        EditText editText2 = findViewById(R.id.editTextEmail);
        String email = editText2.getText().toString();
        EditText editText3 = findViewById(R.id.editTextContrasena);
        String password = editText3.getText().toString();
        //String email = "todoMeLlegaAlPNCHO@pucp.pe";
        //String password = "321";
        //Log.d("infoApp","infoApp : " + email);
        //Log.d("infoApp","infoApp : " + password);
        //String data = "{\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        //Log.d("infoApp"," JSON LOGIN : " + data);
        SubmitRegister(name,email,password);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CON ESTO SE MUESTRA EL FRAGMENT DE LOGIN
    public void agregarLoginFragment(){
        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.principalFragmentContainer,loginFragment);
        fragmentTransaction.addToBackStack(null);
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
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //JSON
    private RequestQueue requestQueueLogin;
    private void SubmitLogin(String data)
    {
        final String savedata= data;
        String URL="http://34.236.191.118:3000/api/v1/users/login";

        requestQueueLogin = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    String mensaje = "Usuario Logeado Correctamente";
                    Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

                    try(FileOutputStream fileOutputStream = openFileOutput("archivo.json", Context.MODE_PRIVATE);
                        FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());
                    ){
                        fileWriter.write(objres.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
        requestQueueLogin.add(stringRequest);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private RequestQueue requestQueueRegister;
    private void SubmitRegister(String name, String email, String password)
    {
        String data = "{\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        final String savedata= data;
        String URL="http://34.236.191.118:3000/api/v1/users/new";

        requestQueueRegister = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("true")){
                    Toast.makeText(getApplicationContext(),"USUARIO REGISTRADO CORRECTAMENTE",Toast.LENGTH_LONG).show();
                    Log.d("infoApp","YA ESTAS LOGEADO");
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    String data = "{\"email\": \""+ email +"\",\"password\": \"" + password + "\"}";
                    Log.d("infoApp"," JSON LOGIN : " + data);
                    SubmitLogin(data);
                    FragmentManager supportFragmentManager = getSupportFragmentManager();
                    //AHORA BUSCAMOS EL FRAGMENTO SI REALMENTE EXISTE
                    RegisterFragment registerFragment = (RegisterFragment) supportFragmentManager.findFragmentById(R.id.principalFragmentContainer);
                    //SI LO ENCUENTRA EL blankFragment ES DIFERENTE DE NULO, ES DECIR QUE EL FRAGMENTO YA ESTA AHI, POR LO TANTO HAY QUE BORRARLO
                    if (registerFragment != null){
                        //QUE BORRE EL FRAGMENTO
                        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.remove(registerFragment);
                        fragmentTransaction.commit();
                    }
                    Log.d("infoApp","YA ESTAS LOGEADO");
                    ///////////////////
                    //GAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                    Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                    startActivity(intent);
                }else if(response.equalsIgnoreCase("false")){
                    Toast.makeText(getApplicationContext(),"Este Email NO ESTÁ DISPONIBLE",Toast.LENGTH_LONG).show();
                }
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
        requestQueueRegister.add(stringRequest);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String obtenerDato(String nom){
        String id = null;
        try(FileInputStream fileInputStream = openFileInput("archivo.json");
            FileReader fileReader = new FileReader(fileInputStream.getFD());// FileReader lee caracter, no es muy practico asi q lo complmentamos con Buffer
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line = null;
            while ((line = bufferedReader.readLine())!=null){
                try {
                    JSONObject objres = new JSONObject(line);
                    Log.d("infoLine","ID : " + objres.get("id").toString());
                    Log.d("infoLine","NAME : " + objres.get("name").toString());
                    Log.d("infoLine","EMAIL : " + objres.get("email").toString());
                    Log.d("infoLine","PASSWORD : " + objres.get("password").toString());
                    Log.d("infoLine","TOKEN : " + objres.get("token").toString());
                    Log.d("infoLine","CREATEDAT : " + objres.get("createdAt").toString());
                    Log.d("infoLine","UPDATEDAT : " + objres.get("updatedAt").toString());
                    Log.d("infoLine","QUESTIONS : " + objres.get("questions").toString());

                    //String nom = "name";

                    id = objres.get(nom).toString();
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
}