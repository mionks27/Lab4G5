package pe.pucp.dduu.tel306;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //VALIDANDO SI HAY O NO UN ARCHIVO .JSON con datos de usuario en memoria local
        try (FileInputStream fileInputStream = openFileInput("archivoAleerConObjetos.json");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            if (fileInputStream.available() > 0) {  //es decir, si es que hay bytes para leer...
                ///mandar a question activity
                Intent intent = new Intent(MainActivity.this,QuestionsActivity.class);
                startActivity(intent);
            }else{ ///cuando no existe el archivo JSON
                ///abrir login
                agregarLoginFragment();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void agregarLoginFragment(){
        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.principalFragmentContainer,loginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void agregarRegisterFragment(){
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.principalFragmentContainer,registerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ///////////////////////////////




    }
}