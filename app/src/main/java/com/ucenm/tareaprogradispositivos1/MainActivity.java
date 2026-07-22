package com.ucenm.tareaprogradispositivos1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PersonAdapter.OnPersonClickListener {

    private RecyclerView rvPersons;
    private PersonAdapter adapter;
    private List<Persona> personList = new ArrayList<>();
    private static final int REQUEST_CODE_FORM = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPersons = findViewById(R.id.rvPersons);
        rvPersons.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonAdapter(personList, this);
        rvPersons.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PersonFormActivity.class);
            startActivityForResult(intent, REQUEST_CODE_FORM);
        });

        loadPersons();
    }

    private void loadPersons() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getPersons().enqueue(new Callback<List<Persona>>() {
            @Override
            public void onResponse(Call<List<Persona>> call, Response<List<Persona>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    personList = response.body();
                    adapter.updateData(personList);
                } else {
                    Toast.makeText(MainActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Persona>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEditClick(Persona persona) {
        Intent intent = new Intent(this, PersonFormActivity.class);
        intent.putExtra("id", persona.getId());
        intent.putExtra("nombres", persona.getNombres());
        intent.putExtra("apellidos", persona.getApellidos());
        intent.putExtra("direccion", persona.getDireccion());
        intent.putExtra("telefono", persona.getTelefono());
        intent.putExtra("foto", persona.getFoto());
        startActivityForResult(intent, REQUEST_CODE_FORM);
    }

    @Override
    public void onDeleteClick(Persona persona) {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.deletePerson(persona).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                    loadPersons();
                } else {
                    Toast.makeText(MainActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FORM && resultCode == RESULT_OK) {
            loadPersons();
        }
    }
}
