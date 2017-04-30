package com.example.no_1.hw6_multimediaplayer;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.provider.MediaStore;
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
    int songnumber;
    String songs[]=new String[]{"missyou","colorful","onewing","entrance"};
    MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
    MediaPlayer mp;
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
        songnumber = intent.getIntExtra("Song_Number",1);//get song name

       /* Toast.makeText(getApplicationContext(),Integer.toString(songNumber),Toast.LENGTH_LONG)
                .show();*/
       setMetadataRetriver(songnumber);

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
                    mp.stop(); //first stop the music
                    if(songnumber==0)
                        songnumber=songs.length; //avoid overflow
                    songnumber--;
                    setMetadataRetriver(songnumber);
                    break;
                case R.id.imageButtonNext:
                    /*switch to next song*/
                    mp.stop(); //first stop the music
                    if(songnumber==songs.length-1)
                        songnumber=-1;
                    songnumber++;
                    setMetadataRetriver(songnumber);
                    break;
                case R.id.imageButtonPlay:
                    /*play the song*/
                    mp.start();
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
                    mp.stop();
                    finish();
                    break;
                case R.id.buttonCover:
                    /*show the cover*/
                    showCover();
                    break;
                case R.id.buttonLyric:
                    /*show the lyric*/
                    showLyric(songnumber);
                    break;
            }
        }
    };

    public void setMetadataRetriver(int index)
    {
        AssetFileDescriptor afd = getResources().openRawResourceFd(getResources().getIdentifier(songs[index], "raw", getApplicationContext().getPackageName()));
        metaRetriever.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
        ivShow.setImageDrawable(Drawable.createFromPath(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)));

        mp=MediaPlayer.create(this,getResources().getIdentifier(songs[index], "raw", getApplicationContext().getPackageName()));
        showCover();
    }

    public void showCover()
    {
        byte [] data = metaRetriever.getEmbeddedPicture();
        //coverart is an Imageview object

        // convert the byte array to a bitmap
        if(data != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            ivShow.setImageBitmap(bitmap); //associated cover art in bitmap
        }
        else
        {
            ivShow.setImageResource(R.drawable.nocover);
        }
    }

    public void showLyric(int index)
    {
        int id=getResources().getIdentifier("lyric"+songs[index], "drawable", getApplicationContext().getPackageName());
        if(id==0)
        {
            ivShow.setImageResource(R.drawable.nolyric);
        }
        else
        {
            ivShow.setImageResource(id);
        }
    }
}
