package com.example.musart1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;

    private ConstraintLayout mainLayout;
    private ImageView imageView;
    private CardView cardViewImage;
    private Button buttonShowCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner mySpinner = findViewById(R.id.mySpinner);
        recyclerView = findViewById(R.id.myRecyclerView);

        mainLayout = findViewById(R.id.main);



        imageView = findViewById(R.id.imageView);
        cardViewImage = findViewById(R.id.cardViewImage);
        buttonShowCard = findViewById(R.id.buttonShowCard);

        buttonShowCard.setOnClickListener(v -> {
            cardViewImage.setVisibility(View.VISIBLE);

        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);


        final int[][] option1Images = {
                {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5},

        };

        final float[][] option1Ratings = {
                {3.0f, 4.0f, 5.0f, 2.0f, 1.0f},
                {4.0f, 3.0f, 2.0f, 1.0f, 5.0f}, };


        final int[][] option2Images = {
                {R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10},
        };

        final float[][] option2Ratings = {
                {3.0f, 4.0f, 5.0f, 2.0f, 1.0f},
                {4.0f, 3.0f, 2.0f, 1.0f, 5.0f}, };

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setRecyclerView(option1Images, option1Ratings);
                } else if (position == 1) {
                    setRecyclerView(option2Images, option2Ratings);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        Button buttonOpenRecyclerView = findViewById(R.id.buttonOpenRecyclerView);

        buttonOpenRecyclerView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
            startActivity(intent);
        });



    }





    private void setRecyclerView(int[][] images, float[][] ratings){
        galleryAdapter = new GalleryAdapter(this, images, ratings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(galleryAdapter);
    }



    private void changeBackgroundColor(int color) {
        mainLayout.setBackgroundColor(color);
        Toast.makeText(this, "Fondo cambiado", Toast.LENGTH_SHORT).show();
    }




}









