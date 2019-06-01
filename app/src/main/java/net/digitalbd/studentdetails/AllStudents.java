package net.digitalbd.studentdetails;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class AllStudents extends AppCompatActivity implements SearchView.OnQueryTextListener {
    /*ArrayList<Student> allStudents;
    Context context;
    Db db;
    Context thisContext;
    ListView viewStudent;
    studentListAdapter adapter;*/

    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;
    List<StudentModel> dataModels;
    DBHandler openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        dataModels = new ArrayList<StudentModel>();
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        openHelper = new DBHandler(AllStudents.this);
        dataModels = openHelper.getdata();
        recycleAdapter = new RecycleAdapter(AllStudents.this, dataModels);
        RecyclerView.LayoutManager reLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        /*allStudents = new ArrayList<Student>();
        context = getApplicationContext();
        thisContext = this;
        db = new Db(context);
        //viewStudent = findViewById(R.id.studentList);
        allStudents = db.getAllStudent();
        viewStudent.setAdapter(new studentListAdapter(allStudents));
        viewStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Student tempStudent = allStudents.get(position);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(thisContext);
                dialogBuilder.setTitle("Delete Student");
                dialogBuilder.setMessage("Name: " + tempStudent.name + "\nId: " + tempStudent.id);
                dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteStudent(tempStudent.dbId);
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                return false;
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.searchView);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<StudentModel> newList = new ArrayList<>();
        for (StudentModel studentModel : dataModels) {
            String name = studentModel.getName().toLowerCase();
            if (name.contains(newText)) {
                newList.add(studentModel);
            }
        }
        recycleAdapter.setFilter(newList);
        return true;
    }
        /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    class studentListAdapter extends BaseAdapter {
        ArrayList<Student> allItems;

        studentListAdapter(ArrayList<Student> items) {
            allItems = items;
        }

        @Override
        public int getCount() {
            return allItems.size();
        }

        @Override
        public Object getItem(int position) {
            return allItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            final View row = inflater.inflate(R.layout.student_row, null, true);
            Student tempStudent = (Student) getItem(position);
            TextView viewName, viewId, viewDetails, viewContact;
            viewContact = row.findViewById(R.id.contact);
            viewDetails = row.findViewById(R.id.details);
            viewId = row.findViewById(R.id.id);
            viewName = row.findViewById(R.id.name);
            viewId.setText(tempStudent.getId());
            viewDetails.setText(tempStudent.getDetails());
            viewContact.setText(tempStudent.getContact());
            viewName.setText(tempStudent.getName());
            return row;
        }
    }*/
}
