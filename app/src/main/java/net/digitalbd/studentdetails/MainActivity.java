package net.digitalbd.studentdetails;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnAllStd, btnSaveStd;
    Context context;
    EditText viewName, viewId, viewContact, vDetails;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    private String name, id, details, contact;
    /*Db db;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        //db = new Db(context);
        openHelper = new DBHandler(MainActivity.this);
        btnAllStd = findViewById(R.id.btnAllStd);
        btnSaveStd = findViewById(R.id.btnSaveStd);
        viewName = findViewById(R.id.name);
        viewId = findViewById(R.id.id);
        viewContact = findViewById(R.id.contact);
        vDetails = findViewById(R.id.details);

        btnSaveStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                name = viewName.getText().toString();
                id = viewId.getText().toString();
                details = vDetails.getText().toString();
                contact = viewContact.getText().toString();
                if (validateData(name, id, details, contact)) {
                    insertData(name, id, contact, details);
                    Toast.makeText(MainActivity.this, "Student Save Successfully", Toast.LENGTH_SHORT).show();
                    clearInputBox();
                }
               /* String name, id, details, contact;
                name = viewName.getText().toString();
                id = viewId.getText().toString();
                details = vDetails.getText().toString();
                contact = viewContact.getText().toString();
                if (name.isEmpty() || id.isEmpty() || contact.isEmpty()) {
                    Toast.makeText(context, "Name, Id and Contact Field are Required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Student student = new Student(name, id, details, "", contact);
                db.addStudent(student);
                Toast.makeText(context, "Student Save Successfully", Toast.LENGTH_SHORT).show();
                clearInputBox();*/
            }
        });

        btnAllStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllStudents.class);
                startActivity(intent);
            }
        });
    }

    private void insertData(String name, String id, String contact, String details) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHandler.COL_2, name);
        contentValues.put(DBHandler.COL_3, id);
        contentValues.put(DBHandler.COL_4, contact);
        contentValues.put(DBHandler.COL_5, details);
        long ID = db.insert(DBHandler.TABLE_NAME, null, contentValues);
    }

    private boolean validateData(String name, String id, String details, String contact) {
        boolean isValid = true;
        if (name.isEmpty()) {
            viewName.setError("Enter Full Name");
            viewName.requestFocus();
            isValid = false;
        } else if (id.isEmpty()) {
            viewId.setError("Enter Age");
            viewId.requestFocus();
        } else if (contact.isEmpty()) {
            viewContact.setError("Enter Batch Year");
            viewContact.requestFocus();
        } else if (details.isEmpty()) {
            vDetails.setError("Enter Course");
            vDetails.requestFocus();
        }
        return isValid;
    }

    public void clearInputBox() {
        viewName.setText("");
        viewId.setText("");
        viewContact.setText("");
        vDetails.setText("");
    }
}
