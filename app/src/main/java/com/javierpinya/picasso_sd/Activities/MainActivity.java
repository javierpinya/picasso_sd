package com.javierpinya.picasso_sd.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.javierpinya.picasso_sd.Adapters.AnimalAdapter;
import com.javierpinya.picasso_sd.Adapters.ImagesAdapter;
import com.javierpinya.picasso_sd.Adapters.PartyAdapter;
import com.javierpinya.picasso_sd.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> images;
    private String[] animals;
    private int[] parties;

    private AnimalAdapter animalAdapter;
    private PartyAdapter partyAdapter;
    private ImagesAdapter imagesAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private final int PERMISSION_READ_EXTERNAL_MEMORY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animals = getAnimalsLinks();
        parties = getPartyPics();
        images = getImagesPath();

        animalAdapter = new AnimalAdapter(this, animals, R.layout.image_layout);
        partyAdapter = new PartyAdapter(this, parties, R.layout.image_layout);
        imagesAdapter = new ImagesAdapter(this, images, R.layout.image_layout);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(imagesAdapter);
    }

    private List<String> getImagesPath() {

        List<String> listOfAllImages = new ArrayList<String>();

        if(hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};

            Cursor cursor = getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, MediaStore.Images.Media._ID);

            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        if (path.contains("20190515")){
                            //Filtramos por el nombre. Ideal para cargar imágenes de una determinada inspección.
                            listOfAllImages.add(path);
                        }
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }else{
                Toast.makeText(this, "Cursor sin datos", Toast.LENGTH_SHORT).show();
            }
        }

        return listOfAllImages;

    }

    private int[] getPartyPics() {
        int[] values = {
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_foreground,
                R.drawable.ic_announcement_black_24dp,
                R.drawable.ic_battery_20_black_24dp,
                R.drawable.ic_battery_full_black_24dp,
                R.drawable.ic_gesture_black_24dp,
                R.drawable.ic_grade_black_24dp
        };
        return values;
    }

    private String[] getAnimalsLinks() {

        String[] links = {
                "https://i.imgur.com/CqmBjo5.jpg",
                "https://i.imgur.com/ZkaAooq.jpg",
                "https://i.imgur.com/0gqnEaY.jpg",
        };
        return links;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.links_adapter:
                recyclerView.setAdapter(animalAdapter);
                return true;
            case R.id.resources_adapter:
                recyclerView.setAdapter(partyAdapter);
                return true;
            case R.id.memory_adapter:
                checkForPermission();
                images.clear();
                images.addAll(getImagesPath());
                recyclerView.setAdapter(imagesAdapter);
                imagesAdapter.notifyDataSetChanged();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void checkForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_EXTERNAL_MEMORY);
        }
    }


    private boolean hasPermission(String permissionToCheck){
        int permissionCheck = ContextCompat.checkSelfPermission(this, permissionToCheck);
        return (permissionCheck == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_READ_EXTERNAL_MEMORY:
                if((grantResults.length>0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    images.clear();
                    images.addAll(getImagesPath());
                    imagesAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        images.clear();
        images.addAll(getImagesPath());
        imagesAdapter.notifyDataSetChanged();
    }
}
