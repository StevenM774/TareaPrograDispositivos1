package com.ucenm.tareaprogradispositivos1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Actividad encargada de mostrar el formulario para crear o actualizar una persona
public class PersonFormActivity extends AppCompatActivity {

    // Campos de texto del formulario
    private EditText etNombres, etApellidos, etDireccion, etTelefono, etFoto;
    // Botón utilizado para guardar o actualizar los datos
    private Button btnSave;
    // Identificador de la persona. Tiene valor -1 cuando se crea un nuevo registro
    private int personId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_form);

        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etDireccion = findViewById(R.id.etDireccion);
        etTelefono = findViewById(R.id.etTelefono);
        etFoto = findViewById(R.id.etFoto);
        btnSave = findViewById(R.id.btnSave);

        if (getIntent().hasExtra("id")) {
            personId = getIntent().getIntExtra("id", -1);
            etNombres.setText(getIntent().getStringExtra("nombres"));
            etApellidos.setText(getIntent().getStringExtra("apellidos"));
            etDireccion.setText(getIntent().getStringExtra("direccion"));
            etTelefono.setText(getIntent().getStringExtra("telefono"));
            etFoto.setText(getIntent().getStringExtra("foto"));
            btnSave.setText("Actualizar");
        }

        btnSave.setOnClickListener(v -> savePerson());
    }

    private void savePerson() {
        String nombres = etNombres.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String foto = etFoto.getText().toString().trim();

        if (nombres.isEmpty() || apellidos.isEmpty()) {
            Toast.makeText(this, "Nombre y Apellido son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Persona persona = new Persona(nombres, apellidos, direccion, telefono, foto);
        ApiService apiService = RetrofitClient.getApiService();
        Call<Persona> call;

        if (personId == -1) {
            call = apiService.createPerson(persona);
        } else {
            persona.setId(personId);
            call = apiService.updatePerson(persona);
        }

        call.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PersonFormActivity.this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(PersonFormActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Toast.makeText(PersonFormActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
