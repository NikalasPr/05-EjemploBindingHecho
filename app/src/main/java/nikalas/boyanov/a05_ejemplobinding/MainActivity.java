package nikalas.boyanov.a05_ejemplobinding;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import nikalas.boyanov.a05_ejemplobinding.databinding.ActivityMainBinding;
import nikalas.boyanov.a05_ejemplobinding.modelos.Alumno;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addAlumnoLauncher;

    private ArrayList<Alumno> listaAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        listaAlumnos = new ArrayList<>();

        inicializarLauncher();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrir la actividad add alumno
                addAlumnoLauncher.launch(new Intent(MainActivity.this, AddAlumnoActivity.class));
            }
        });
    }

    private void inicializarLauncher() {
        addAlumnoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData() != null && result.getData().getExtras()!= null){
                                Alumno alumno = (Alumno) result.getData().getExtras().getSerializable("ALUMNO");
                                //Toast.makeText(MainActivity.this, alumno.toString(), Toast.LENGTH_SHORT).show();
                                listaAlumnos.add(alumno);
                                mostrarAlumnos();
                                //falta mostrar la informacion
                                //1. Elemento para mostrar la info en la vista --> TextView
                                //2. Conjunto de datos para mostrar --> Lista de alumnos
                                //3. Contenedor donde mostrar cada elemento --> Scroll
                                //4. La logica para mostrar los elementos de la lista
                            }else {
                                Toast.makeText(MainActivity.this, "NO HAY INFORMACION", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "ACCION CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


        );
    }

    private void mostrarAlumnos() {
        binding.contentMain.contenedorMain.removeAllViews();

        for (Alumno alumno: listaAlumnos){
            /*
            TextView tvAlumno = new TextView(MainActivity.this);
            tvAlumno.setText(alumno.toString());
            binding.contentMain.contenedorMain.addView(tvAlumno);
             */

            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

            View alumnoView = layoutInflater.inflate(R.layout.alumno_fila_view, null);
            TextView lbNombre = alumnoView.findViewById(R.id.lbNombreAlumnoView);
            TextView lbApellidos = alumnoView.findViewById(R.id.lbApellidosAlumnoView);
            TextView lbCiclos = alumnoView.findViewById(R.id.lbCicloAlumnoView);
            TextView lbGrupo = alumnoView.findViewById(R.id.lbGrupoAlumnoView);

            lbNombre.setText(alumno.getNombre());
            lbApellidos.setText(alumno.getApellidos());
            lbCiclos.setText(alumno.getCiclo());
            lbGrupo.setText(String.valueOf(alumno.getGrupo()));

            binding.contentMain.contenedorMain.addView(alumnoView);
        }
    }
}