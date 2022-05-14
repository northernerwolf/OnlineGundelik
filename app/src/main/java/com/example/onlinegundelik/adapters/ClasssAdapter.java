package com.example.onlinegundelik.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegundelik.ClassActivity;
import com.example.onlinegundelik.R;
import com.example.onlinegundelik.models.Classs;
import com.example.onlinegundelik.models.Student_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.List;

public class ClasssAdapter extends RecyclerView.Adapter<ClasssAdapter.ViewHolder>{


    private DatabaseReference databaseReference;
    private StorageTask storageTask;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference diaryRef = db.collection("AllClass");
    private static final String SERE_PREF_NAME = "mypref";
    private static final String TITLE = "title";
    private static final String CLID ="classid";
    private Context context;
    private List<Classs> classsList;
    FirebaseAuth auth;

    public ClasssAdapter(Context context , List<Classs> classsList){
        this.context = context;
        this.classsList = classsList;
        auth = FirebaseAuth.getInstance();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,parent,false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.class_name.setText(classsList.get(position).getTitle());
        holder.teacher_name.setText(classsList.get(position).getTeacher());
        holder.class_id.setText(classsList.get(position).getClassid());
        holder.delet_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("AllClass").document(classsList.get(position).getDocumentid())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    classsList.remove(classsList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context.getApplicationContext(), "Synp üstünliki pozuldy!", Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClassActivity.class);
                intent.putExtra("detail",classsList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classsList.size();
    }

    public void deleteItem(int position){
       
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        private final SharedPreferences sharedPreferences;
        TextView class_name, teacher_name, class_id;
        ImageView delet_cl;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            class_name = itemView.findViewById(R.id.class_title);
            teacher_name = itemView.findViewById(R.id.teacher_name);
            class_id = itemView.findViewById(R.id.class_id2);
            delet_cl =  itemView.findViewById(R.id.delet_class);
//            sharedPreferences = context.getSharedPreferences(SERE_PREF_NAME, Context.MODE_PRIVATE);

        }
    }
}
