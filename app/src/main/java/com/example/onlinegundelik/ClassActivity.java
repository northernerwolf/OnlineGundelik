package com.example.onlinegundelik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinegundelik.Add.AddActivity;
import com.example.onlinegundelik.Add.AddStudentActivity;
import com.example.onlinegundelik.adapters.ClasssAdapter;
import com.example.onlinegundelik.adapters.StudentAdapter;
import com.example.onlinegundelik.models.Classs;
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

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {

    private Context context;
    SharedPreferences sharedPreferences;
    private static final String SERE_PREF_NAME = "mypref";
    private static final String TITLE = "title";
    private static final String CLID ="classid";
    String title, class_id;
    TextView class_namein, class_idin;
    ImageView imageViewadd , back;;
    Classs classs = null;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID;
    RecyclerView recyclerView;
    ImageView sortimg;
    String class_id_s;
    private FirebaseFirestore db;
    List<Student_model> student_modelList;
    StudentAdapter studentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        class_idin = findViewById(R.id.class_idput);
        class_namein = findViewById(R.id.class_namein);
        imageViewadd = findViewById(R.id.language);
        back = findViewById(R.id.back_mainadd);


        reference = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView = findViewById(R.id.recyclerstudent);
        db = FirebaseFirestore.getInstance();
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        recyclerView.setLayoutManager(new LinearLayoutManager(ClassActivity.this,RecyclerView.VERTICAL,false ));
        student_modelList = new ArrayList<>();
        studentAdapter = new StudentAdapter(this,student_modelList);
        recyclerView.setAdapter(studentAdapter);

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof Classs){
            classs = (Classs) object;
        }

        if ( classs != null){
            class_namein.setText( classs.getTitle());
            class_idin.setText(classs.getClassid());

            class_id_s = class_idin.getText().toString();

        }

        sharedPreferences = getSharedPreferences(SERE_PREF_NAME, MODE_PRIVATE);

        imageViewadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor  editor = sharedPreferences.edit();
                editor.putString(CLID, class_id_s);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), AddStudentActivity.class);
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
        db.collection("AllStudent").whereEqualTo("classid", class_id_s)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                    String documentCl = documentSnapshot.getId();

                                    Student_model classs = documentSnapshot.toObject(Student_model.class);
                                    classs.setDocumentCl(documentCl);
                                    student_modelList.add(classs);
                                    studentAdapter.notifyDataSetChanged();

                                }
                            } else {
                                Toast.makeText(ClassActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        }catch (IllegalAccessError e) {
                            Log.e("2error", "ConnectivityManager.NetworkCallback.onAvailable: ", e);
                        }
                    }
                });



    }
}