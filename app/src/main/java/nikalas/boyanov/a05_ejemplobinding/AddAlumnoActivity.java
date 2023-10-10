package nikalas.boyanov.a05_ejemplobinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import nikalas.boyanov.a05_ejemplobinding.databinding.ActivityAddAlumnoBinding;
import nikalas.boyanov.a05_ejemplobinding.modelos.Alumno;

public class AddAlumnoActivity extends AppCompatActivity {
    private ActivityAddAlumnoBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //se quita el contentview
        binding = ActivityAddAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddAlumno.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(AddAlumnoActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
    }

    private Alumno crearAlumno() {
        if (binding.txtNombreAddAlumno.getText().toString().isEmpty()){
            return null;
        }
        if (binding.txtApellidosAddAlumno.getText().toString().isEmpty()){
            return null;
        }
        if (binding.spCiclosAddAlumno.getSelectedItemPosition() == 0){
            return null;
        }
        if (!binding.rbGrupoAAddAlumno.isChecked()&&!binding.rbGrupoBAddAlumno.isChecked()&&!binding.rbGrupoCAddAlumno.isChecked()){
            return null;
        }
        RadioButton rb = findViewById(binding.rgGrupoAddAlumno.getCheckedRadioButtonId());
        char grupo = rb.getText().toString().split(" ")[1].charAt(0);
        
        Alumno alumno = new Alumno(
                binding.txtNombreAddAlumno.getText().toString(),
                binding.txtApellidosAddAlumno.getText().toString(),
                binding.spCiclosAddAlumno.getSelectedItem().toString(),
                grupo
        );
        
        return alumno;
    }
}