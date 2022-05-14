package com.example.onlinegundelik.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinegundelik.R;
import com.example.onlinegundelik.models.MarkModel;
import com.example.onlinegundelik.models.Student_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder>{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference diaryRef = db.collection("AllMarks");
    private Context context;
    private List<MarkModel> markModelList;
    FirebaseAuth auth;

    public DiaryAdapter(Context context, List<MarkModel> markModelList) {
        this.context = context;
        this.markModelList = markModelList;
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public DiaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mark,parent,false);
        DiaryAdapter.ViewHolder viewHolder = new DiaryAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mark_item.setText(markModelList.get(position).getMark());
        holder.mark_sub.setText(markModelList.get(position).getSubject());
        holder.mark_day_i.setText(markModelList.get(position).getMarkday());
        holder.bellik.setText(markModelList.get(position).getNote());
        holder.delet_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("AllMarks").document(markModelList.get(position).getDocumentMark())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    markModelList.remove(markModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context.getApplicationContext(), "Baha üstünlikli pozuldy!", Toast.LENGTH_SHORT).show();

                                }else
                                    Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                            }
                        });
                }
        });

    }

    @Override
    public int getItemCount() {
        return markModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mark_sub, mark_day_i, mark_item, bellik;
        ImageView delet_mark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mark_sub = itemView.findViewById(R.id.mark_sub_item);
            mark_day_i = itemView.findViewById(R.id.mark_day_item);
            mark_item = itemView.findViewById(R.id.mark_view);
            bellik = itemView.findViewById(R.id.mark_bellik);
            delet_mark = itemView.findViewById(R.id.delet_mark);
        }
    }
}
