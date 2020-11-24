package pe.pucp.dduu.tel306;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlternativasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlternativasFragment extends Fragment {


    public AlternativasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AlternativasFragment newInstance() {
        AlternativasFragment fragment = new AlternativasFragment();
        Bundle args = new Bundle();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alternativas, container, false);
    }
}