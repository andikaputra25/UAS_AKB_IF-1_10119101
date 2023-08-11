package com.andikaputra.uasakbif_110119101;

/**
 * Nim   : 10119101
 * Nama  : Andika Putra
 * Kelas : IF-1
 */

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.andikaputra.uasakbif_110119101.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDetailsBinding binding;

    TextView nDetails, nDate,textDetail, noteCategory;
    NoteDatabase db;
    Note note;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nDetails = findViewById(R.id.detailsOfNote);
        nDate = findViewById(R.id.dateNote);
        textDetail = findViewById(R.id.textDetail);
        noteCategory = findViewById(R.id.noteCategory);

        Intent intent = getIntent();
        id = intent.getLongExtra("ID",0);

        db = new NoteDatabase(this);
        Note note = db.getNote(id);
        getSupportActionBar().setTitle(note.getTitle());
        nDetails.setText(note.getContent());
        nDate.setText("Dibuat pada : "+note.getDate()+" "+note.getTime());
        textDetail.setText(note.getTitle());
        noteCategory.setText(note.getCategory());
        nDetails.setMovementMethod(new ScrollingMovementMethod());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNote(note.getID());
                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);
                Toast.makeText(DetailsActivity.this, "Catatan Berhasil Dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editNote){
            //mengirim user ke edit activity
            Intent i = new Intent(this, EditActivity.class);

            i.putExtra("ID",id);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

}