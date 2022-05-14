package com.example.onlinegundelik.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegundelik.DiaryActivity;
import com.example.onlinegundelik.R;
import com.example.onlinegundelik.models.Student_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    SharedPreferences sharedPreferences;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask storageTask;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference diaryRef = db.collection("AllStudent");
    private static final String SERE_PREF_NAME = "mypref";
    private static final String TITLE = "title";
    private static final String CLID ="classid";
    private Context context;
    private List<Student_model> student_modelList;
    FirebaseAuth auth;

    public StudentAdapter(Context context, List<Student_model> student_modelList) {
        this.context = context;
        this.student_modelList = student_modelList;
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,parent,false);
        StudentAdapter.ViewHolder viewHolder = new StudentAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.studentname.setText(student_modelList.get(position).getName());
        holder.studentid.setText(student_modelList.get(position).getStudentid());
        holder.deletstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("AllStudent").document(student_modelList.get(position).getDocumentCl())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    student_modelList.remove(student_modelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context.getApplicationContext(), "Okuwçy üstünliki pozuldy!", Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiaryActivity.class);
                intent.putExtra("detail", student_modelList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return student_modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView studentname, studentid;
        Context context;
        ImageView deletstu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentid = itemView.findViewById(R.id.studentid2);
            studentname = itemView.findViewById(R.id.student_name2);
            deletstu = itemView.findViewById(R.id.delet_student);

        }
    }
}
