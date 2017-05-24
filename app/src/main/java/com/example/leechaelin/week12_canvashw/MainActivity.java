package com.example.leechaelin.week12_canvashw;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    my_canvas my_canvas;
    CheckBox ck;
    float[] matrixarray={2f,0f,0f,0f,-25f,
            0f,2f,0f,0f,-25f,
            0f,0f,2f,0f,-25f,
            0f,0f,0f,1f,0f};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_canvas = (my_canvas)findViewById(R.id.canvas);
        ck = (CheckBox)findViewById(R.id.checkbox);

        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ck.isChecked()){
                  my_canvas.Stamp_flag=true;
                }else{
                    my_canvas.Stamp_flag=false;
                }

            }
        });
    }

    public void myClick(View v){
        if(v.getTag()!=null){
            my_canvas.setOperationtype((String)v.getTag());
            ck.setChecked(true);
            my_canvas.Stamp_flag=true;
        }else{
            if(v.getId()==R.id.erase){
                my_canvas.clear();
            }else if(v.getId()==R.id.open){
                File f = new File(getFilesDir()+".jpg");
                if(f.isFile()){
                    my_canvas.clear();
                    Bitmap img = BitmapFactory.decodeFile(getFilesDir()+".jpg");
                    Bitmap simg = Bitmap.createScaledBitmap(img,img.getWidth()/2,img.getHeight()/2,false);
                    int x = my_canvas.getWidth()/2 - simg.getWidth()/2;
                    int y = my_canvas.getHeight()/2 - simg.getHeight()/2;
                    my_canvas.mCanvas.drawBitmap(simg,x,y,null);


                }else{
                    Toast.makeText(getApplicationContext(),"그런 파일이 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                }
            }else if(v.getId()==R.id.save){
                Bitmap save_img = my_canvas.getmBitmap();
                try{
                    FileOutputStream fs = new FileOutputStream(getFilesDir()+".jpg");
                    save_img.compress(Bitmap.CompressFormat.JPEG,100,fs);
                    fs.close();
                    Toast.makeText(getApplicationContext(),"저장이 완료되었습니다!",Toast.LENGTH_SHORT).show();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu1){
            if(item.isChecked()){
                item.setChecked(false);
                my_canvas.Blurring_flag=false;

            }else{
                item.setChecked(true);
                my_canvas.Blurring_flag=true;

            }
        }else if(item.getItemId()==R.id.menu2){
            if(item.isChecked()){
                item.setChecked(false);
                my_canvas.Coloring_flag=false;

            }else{
                item.setChecked(true);
                my_canvas.Coloring_flag=true;

            }
        }else if(item.getItemId()==R.id.menu3){
            if(item.isChecked()){
                item.setChecked(false);
                my_canvas.Bigwidth_flag=false;
            }else{
                item.setChecked(true);
                my_canvas.Bigwidth_flag=true;
            }
        }else if(item.getItemId()==R.id.menu4){
            //팬 색깔 빨간색

            my_canvas.red_flag=true;
            my_canvas.blue_flag=false;
        }else if(item.getItemId()==R.id.menu5){
            // 팬 색깔 파란색
            my_canvas.blue_flag=true;
            my_canvas.red_flag=false;
        }
        return super.onOptionsItemSelected(item);
    }



}
