package com.example.no_1.hw6_multimediaplayer;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvCurrentSongRequest,tvSongRequest;
    Button btnNumber[]=new Button[10];
    Button btnClear,btnBack,btnList,btnEnter;
    String requestNumber="",showSongName="";
    String songs[]=new String[]{"missyou","colorful","onewing","entrance"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*find view*/
        findView();
        /*set button listener*/
        for(int i=0;i<10;i++)
            btnNumber[i].setOnClickListener(numberClick);
        btnClear.setOnClickListener(doClick);
        btnBack.setOnClickListener(doClick);
        btnList.setOnClickListener(doClick);
        btnEnter.setOnClickListener(doClick);
    }

    public void findView()
    {
        tvCurrentSongRequest = (TextView)findViewById(R.id.textViewCurrentSongRequest);
        tvSongRequest = (TextView)findViewById(R.id.textViewSongRequest);
        btnNumber[0]=(Button)findViewById(R.id.buttonZero);
        btnNumber[1]=(Button)findViewById(R.id.buttonOne);
        btnNumber[2]=(Button)findViewById(R.id.buttonTwo);
        btnNumber[3]=(Button)findViewById(R.id.buttonThree);
        btnNumber[4]=(Button)findViewById(R.id.buttonFour);
        btnNumber[5]=(Button)findViewById(R.id.buttonFive);
        btnNumber[6]=(Button)findViewById(R.id.buttonSix);
        btnNumber[7]=(Button)findViewById(R.id.buttonSeven);
        btnNumber[8]=(Button)findViewById(R.id.buttonEight);
        btnNumber[9]=(Button)findViewById(R.id.buttonNine);
        btnClear = (Button)findViewById(R.id.buttonClear);
        btnBack = (Button)findViewById(R.id.buttonBack);
        btnList = (Button)findViewById(R.id.buttonList);
        btnEnter = (Button)findViewById(R.id.buttonEnter);
    }
    private Button.OnClickListener numberClick = new Button.OnClickListener(){
        //Number Button Listener
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.buttonZero:
                    requestNumber+=0;
                    tvSongRequest.setText(requestNumber);
                    break;
                case R.id.buttonOne:
                    requestNumber+=1;
                    tvSongRequest.setText(requestNumber);
                    break;
                case R.id.buttonTwo:
                    requestNumber+=2;
                    tvSongRequest.setText(requestNumber);
                    break;
                case R.id.buttonThree:
                    requestNumber+=3;
                    tvSongRequest.setText(requestNumber);
                    break;
                case R.id.buttonFour:
                    requestNumber+=4;
                    tvSongRequest.setText(requestNumber);
                    break;
                case R.id.buttonFive:
                    requestNumber+=5;
                    tvSongRequest.setText(requestNumber);
                    break;
                case R.id.buttonSix:
                    requestNumber+=6;
                    tvSongRequest.setText(requestNumber);
                    break;
                case R.id.buttonSeven:
                    requestNumber+=7;
                    tvSongRequest.setText(requestNumber);
                    break;
                case R.id.buttonEight:
                    requestNumber+=8;
                    tvSongRequest.setText(requestNumber);
                    break;
                case R.id.buttonNine:
                    requestNumber+=9;
                    tvSongRequest.setText(requestNumber);
                    break;
            }
            if(checkSongNumber())
            {
                AssetFileDescriptor afd = getResources()
                        .openRawResourceFd(
                         getResources().getIdentifier(
                         songs[Integer.valueOf(requestNumber)-1]
                                 , "raw", getApplicationContext().getPackageName()));
                MediaMetadataRetriever metaRetriever= new MediaMetadataRetriever();
                metaRetriever.setDataSource(afd.getFileDescriptor()
                        ,afd.getStartOffset(),afd.getLength());
                showSongName="歌曲編號:"+requestNumber+"\n歌曲名稱:"+metaRetriever
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                showSongName();
            }
            else
            {
                showSongName="歌曲不存在!";
                showSongName();
            }
        }
    };

    private Button.OnClickListener doClick = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.buttonClear:
                    /*Clear the request*/
                    reset();
                    break;
                case R.id.buttonBack:
                    /*back a character of request*/
                    if(requestNumber.length()>0) {
                        requestNumber = requestNumber.substring(0, requestNumber.length() - 1);
                        tvSongRequest.setText(requestNumber);
                        showSongName = "歌曲編號:" + requestNumber + "\n歌曲名稱:";
                        showSongName();
                    }
                    break;
                case R.id.buttonList:
                    /*show song list*/
                    showList();
                    break;
                case R.id.buttonEnter:
                    /*First check if the song request is correct.
                      If correct,make the song request and call the activity_list.
                      Else,show the error message*/
                    if(checkSongNumber())
                    {
                        /*jump to the MediaActivity*/
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, MediaActivity.class)
                               .putExtra("Song_Number",Integer.valueOf(requestNumber)-1);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        showSongName="歌曲不存在!";
                        showSongName();
                    //    reset();
                    }
                    break;
            }
        }
    };

    public boolean checkSongNumber()
    {
        if(requestNumber.equals("")) return false; //no input
        else if(Integer.valueOf(requestNumber)>songs.length
                ||Integer.valueOf(requestNumber)<=0)
            return false; //larger than the number of songs
        else return true;
    }
    public void showSongName()
    {
        tvCurrentSongRequest.setText(showSongName);
    }

    public void reset()
    {
        requestNumber=showSongName="";
        tvSongRequest.setText("");
        tvCurrentSongRequest.setText("請利用下方數字鍵盤點歌:");
    }

    public void showList()
    {
        final String songlist[]= new String[songs.length];
        for(int i=0;i<songlist.length;i++)
        {
            AssetFileDescriptor afd = getResources()
                    .openRawResourceFd(getResources()
                    .getIdentifier(songs[i], "raw", getApplicationContext().getPackageName()));
            MediaMetadataRetriever metaRetriever= new MediaMetadataRetriever();
            metaRetriever.setDataSource(afd.getFileDescriptor()
                    ,afd.getStartOffset(),afd.getLength());
            songlist[i] = (i+1)+"."+metaRetriever
                    .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        }
        /*use the AlertDialog to show the songlist*/
        AlertDialog.Builder dialog_list = new AlertDialog.Builder(MainActivity.this);
        dialog_list.setTitle("歌單")
                .setItems(songlist,null)
                .show();
    }
}
