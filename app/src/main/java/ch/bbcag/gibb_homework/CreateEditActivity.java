package ch.bbcag.gibb_homework;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.bbcag.gibb_homework.constants.IntentContext;
import ch.bbcag.gibb_homework.dal.TaskDAO;
import ch.bbcag.gibb_homework.model.Task;

public class CreateEditActivity extends AppCompatActivity {

    private String context;
    private String uploadedFileName;
    private static final int PICK_IMAGE = 1;
    TaskDAO taskDAO;
    ImageView imageView;
    Button btnOpen;
    Button btnSubmit;
    Uri saveImageUri;
    Task newTask = new Task();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Back-Button

        if(getIntent().getStringExtra(IntentContext.NAME) != null) {
            context = getIntent().getStringExtra(IntentContext.NAME);
            setActivityTitle();
        } else {
            Log.e("Intent", "Invalid call!");
        }

        imageView = findViewById(R.id.upload_image);
        btnOpen = findViewById(R.id.button_upload);
        btnSubmit = findViewById(R.id.button_submit);

        if(ContextCompat.checkSelfPermission(CreateEditActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateEditActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                saveImageUri = imageUri;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 100);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDAO = new TaskDAO(CreateEditActivity.this);
                if(uploadedFileName != null) {
                    newTask.setImageFile(uploadedFileName);
                } else {
                    newTask.setImageFile("");
                }
                newTask.setTitle("Titel");
                newTask.setDescription("Beschreibung");
                newTask.setDueDate("01.02.21");
                newTask.setModuleId(17);
                taskDAO.add(
                        newTask.getTitle(),
                        newTask.getDescription(),
                        newTask.getDueDate(),
                        newTask.getModuleId(),
                        newTask.getImageFile(),
                        0
                );
            }
        });
    }

    @Override
    // Method to configure Back/Up-Button
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setActivityTitle() {
        if (context.equals(IntentContext.CONTEXT_CREATE)) {
            setTitle(getString(R.string.title_create)); // Access dynamic string from strings.xml
        }
        if (context.equals(IntentContext.CONTEXT_EDIT)) {
            setTitle(getString(R.string.title_edit)); // Access dynamic string from strings.xml
        }
    }

    // Triggered when user clicks on the upload button
    public void uploadImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), saveImageUri);
                    imageView.setImageBitmap(thumbnail);
                    String imagePath = getRealPathFromURI(saveImageUri);
                    storeImage(thumbnail); // Write to storage
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("ERROR", "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("ERROR", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("ERROR", "Error accessing file: " + e.getMessage());
        }
    }

    private File getOutputMediaFile(){
        File mediaStorageDir = new File("data/data/ch.bbcag.gibb_homework/images");

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
        File mediaFile;
        String mImageName="IMG_"+ timeStamp +".png";
        uploadedFileName = mImageName; // Save the filename so its accessible later
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}