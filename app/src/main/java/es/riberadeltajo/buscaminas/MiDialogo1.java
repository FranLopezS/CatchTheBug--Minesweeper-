package es.riberadeltajo.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.zip.Inflater;

public class MiDialogo1 extends DialogFragment implements AdapterView.OnItemSelectedListener {
    Context context;
    OnRespuestaListener miRespuesta;


    public MiDialogo1(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        miRespuesta = (OnRespuestaListener) context;
    }

    private Spinner inflarSpinner(){
        String[] options = {"Easy", "Medium", "Hard"};
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner miSpinner = new Spinner(context);
        miSpinner.setOnItemSelectedListener(this);
        miSpinner.setAdapter(adapter);
        return miSpinner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder miDialog = new AlertDialog.Builder(getContext());
        miDialog.setTitle("Difficult level");
        miDialog.setMessage("Select de the difficult level!");
        miDialog.setView(inflarSpinner()); // rellenar el spinner para que hayan datos.
        return miDialog.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position) {
            case 0:
                miRespuesta.OnRespuesta(0);
                break;
            case 1:
                miRespuesta.OnRespuesta(1);
                break;
            case 2:
                miRespuesta.OnRespuesta(2);
                break;
            default: break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnRespuestaListener {
        void OnRespuesta(int s);
    }
}
