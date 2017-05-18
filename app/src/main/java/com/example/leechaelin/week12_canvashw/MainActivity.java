package com.example.leechaelin.week12_canvashw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {
    my_canvas my_canvas;
    CheckBox ck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_canvas = (my_canvas)findViewById(R.id.canvas);
        ck = (CheckBox)findViewById(R.id.checkbox);

        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ck.isChecked()==true){
                    my_canvas.Checkbox_flag=true;
                }else{
                    my_canvas.Checkbox_flag=false;
                }
            }
        });
    }

    public void myClick(View v){

        if(v.getId()==R.id.erase){
            my_canvas.clear();

        }else if(v.getId()==R.id.rotate){
            ck.setChecked(true);
            my_canvas.rotate();

        }else if(v.getId()==R.id.move){
            ck.setChecked(true);
            my_canvas.move();
        }else if(v.getId()==R.id.scale){
            ck.setChecked(true);

            my_canvas.scale();

        }else if(v.getId()==R.id.skew){
            ck.setChecked(true);
            my_canvas.skew();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"Blurring").setCheckable(false);
        menu.add(0,2,0,"Coloring").setCheckable(false);
        menu.add(0,3,0,"Pen Width Big").setCheckable(false);
        menu.add(0,4,0,"Pen Color Red");
        menu.add(0,5,0,"Pen Color Blue");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1){
            my_canvas.setOperationtype("Blurring");
        }else if(item.getItemId()==2){
            my_canvas.setOperationtype("Coloring");
        }else if(item.getItemId()==3){
            my_canvas.setOperationtype("Pen Width Big");
        }else if(item.getItemId()==4){
            my_canvas.setOperationtype("Pen Color Red");
        }else if(item.getItemId()==5){
            my_canvas.setOperationtype("Pen Color Blue");
        }
        return super.onOptionsItemSelected(item);
    }



}
