package com.example.agendify.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendify.EditContacto;
import com.example.agendify.Entidades.Contactos;
import com.example.agendify.R;

import java.util.ArrayList;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    ArrayList<Contactos> listaContactos;

    // Constructor que recibe la lista de contactos
    public ListaContactosAdapter(ArrayList<Contactos> listaContactos){
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    // Método para crear una nueva instancia de la vista de un elemento de la lista
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de la vista de un elemento de la lista desde el archivo XML
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    // Método llamado cuando se quiere mostrar un elemento en una posición específica
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        // Configurar los datos del contacto en la vista
        holder.TvNombreRecycle.setText(listaContactos.get(position).getNombre());
        holder.TvTelefonoRecycle.setText(listaContactos.get(position).getTelefono());
        holder.TvCorreoRecycle.setText(listaContactos.get(position).getCorreo_electronico());
    }

    @Override
    // Método que devuelve la cantidad de elementos en la lista
    public int getItemCount() {
        return listaContactos.size();
    }

    // Clase interna que representa la vista de un elemento de la lista
    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView TvNombreRecycle, TvTelefonoRecycle, TvCorreoRecycle;

        // Constructor que recibe la vista de un elemento de la lista
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar los TextViews con los elementos de la vista
            TvNombreRecycle = itemView.findViewById(R.id.TvNombreRecycle);
            TvTelefonoRecycle = itemView.findViewById(R.id.TvTelefonoRecycle);
            TvCorreoRecycle = itemView.findViewById(R.id.TvCorreoRecycle);

            // Configurar un listener para el evento de clic en un elemento de la lista
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtener el contexto y lanzar la actividad de edición de contacto con el ID del contacto
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EditContacto.class);
                    intent.putExtra("ID", listaContactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
