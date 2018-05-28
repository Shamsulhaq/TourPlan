package com.example.sam.tourplan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sam.tourplan.DataBase.MemoryDatabase;
import com.example.sam.tourplan.Event.Summary.Memory.MemoryModelClass;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class AddMemoryActivity extends AppCompatActivity {
    private TextInputEditText feelingET;
    private ImageView cameraIV;
    private Button cancleBtn,createBtn;
    private int eventID;
    private byte [] byteArray;
    private int flag = 0;
    private Bitmap b;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1111;
    private MemoryDatabase memoryDatabase;

    private MemoryModelClass memory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);
        setTitle("Create Memory");
        inti();

        cameraIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(AddMemoryActivity.this,cameraIV);
                popupMenu.getMenuInflater().inflate(R.menu.image_memu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.camera_menu:
                                takephoto(view);
                                break;
                            case R.id.gallery_menu:
                                pickImage(view);
                                break;
                        }
                        return false;
                    }
                });
            popupMenu.show();
            }

        });
        /*cameraIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View layout = LayoutInflater.from(AddMemoryActivity.this).inflate(R.layout.option_menu_for_picture,null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddMemoryActivity.this);
                builder.setView(layout);
                LinearLayout camera,gallery;
                camera = (LinearLayout) layout.findViewById(R.id.cameraimg);
                gallery = (LinearLayout) layout.findViewById(R.id.galleryimg);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        takephoto(view);

                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pickImage(view);
                    }
                });
                builder.show();
            }
        });*/

    }

    private void inti(){
        cancleBtn = (Button) findViewById(R.id.cancleBTN);
        createBtn = (Button) findViewById(R.id.addBTN);
        feelingET = (TextInputEditText)findViewById(R.id.feelings);
        cameraIV = (ImageView) findViewById(R.id.camera);
        eventID = getIntent().getIntExtra("rowID",0);
        memoryDatabase = new MemoryDatabase(this);
    }

    public void takephoto(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    public void pickImage(View View) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, GALLERY_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       switch (requestCode) {
           case CAMERA_REQUEST:
               Bitmap photo = (Bitmap) data.getExtras().get("data");
               cameraIV.setImageBitmap(photo);
               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               photo.compress(Bitmap.CompressFormat.PNG, 60, stream);
               byteArray = stream.toByteArray();
               break;
           case GALLERY_REQUEST:
               try {

                   if (b != null) {
                       b.recycle();
                   }else {
                       InputStream inputStreamstream = getContentResolver().openInputStream(
                               data.getData());
                       b = BitmapFactory.decodeStream(inputStreamstream);

                       cameraIV.setImageBitmap(b);
                       ByteArrayOutputStream outputStreamstream = new ByteArrayOutputStream();
                       b.compress(Bitmap.CompressFormat.PNG, 50, outputStreamstream);
                       byteArray = outputStreamstream.toByteArray();
                   }

               } catch (FileNotFoundException e) {
                   e.printStackTrace();

               } catch (IOException e) {
                   e.printStackTrace();
               }
               break;

        /*if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            cameraIV.setImageBitmap(photo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 60, stream);
            byteArray = stream.toByteArray();

        }*/

       }
    }


    public void canclebtnAction(View view) {
        startActivity(new Intent(AddMemoryActivity.this,EventDetailsActivity.class).putExtra("rowID",eventID));

    }

    public void createMemory(View view) {
        String text = feelingET.getText().toString();

        if (text.isEmpty()){
            feelingET.setError("Enter Title ");
        }else if (byteArray.equals(null)){
            Toast.makeText(this, "Enter Picture", Toast.LENGTH_SHORT).show();

        }else {
            memory = new MemoryModelClass(eventID,text,byteArray);
            boolean status = memoryDatabase.createMemory(memory);
            if (status){
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddMemoryActivity.this,ShowMemoryActivity.class).putExtra("rowID",eventID));
            }
        }

    }


}
