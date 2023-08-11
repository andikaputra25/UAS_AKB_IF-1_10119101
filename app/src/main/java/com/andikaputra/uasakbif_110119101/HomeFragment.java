package com.andikaputra.uasakbif_110119101;

/**
 * Nim   : 10119101
 * Nama  : Andika Putra
 * Kelas : IF-1
 */

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    FloatingActionButton button;
    IAdapter adapter;
    List<Note> notes;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.listOfNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        button = view.findViewById(R.id.addBtn);
        NoteDatabase db = new NoteDatabase(view.getContext());
        notes = db.getNotes();
        adapter = new IAdapter(view.getContext(),notes);
        recyclerView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),AddNoteActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}