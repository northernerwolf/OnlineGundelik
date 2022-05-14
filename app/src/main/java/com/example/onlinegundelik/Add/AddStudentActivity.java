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
import com.example.onlinegundelik.MainActivity;
import com.example.onlinegundelik.R;
import com.example.onlinegundelik.fragments.HomeFragment;
import com.example.onlinegundelik.models.Classs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {

    private static final String NAME ="name";
    private static final String CLASS_ID ="classid";
    private static final String STUDENT_ID ="studentid";
    private static final String USERID ="userid";
    private static final String SERE_PREF_NAME = "mypref";
    private static final String CLID ="classid";

    EditText editTextname, editTextclassid, editTextstudentid;
    AppCompatButton add_class;
    TextView input_txt;
    Classs classs = null;
    SharedPreferences sharedPreferences;
    private String UserID;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask storageTask;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference studentRef = db.collection("AllStudent");
    private FirebaseAuth firebaseAuth;
    private ImageView back;
    private ProgressBar progressBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        editTextname = findViewById(R.id.student_name);
//        editTextclassid = findViewById(R.id.class_identer);
        editTextstudentid = findViewById(R.id.student_identer);
        progressBar1 = findViewById(R.id.progresbar1);
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        back = findViewById(R.id.back_mainadd);
        add_class = findViewById(R.id.add_class);
        input_txt = findViewById(R.id.id_class_input);

        sharedPreferences = getSharedPreferences(SERE_PREF_NAME, MODE_PRIVATE);
        String clasid = sharedPreferences.getString(CLID, null);

        if (clasid != null){
            input_txt.setText(clasid);
        }


        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_Student();
            }
        });

//        final Object object = getIntent().getSerializableExtra("detail");
//        if (object instanceof Classs){
//            classs = (Classs) object;
//        }
//
//        if ( classs != null){
//            input_txt.setText( classs.getClassid());
////
//        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
    }

    private void Save_Student() {



        String name = editTextname.getText().toString();
        String studentid = editTextstudentid.getText().toString();
        String classid = input_txt.getText().toString();



        if (name.isEmpty()){
            editTextname.setError("Ady familýasyny giriziň");
            editTextname.requestFocus();
            return;
        }
        if (studentid.isEmpty()){
            editTextstudentid.setError("Okuwça ID berilmedi");
            editTextstudentid.requestFocus();
            return;
        }
//        if (classid.isEmpty()){
//            editTextclassid.setError("Synpa ID berilmedi");
//            editTextclassid.requestFocus();
//            return;
//        }

        Map<String, Object> classs = new HashMap<>();
        classs.put(NAME, name);
        classs.put(STUDENT_ID, studentid);
        classs.put(CLASS_ID, classid);

        progressBar1.setVisibility(View.VISIBLE);
        studentRef.add(classs)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Intent intent = new Intent(AddStudentActivity.this, AddStudentActivity.class);
                        Toast.makeText(AddStudentActivity.this, "Üstünlikli goşuldy !", Toast.LENGTH_SHORT).show();
//                        startActivity(intent);
//                        finishAffinity();
                        setResult(2);
                        finish();
                        progressBar1.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar1.setVisibility(View.GONE);
                        Toast.makeText(AddStudentActivity.this, "Üstünliksiz !", Toast.LENGTH_SHORT).show();

                    }
                });


    }
}