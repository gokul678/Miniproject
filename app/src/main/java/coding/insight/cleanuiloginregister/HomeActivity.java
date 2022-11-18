package coding.insight.cleanuiloginregister;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        getActionBar().setTitle("MESS");
        return super.getSupportActionBar();
    }
    //    MaterialButton btn_pick_date;
//    MaterialTextView tv_date;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        btn_pick_date=findViewById(R.id.btn_pick_date);
//        tv_date=findViewById(R.id.tv_date);
//
//        btn_pick_date.setOnClickListener(v -> showDatePickerDialog());
//
//
//    }
//
//    private void showDatePickerDialog() {
//        MaterialDatePicker<Long> materialDatePicker= MaterialDatePicker.Builder.datePicker().setTitleText("ss").build();
//
//        materialDatePicker.addOnPositiveButtonClickListener(selection -> tv_date.setText("" + materialDatePicker.getHeaderText()));
//
//        materialDatePicker.show(getSupportFragmentManager(), "TAG");


//

    private EditText PickDate;
    private int year,month,day;
    private ImageView button;
    public TextView nameview;
    public TextView depview;
    public TextView semview;
    public TextView nfd;
//    public TextView fday,lday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        if(getSupportActionBar() !=null)
//        {
//            getSupportActionBar().hide();
//        }
         nameview=findViewById(R.id.nameview);
//          fday=findViewById(R.id.fday);
//          lday=findViewById(R.id.lday);
         depview=findViewById(R.id.depview);
         semview=findViewById(R.id.semview);

        button=findViewById(R.id.button);
        Bundle extras = getIntent().getExtras();
        String value1 = extras.getString("x");
        String value2 = extras.getString("y");
        String value3 = extras.getString("z");
        String value5 = extras.getString("fame");
        String value6 = extras.getString("lime");
        String value7=extras.getString("w");
        Toast.makeText(this, value7, Toast.LENGTH_LONG).show();
//        Intent intentdd = new Intent(getApplicationContext(), Selectdate.class);
//        intentdd.putExtra("h",value7);



//        String value4= extras.getString("nfd");
        nameview.setText(  "Name: "+value1);
        depview.setText("Department: "+value2);
        semview.setText("Semester: "+value3);
//        fday.setText(value5);
//        lday.setText(value6);

//        nfd.setText(value4+"Days");


//        Toast.makeText(getApplicationContext(),value2, Toast.LENGTH_LONG).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity2 = new Intent(getApplicationContext(),Selectdate.class);
                activity2.putExtra("h",value7);

                startActivity(activity2);









            }
        });
    }



}