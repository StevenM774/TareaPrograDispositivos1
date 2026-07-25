package com.ucenm.tareaprogradispositivos1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<Persona> persons;
    private OnPersonClickListener listener;

    public interface OnPersonClickListener {
        void onEditClick(Persona persona);
        void onDeleteClick(Persona persona);
    }

    public PersonAdapter(List<Persona> persons, OnPersonClickListener listener) {
        this.persons = persons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Persona persona = persons.get(position);
        holder.tvFullName.setText(persona.getNombres() + " " + persona.getApellidos());
        holder.tvPhone.setText(persona.getTelefono());
        holder.tvAddress.setText(persona.getDireccion());

       // Por simplicidad, no estamos cargando la imagen aquí.
       // En una aplicación real, se utilizaría Glide o Picasso para cargar persona.getFoto() en holder.ivPersonPhoto.

        holder.itemView.setOnClickListener(v -> listener.onEditClick(persona));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(persona));
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public void updateData(List<Persona> newPersons) {
        this.persons = newPersons;
        notifyDataSetChanged();
    }

    public void removePerson(Persona persona) {
        int position = persons.indexOf(persona);
        if (position != -1) {
            persons.remove(position);
            notifyItemRemoved(position);
        }
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvPhone, tvAddress;
        ImageView ivPersonPhoto;
        ImageButton btnDelete;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            ivPersonPhoto = itemView.findViewById(R.id.ivPersonPhoto);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
