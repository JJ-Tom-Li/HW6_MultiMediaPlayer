package com.example.no_1.hw6_multimediaplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MediaActivity extends AppCompatActivity {

    ImageView ivShow;
    Button btnHome,btnCover,btnLyric;
    ImageButton btnPrev,btnNext,btnPlay;
    int songNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        /*find view*/
        findView();
        /*set button listener*/
        btnPrev.setOnClickListener(imageDoClick);
        btnNext.setOnClickListener(imageDoClick);
        btnPlay.setOnClickListener(imageDoClick);
        btnHome.setOnClickListener(doClick);
        btnCover.setOnClickListener(doClick);
        btnLyric.setOnClickListener(doClick);

        Intent intent = getIntent();
        songNumber = intent.getIntExtra("Song_Number",1);
       /* Toast.makeText(getApplicationContext(),Integer.toString(songNumber),Toast.LENGTH_LONG)
                .show();*/
    }

    public void findView()
    {
        ivShow = (ImageView)findViewById(R.id.imageViewShow);
        btnPrev = (ImageButton)findViewById(R.id.imageButtonPrev);
        btnNext = (ImageButton)findViewById(R.id.imageButtonNext);
        btnPlay = (ImageButton)findViewById(R.id.imageButtonPlay);
        btnHome = (Button)findViewById(R.id.buttonHome);
        btnCover = (Button)findViewById(R.id.buttonCover);
        btnLyric = (Button)findViewById(R.id.buttonLyric);

    }

    private ImageButton.OnClickListener imageDoClick = new ImageButton.OnClickListener() {
        /*ImageButton listener*/
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButtonPrev:
                    /*switch to previous song*/
                    break;
                case R.id.imageButtonNext:
                    /*switch to next song*/
                    break;
                case R.id.imageButtonPlay:
                    /*play the song*/
                    break;
            }
        }
    };
    private Button.OnClickListener doClick = new Button.OnClickListener(){
        /*Button listener*/
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonHome:
                    /*go back to MainActivity and finish this activity*/
                    Intent intent = new Intent();
                    intent.setClass(MediaActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.buttonCover:
                    /*show the cover*/
                    break;
                case R.id.buttonLyric:
                    /*show the lyric*/
                    break;
            }
        }
    };
}
