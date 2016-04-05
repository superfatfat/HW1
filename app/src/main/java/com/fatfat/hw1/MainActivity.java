package com.fatfat.hw1;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int[] images ={R.drawable.dino, R.drawable.pig, R.drawable.pink, R.drawable.punk, R.drawable.witch};
    private ImageSwitcher imgSwitch;
    private TextView textView;
    private GestureOverlayView gov;
    private int count=0;
    private GestureLibrary gestureLibrary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if(!gestureLibrary.load()){
            finish();
        }
        Button backBtn = (Button)findViewById(R.id.backBtn);
        Button goBtn = (Button)findViewById(R.id.goBtn);
        imgSwitch = (ImageSwitcher)findViewById(R.id.imageSwitcher);
        textView = (TextView)findViewById(R.id.textView);
        gov = (GestureOverlayView)findViewById(R.id.gov);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        gov.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
                String gestureName = predictions.get(0).name;
                double gestureScore = predictions.get(0).score;

                switch (gestureName) {
                    case "swipe_left":
                        if (gestureScore >= 20) {
                            back();
                        }
                        break;
                    case "swipe_right":
                        if (gestureScore >= 20) {
                            next();
                        }
                        break;
                }
            }
        });
        imgSwitch.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setBackgroundColor(0xFFFFFFFF);
                imageView.setScaleType(ImageView.ScaleType.CENTER);//居中显示
                //imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));//定义组件
                return imageView;

            }
        });

        show();//初始化时显示，必须放在工厂后面，否则会报NullPointerException
        imgSwitch.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));//设置动画
        imgSwitch.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
    }
    private void show(){
        imgSwitch.setImageResource(images[count]);
        textView.setText((count+1)+"/"+ images.length);
    }
    private void back(){
        count--;
        if (count <= 0)
            count = 0;
        show();
    }
    private void next(){
        count++;
        if(count >= images.length-1)
            count =images.length-1;
        show();
    }
}
