package com.fatfat.hw1;

import android.app.ActionBar;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {
    int[] images ={R.drawable.dino, R.drawable.pig, R.drawable.pink, R.drawable.punk, R.drawable.witch};
    ImageSwitcher imgSwitch;
    TextView textView;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button backBtn = (Button)findViewById(R.id.backBtn);
        Button goBtn = (Button)findViewById(R.id.goBtn);
        imgSwitch = (ImageSwitcher)findViewById(R.id.imageSwitcher);
        textView = (TextView)findViewById(R.id.textView);
        //show();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                if (count <= 0)
                    count = 0;
                show();
            }
        });
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count >= images.length-1)
                    count =images.length-1;
                show();
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
}
