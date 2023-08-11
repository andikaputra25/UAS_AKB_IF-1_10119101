package com.andikaputra.uasakbif_110119101;


/**
 * Nim   : 10119101
 * Nama  : Andika Putra
 * Kelas : IF-1
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText noteTitle, noteDetail;
    Spinner spinner;
    TextView hari;
    Calendar c;
    String bulan;
    String todaysDate;
    String currentTime;
    NoteDatabase db;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        Long id = i.getLongExtra("ID",0);
        db = new NoteDatabase(this);
        note = db.getNote(id);



        toolbar = findViewById(R.id.toolbar);
        hari = findViewById(R.id.hari);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(note.getTitle());
        noteTitle = findViewById(R.id.noteTitle);
        noteDetail = findViewById(R.id.noteDetail);

        noteTitle.setText(note.getTitle());
        noteDetail.setText(note.getContent());



        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0 ){
                    getSupportActionBar().setTitle(charSequence);
                }else {
                    getSupportActionBar().setTitle("Edit Baru");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //waktu(Date) hari ini
        c = Calendar.getInstance();
        Integer bln = (c.get(Calendar.MONTH)+1);
        if (bln == 1){
            bulan = "Jan";
        }else if (bln ==2){
            bulan = "Feb";
        }else if (bln ==3){
            bulan = "Mar";
        }else if (bln ==4){
            bulan = "Apr";
        }else if (bln ==5){
            bulan = "Mei";
        }else if (bln ==6){
            bulan = "Jun";
        }else if (bln ==7){
            bulan = "Jul";
        }else if (bln ==8){
            bulan = "Agu";
        }else if (bln ==9){
            bulan = "Sep";
        }else if (bln ==10){
            bulan = "Okt";
        }else if (bln ==11){
            bulan = "Nov";
        }else if (bln ==12){
            bulan = "Des";
        }
        todaysDate =  c.get(Calendar.DAY_OF_MONTH)+ " "  + bulan + " " + c.get(Calendar.YEAR);
        currentTime = pad(c.get(Calendar.HOUR)) + ":" +pad(c.get(Calendar.MINUTE));
        hari.setText(todaysDate);
        Log.d("Kalender","Tanggal dan waktu : "+ todaysDate + " dan "+ currentTime);


        //spinner (Category)
        spinner = findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("📝 Catatan");
        arrayList.add("🎓 Kuliah");
        arrayList.add("🏠 Rumah");
        arrayList.add("🔏 Pribadi");
        arrayList.add("❗ Penting");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                note.setCategory(spinner.getSelectedItem().toString());
                int id = db.editNote(note);
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });

    }

    private String pad(int i) {
        if (i < 10){
            return "0"+i;
        }else {
            return String.valueOf(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save){
            note.setTitle(noteTitle.getText().toString());
            note.setContent(noteDetail.getText().toString());
            note.setCategory(spinner.getSelectedItem().toString());
            int id = db.editNote(note);
            Toast.makeText(this, "Catatan Dirubah", Toast.LENGTH_SHORT).show();
            goToMain();
        }
        if (item.getItemId() == R.id.delete){
            Toast.makeText(this, "Catatan Tidak Disimpan.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}