package coding.insight.cleanuiloginregister;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    Handler handler;
    ImageView rotate_image;
    TextView textView3;
    String value9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
           textView3=findViewById(R.id.textView3);
        Bundle extras = getIntent().getExtras();
         value9 = extras.getString("p");
         textView3.setText(value9+"₹ TO BE PAID");
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        setContentView(R.layout.activity_main2);
//        getSupportActionBar().hide();
        rotate_image=findViewById(R.id.rotate_image);


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        RotateAnimation rotate = new RotateAnimation(30, 360, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate_image.startAnimation(rotate);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);

                startActivity(intent);
                finish();
            }
        },3000);

        LinearLayout mainFrame = ((LinearLayout) findViewById(R.id.frame));
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this,
                R.anim.anim_slide_in_left);
        mainFrame.startAnimation(hyperspaceJumpAnimation);
    }
}