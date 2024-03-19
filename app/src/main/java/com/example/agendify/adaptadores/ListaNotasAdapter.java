package com.example.agendify.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendify.Entidades.Notas;
import com.example.agendify.R;
import com.example.agendify.VerNota;

import java.util.ArrayList;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    ArrayList<Notas> listaNotas;

    // Constructor que recibe la lista de notas
    public ListaNotasAdapter(ArrayList<Notas> listaNotas) {
        this.listaNotas = listaNotas;
    }

    @NonNull
    @Override
    // Método para crear una nueva instancia de la vista de un elemento de la lista
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño de la vista de un elemento de la lista desde el archivo XML
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_nota, parent, false);
        Log.d("ListaNotasAdapter", "onCreateViewHolder");
        return new NotaViewHolder(view);
    }

    @Override
    // Método llamado cuando se quiere mostrar un elemento en una posición específica
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        // Configurar los datos de la nota en la vista
        holder.TvTituloRecycle.setText(listaNotas.get(position).getTitulo());
        holder.TvDescripcionRecycle.setText(listaNotas.get(position).getDescripcion());
        Log.d("ListaNotasAdapter", "onBindViewHolder: " + position);
    }

    @Override
    // Método que devuelve la cantidad de elementos en la lista
    public int getItemCount() {
        return listaNotas.size();
    }

    // Clase interna que representa la vista de un elemento de la lista
    public class NotaViewHolder extends RecyclerView.ViewHolder {

        TextView TvTituloRecycle, TvDescripcionRecycle;

        // Constructor que recibe la vista de un elemento de la lista
        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar los TextViews con los elementos de la vista
            TvTituloRecycle = itemView.findViewById(R.id.TvTituloRecycle);
            TvDescripcionRecycle = itemView.findViewById(R.id.TvDescripcionRecycle);

            // Configurar un listener para el evento de clic en un elemento de la lista
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtener el contexto y lanzar la actividad para ver la nota con el ID de la nota
                    Context context = v.getContext();
                    Intent intent = new Intent(context, VerNota.class);
                    intent.putExtra("ID", listaNotas.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
