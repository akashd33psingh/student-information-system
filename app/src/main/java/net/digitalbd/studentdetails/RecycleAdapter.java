package net.digitalbd.studentdetails;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyHolder> {
    List<StudentModel> dataModelArrayList;
    Context context;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    private AlertDialog dialog;

    public RecycleAdapter(Context context, List<StudentModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
    }

    public List<StudentModel> getItems() {
        return dataModelArrayList;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyHolder holder, int position) {

        final StudentModel dataModel = dataModelArrayList.get(position);
        holder.name.setText(dataModel.getName());
        holder.id.setText(dataModel.getStdid());
        holder.contact.setText(dataModel.getContact());
        holder.details.setText(dataModel.getDetails());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public void setFilter(ArrayList<StudentModel> newList) {
        dataModelArrayList = new ArrayList<>();
        dataModelArrayList.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name, id, contact, details;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            contact = itemView.findViewById(R.id.contact);
            details = itemView.findViewById(R.id.details);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int p = getLayoutPosition();
                    final StudentModel temp = dataModelArrayList.get(p);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are you sure Delete Student");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            openHelper = new DBHandler(context);
                            db = openHelper.getWritableDatabase();
                            String whereClause = "id=?";
                            String whereArgs[] = {temp.getId()};
                            db.delete(DBHandler.TABLE_NAME, whereClause, whereArgs);
                            getItems().remove(temp);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                    return true;
                }
            });
        }
    }
}
