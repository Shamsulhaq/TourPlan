package com.example.sam.tourplan;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.sam.tourplan.Adapter.MemoryAdapter;
        import com.example.sam.tourplan.DataBase.MemoryDatabase;
        import com.example.sam.tourplan.Event.Summary.Memory.MemoryModelClass;

        import java.util.ArrayList;

public class ShowMemoryActivity extends AppCompatActivity {
    private ListView mylist;
    private MemoryDatabase database;
    private ArrayList <MemoryModelClass> memorys;
    private MemoryAdapter adapter;
    private int eventID;

    private int memoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_memory);
        setTitle("Memory Details");


        inti();
        setAction();


    }
    private void setAction(){
        mylist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                memoryID = memorys.get(i).getMemoryId();

                AlertDialog.Builder builder = new AlertDialog.Builder(ShowMemoryActivity.this);
                builder.setTitle("Delete Event !");
                builder.setMessage("Are you sure you want to Delete?");
                builder.setIcon(R.drawable.folder);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean status = database.deleteMemory(memoryID);
                        if (status){
                            Toast.makeText(ShowMemoryActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ShowMemoryActivity.this,ShowMemoryActivity.class)
                                    .putExtra("rowID",eventID)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }else {
                            Toast.makeText(ShowMemoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();
                return false;
            }
        });
    }
    private void inti(){
        eventID = getIntent().getIntExtra("rowID",0);
        database =new MemoryDatabase(this);
        memorys = database.getAllMemory(eventID);
        mylist = (ListView) findViewById(R.id.mymemorylist);
        adapter = new MemoryAdapter(this,memorys);
        mylist.setAdapter(adapter);
        TextView nodataTv = (TextView) findViewById(R.id.nodatMT);
        if (memorys.isEmpty()){
            nodataTv.setText("NO Data Found!");
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.expenses_menu,menu);
        return true;

    }
    public void gotoHome(MenuItem item) {
        startActivity(new Intent(ShowMemoryActivity.this,EventDetailsActivity.class).putExtra("rowID",eventID));
    }

    public void fbAction(MenuItem item) {
        startActivity(new Intent(ShowMemoryActivity.this,AddMemoryActivity.class).putExtra("rowID",eventID));
    }
}
