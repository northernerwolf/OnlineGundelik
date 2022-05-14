package com.example.onlinegundelik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinegundelik.Add.AddBahaActivity;
import com.example.onlinegundelik.Add.AddStudentActivity;
import com.example.onlinegundelik.adapters.DiaryAdapter;
import com.example.onlinegundelik.adapters.StudentAdapter;
import com.example.onlinegundelik.models.Classs;
import com.example.onlinegundelik.models.MarkModel;
import com.example.onlinegundelik.models.Student_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiaryActivity extends AppCompatActivity {


    private Context context;
    String title, class_id,Date;
    SharedPreferences sharedPreferences;
    private static final String SERE_PREF_NAME = "mypref";
    private static final String TITLEID = "titleid";
    TextView class_namein, class_idin, time;
    ImageView imageViewadd , back;;
    Student_model student_model = null;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID, mark_int;
    RecyclerView recyclerView;
    private FirebaseFirestore db;
    List<MarkModel> markModelList;
    DiaryAdapter diaryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        class_idin = findViewById(R.id.class_idput_s);
        class_namein = findViewById(R.id.class_name_s);
        imageViewadd = findViewById(R.id.language);
        back = findViewById(R.id.back_mainad);






//        String time1 = time.getText().toString();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView = findViewById(R.id.recyclerdiary);
        db = FirebaseFirestore.getInstance();
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        recyclerView.setLayoutManager(new LinearLayoutManager(DiaryActivity.this,RecyclerView.VERTICAL,false ));
        markModelList = new ArrayList<>();
        diaryAdapter = new DiaryAdapter(this,markModelList);
        recyclerView.setAdapter(diaryAdapter);


        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof Student_model){
            student_model = (Student_model) object;
        }

        if ( student_model != null){
            class_namein.setText( student_model.getName());
            class_idin.setText(student_model.getStudentid());


            mark_int = class_idin.getText().toString();

        }


        sharedPreferences = getSharedPreferences(SERE_PREF_NAME, MODE_PRIVATE);
        imageViewadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor  editor = sharedPreferences.edit();
                editor.putString(TITLEID, mark_int);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), AddBahaActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });

        db.collection("AllMarks").whereEqualTo("studentid",mark_int)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                    String documentMark = documentSnapshot.getId();

                                    MarkModel mark= documentSnapshot.toObject(MarkModel.class);

                                    mark.setDocumentMark(documentMark);
                                    markModelList.add(mark);
                                    diaryAdapter.notifyDataSetChanged();

                                }
                            } else {
                                Toast.makeText(DiaryActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        }catch (IllegalAccessError e) {
                            Log.e("2error", "ConnectivityManager.NetworkCallback.onAvailable: ", e);
                        }
                    }
                });

    }
}