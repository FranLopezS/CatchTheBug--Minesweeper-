package es.riberadeltajo.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MiDialogoSkins extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private Context context;
    OnRespuestaListener respuesta;
    private int[] imgs = {R.drawable.mar21, R.drawable.mar22};

    public MiDialogoSkins(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        respuesta = (OnRespuestaListener) getContext();
    }

    public Spinner inflarSpinner() {
        Spinner sp = new Spinner(context);
        MiAdaptador adapter = new MiAdaptador(context, R.layout.spinnerfotos, new String[]{"hola", "jaja"});
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        return sp;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder d = new AlertDialog.Builder(getContext());
        d.setTitle("Skin chooser");
        d.setMessage("Choose a skin!");
        d.setView(inflarSpinner());
        return d.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position) {
            case 0:
                respuesta.OnRespuesta(10);
                break;
            case 1:
                respuesta.OnRespuesta(11);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnRespuestaListener {
        void OnRespuesta(int s);
    }

    public class MiAdaptador extends ArrayAdapter<String> {
        public MiAdaptador(@NonNull Context context, int resource, @NonNull String[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return miFilaPersonalizada(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return miFilaPersonalizada(position, convertView, parent);
        }

        public View miFilaPersonalizada(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View vista = inflater.inflate(R.layout.spinnerfotos, parent, false);

            ImageView foto = vista.findViewById(R.id.fotoCambiar);
            foto.setImageDrawable(getResources().getDrawable(imgs[position]));

            return vista;
        }

    }

}
