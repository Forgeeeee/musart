package com.example.musart1;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;




public class RecyclerViewActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private ImageView imageView1, imageView2, imageView3;
    private Button buttonBackToMainActivity;
    private ProgressBar progressBar;
    RecyclerViewActivity binding;
    android.os.Handler handler;
    int progressStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        handler = new Handler();


        radioGroup = findViewById(R.id.radioGroup);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);


        progressBar= findViewById(R.id.progressBar);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {


                if (progressStatus < 100) {
                    progressStatus++;
                    progressBar.setProgress(progressStatus);
                    handler.postDelayed(this, 100); // Volver a ejecutar después de 100 ms
                }
            }
        };
        handler.post(runnable); // Iniciar el Runnable


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            imageView1.setVisibility(ImageView.GONE);
            imageView2.setVisibility(ImageView.GONE);
            imageView3.setVisibility(ImageView.GONE);



            if (checkedId == R.id.foto1) {
                imageView1.setVisibility(ImageView.VISIBLE);
            } else if (checkedId == R.id.foto2) {
                imageView2.setVisibility(ImageView.VISIBLE);
            } else if (checkedId == R.id.foto3) {
                imageView3.setVisibility(ImageView.VISIBLE);
            }

        });





        buttonBackToMainActivity = findViewById(R.id.buttonBackToMainActivity);
        buttonBackToMainActivity.setOnClickListener(v -> {
            Intent intent = new Intent(RecyclerViewActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
