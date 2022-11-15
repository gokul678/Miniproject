package coding.insight.cleanuiloginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.squareup.timessquare.CalendarPickerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Selectdate extends AppCompatActivity {

    private CalendarPickerView calendarPickerView;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdate);

        Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.NOVEMBER, 1);
        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        Date tomorrow = calendar.getTime();
        calendarPickerView.init(lastYear.getTime(),tomorrow)
                .withSelectedDate(today)
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
        //calendar.highlightDates(getHolidays());
        Log.d("today", String.valueOf(today.getYear()));
        Log.d("today", String.valueOf(today.getMonth()));
        Log.d("today", String.valueOf(today.getDay()));

        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        //sdf.format(today);
        Log.d("newFormatDate", sdf.format(today));

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Date> selectedDates = (ArrayList<Date>) calendarPickerView
                        .getSelectedDates();
                Log.d("newFormatDate", sdf.format(selectedDates.get(0)));
                Log.d("newFormatDate", sdf.format(selectedDates.get(selectedDates.size() - 1)));
//                Toast.makeText(Selectdate.this, selectedDates.toString(),
//                        Toast.LENGTH_LONG).show();
                int nowadays = selectedDates.size();
                if (nowadays < 3) {
                    Toast.makeText(Selectdate.this, "MINIMUM THREE DAYS REQUIRED", Toast.LENGTH_LONG).show();
                } else {
                            Datesent dt= new Datesent();
                            dt.execute(nowadays);
                    Toast.makeText(Selectdate.this, String.valueOf(nowadays) + "days messcut", Toast.LENGTH_LONG).show();

                    Intent activity3 = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(activity3);
                }
            }

        });


    }

    class  Datesent extends AsyncTask {
        String result;

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            result="";
            String na= (String) objects[0];
            String ag= (String) objects[1];
            String ph= (String) objects[2];
            String ps= (String) objects[3];

            String conn=Configuration.partial_link+"register.php";
            try {
                URL url=new URL(conn);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops=http.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(na,"UTF-8") + "&&" +
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(ag,"UTF-8") + "&&" +
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(ph,"UTF-8") + "&&" +
                        URLEncoder.encode("psw","UTF-8")+"="+URLEncoder.encode(ps,"UTF-8") ;

                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips=http.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
                String line="";
                while ((line =reader.readLine())!=null){
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
            } catch (MalformedURLException e) {
                result=e.getMessage();
            } catch (IOException e) {
                result=e.getMessage();
            }
            return result;
        }

    }
}