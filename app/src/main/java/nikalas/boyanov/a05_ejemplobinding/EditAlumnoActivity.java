package nikalas.boyanov.a05_ejemplobinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import nikalas.boyanov.a05_ejemplobinding.databinding.ActivityEditAlumnoBinding;
import nikalas.boyanov.a05_ejemplobinding.modelos.Alumno;

public class EditAlumnoActivity extends AppCompatActivity {
    private ActivityEditAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_alumno);
        binding = ActivityEditAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Alumno alumno = (Alumno) bundle.getSerializable("ALUMNO");
        //Toast.makeText(this, alumno.toString(), Toast.LENGTH_SHORT).show();

        rellenarAlumno(alumno);

        binding.btnActualizarEditAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recoger la informacion para crear alumno
                Alumno alumno = crearAlumno();


                if (alumno != null) {
                    //Hacer el intent
                    Intent intent = new Intent();

                    //poner el bumdle
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ALUMNO", alumno);
                    intent.putExtras(bundle);
                    //comunicar el resultado correcto
                    setResult(RESULT_OK, intent);
                    //terminar
                    finish();
                }else {
                    Toast.makeText(EditAlumnoActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnBorrarEditAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void rellenarAlumno(Alumno alumno) {
        binding.txtNombreEditAlumno.setText(alumno.getNombre());
        binding.txtApellidosEditAlumno.setText(alumno.getApellidos());

        int posicion = 0;
        switch (alumno.getCiclo()){
            case "SMR": posicion = 1;
            break;
            case "DAM": posicion = 2;
            break;
            case "DAW": posicion = 3;
            break;
            case "3D": posicion = 4;
            break;
            case "MARKETING": posicion = 5;
            break;
        }
        binding.spCiclosEditAlumno.setSelection(posicion);

        switch (alumno.getGrupo()){
            case 'A': binding.rbGrupoAEditAlumno.setChecked(true);
            break;
            case 'B': binding.rbGrupoBEditAlumno.setChecked(true);
            break;
            case 'C': binding.rbGrupoCEditAlumno.setChecked(true);
        }
    }

    private Alumno crearAlumno() {
        if (binding.txtNombreEditAlumno.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtApellidosEditAlumno.getText().toString().isEmpty()){
            return null;
        }
        if (binding.spCiclosEditAlumno.getSelectedItemPosition() == 0){
            return null;
        }
        if (!binding.rbGrupoAEditAlumno.isChecked()&&!binding.rbGrupoBEditAlumno.isChecked()&&!binding.rbGrupoCEditAlumno.isChecked()){
            return null;
        }
        RadioButton rb = findViewById(binding.rgGrupoEditAlumno.getCheckedRadioButtonId());
        char grupo = rb.getText().toString().split(" ")[1].charAt(0);

        Alumno alumno = new Alumno(
                binding.txtNombreEditAlumno.getText().toString(),
                binding.txtApellidosEditAlumno.getText().toString(),
                binding.spCiclosEditAlumno.getSelectedItem().toString(),
                grupo
        );

        return alumno;
    }
}