package pe.pucp.dduu.tel306;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class EstadisticasFragment extends Fragment {


    public EstadisticasFragment() {
        // Required empty public constructor
    }

    public static EstadisticasFragment newInstance() {
        EstadisticasFragment fragment = new EstadisticasFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estadisticas, container, false);
    }
}