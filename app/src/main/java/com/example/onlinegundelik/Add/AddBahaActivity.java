package com.example.onlinegundelik.Add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinegundelik.ClassActivity;
import com.example.onlinegundelik.DiaryActivity;
import com.example.onlinegundelik.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddBahaActivity extends AppCompatActivity {

    private static final String MARK ="mark";
    private static final String SUBJECT ="subject";
    private static final String STUDENT_ID ="studentid";
    private static final String DAY ="markday";
    private static final String BELLIK = "note";

    SharedPreferences sharedPreferences;
    private static final String SERE_PREF_NAME = "mypref";
    private static final String TITLEID = "titleid";

    EditText editTextmark, editTextmarkday, editTextstudentid, editTextsubject;
    AppCompatButton add_mark;
    TextInputEditText editTextbellik;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private String UserID, Date;
    private TextView mark_idd, time;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask storageTask;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference markRef = db.collection("AllMarks");
    private FirebaseAuth firebaseAuth;
    private ImageView back;
    private ProgressBar progressBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baha);


        editTextmark = findViewById(R.id.student_mark);
//        editTextmarkday = findViewById(R.id.mark_day);
//        editTextstudentid = findViewById(R.id.student_id_mark);
        editTextbellik = findViewById(R.id.student_bellik);
        editTextsubject = findViewById(R.id.mark_subject);
        progressBar1 = findViewById(R.id.progresbar2);
        time = findViewById(R.id.time_now);

        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        back = findViewById(R.id.back_mainad);
        add_mark = findViewById(R.id.add_mark);
        mark_idd = findViewById(R.id.mark_id);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date = simpleDateFormat.format(calendar.getTime());


        sharedPreferences = getSharedPreferences(SERE_PREF_NAME, MODE_PRIVATE);
        String clasid = sharedPreferences.getString(TITLEID, null);

        if (clasid != null){
            mark_idd.setText(clasid);
            time.setText(Date);
        }

        add_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_Mark();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
    }

    private void Save_Mark() {


        String mark = editTextmark.getText().toString();
        String day = time.getText().toString();
        String studentid = mark_idd.getText().toString();
        String bellik = editTextbellik.getText().toString();
        String subject = editTextsubject.getText().toString();


//        if (mark.isEmpty()){
//            editTextmark.setError("Bahalandyryň");
//            editTextmark.requestFocus();
//            return;
//        }
//        if (studentid.isEmpty()){
//            editTextstudentid.setError("Okuwça ID berilmedi");
//            editTextstudentid.requestFocus();
//            return;
//        }
//        if (day.isEmpty()){
//            editTextmarkday.setError("Senesini giriziň");
//            editTextmarkday.requestFocus();
//            return;
//        }
        if (subject.isEmpty()){
            editTextsubject.setError("Sapagy girizilmedi");
            editTextsubject.requestFocus();
            return;
        }

        Map<String, Object> marks = new HashMap<>();
        marks.put(MARK, mark);
        marks.put(STUDENT_ID, studentid);
        marks.put(SUBJECT, subject);
        marks.put(BELLIK, bellik);
        marks.put(DAY, day);

        progressBar1.setVisibility(View.VISIBLE);
        markRef.add(marks)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Intent intent = new Intent(AddBahaActivity.this, ClassActivity.class);
                        Toast.makeText(AddBahaActivity.this, "Üstünlikli goşuldy !", Toast.LENGTH_SHORT).show();
//                        startActivity(intent);
                        setResult(2);
                        finish();
//                        finishAffinity();
                        progressBar1.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar1.setVisibility(View.GONE);
                        Toast.makeText(AddBahaActivity.this, "Üstünliksiz !", Toast.LENGTH_SHORT).show();

                    }
                });



    }
}