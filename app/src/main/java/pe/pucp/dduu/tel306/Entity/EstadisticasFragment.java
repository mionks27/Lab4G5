package pe.pucp.dduu.tel306.Entity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.pucp.dduu.tel306.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstadisticasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstadisticasFragment extends Fragment {


    public EstadisticasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EstadisticasFragment newInstance() {
        EstadisticasFragment fragment = new EstadisticasFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estadisticas, container, false);
    }
}