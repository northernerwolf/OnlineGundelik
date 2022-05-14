package com.example.onlinegundelik.Add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.onlinegundelik.MainActivity;
import com.example.onlinegundelik.R;
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

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "Cannot invoke method length() on null object";
    private static final String SHARE_PREF ="mypref";
    private static final String KEY_TITLE ="title";
    private static final String KEY_TEACHER ="teacher";
    private static final String KEY_CLID ="classid";
    private static final String USERID ="userid";

    EditText editTextclass, editTextteacher, editTextClassId;
    AppCompatButton add_class;
    private String UserID;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask storageTask;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference diaryRef = db.collection("AllClass");
    private FirebaseAuth firebaseAuth;
    private ImageView back;
    private ProgressBar progressBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        back = findViewById(R.id.back_mainadd);
        editTextclass = findViewById(R.id.class_name);
        editTextteacher = findViewById(R.id.teacher_name);
        editTextClassId = findViewById(R.id.class_id);
        progressBar1 = findViewById(R.id.progresbar1);
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        add_class = findViewById(R.id.add_class);
        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_Class();
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


    protected void Save_Class(){

        String title = editTextclass.getText().toString();
        String teachers = editTextteacher.getText().toString();
        String classid = editTextClassId.getText().toString();
        String userid = UserID.toString();


        if (title.isEmpty()){
            editTextclass.setError("Synpyn ady ýok");
            editTextclass.requestFocus();
            return;
        }
        if (teachers.isEmpty()){
            editTextteacher.setError("Okadýan mugallymyň ady ýok");
            editTextteacher.requestFocus();
            return;
        }
        if (classid.isEmpty()){
            editTextClassId.setError("Synpa ID berilmedi");
            editTextClassId.requestFocus();
            return;
        }

        Map<String, Object> classs = new HashMap<>();
        classs.put(KEY_TITLE, title);
        classs.put(KEY_TEACHER, teachers);
        classs.put(KEY_CLID, classid);
        classs.put(USERID, userid);
        progressBar1.setVisibility(View.VISIBLE);
        diaryRef.add(classs)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        Toast.makeText(AddActivity.this, "Üstünlikli goşuldy !", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                        progressBar1.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar1.setVisibility(View.GONE);
                        Toast.makeText(AddActivity.this, "Üstünliksiz !", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}