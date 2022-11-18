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

import org.json.JSONArray;

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

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("h");
        Toast.makeText(Selectdate.this, value, Toast.LENGTH_LONG).show();
        Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.NOVEMBER,-1);
        calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, 14);
        Date tomorrow = calendar.getTime();
        calendarPickerView.init(lastYear.getTime(),tomorrow)
                .withSelectedDate(today)
                .inMode(CalendarPickerView.SelectionMode.RANGE);
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
                Log.d("newFormatDate2", sdf.format(selectedDates.get(selectedDates.size() - 1)));
//                Toast.makeText(Selectdate.this, selectedDates.toString(),
//                        Toast.LENGTH_LONG).show();
//                JSONArray jsonArrayName=new JSONArray();
                int nowadays = selectedDates.size();
                 int fess=0;
                 fess=4185-(135*nowadays);
                String firstday = sdf.format(selectedDates.get(0));
                String lastday = sdf.format(selectedDates.get(selectedDates.size()-1));

                if (nowadays < 3) {
                    Toast.makeText(Selectdate.this, "MINIMUM THREE DAYS REQUIRED", Toast.LENGTH_LONG).show();
                } else {
                    SendDates sendDates=new SendDates();
                    sendDates.execute(firstday,lastday,value);
                    SendDays sendDays=new SendDays();
                    sendDays.execute(value,nowadays);
                     SendFees sendFees=new SendFees();
                     sendFees.execute(value,fess);

                    Toast.makeText(Selectdate.this, String.valueOf(nowadays) + "days messcut", Toast.LENGTH_LONG).show();

//                    Intent activity3 = new Intent(getApplicationContext(), HomeActivity.class);
//                    activity3.putExtra("fame",firstday);
//                    activity3.putExtra("lime",lastday);
//
//                    startActivity(activity3);
                    finish();
                }
            }

        });


    }


    class  SendDates extends AsyncTask {
        String result;
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Toast.makeText(getApplicationContext(),result+"hello", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            result="";
            String na= (String) objects[0];
            String ag= (String) objects[1];
            String dg=(String) objects[2];

            String conn=Configuration.partial_link+"messcut.php";
            try {
                URL url=new URL(conn);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops=http.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data= URLEncoder.encode("fdate","UTF-8")+"="+URLEncoder.encode(na,"UTF-8") + "&&" +
                        URLEncoder.encode("ldate","UTF-8")+"="+URLEncoder.encode(ag,"UTF-8")  + "&&" +
                        URLEncoder.encode("auth_id","UTF-8")+"="+URLEncoder.encode(dg,"UTF-8") ;

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
    class  SendDays extends AsyncTask {
        String result;
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Toast.makeText(getApplicationContext(),result+"hello", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            result="";
            String ab= (String) objects[0];
            int bc= (int) objects[1];


            String conn=Configuration.partial_link+"noofdays.php";
            try {
                URL url=new URL(conn);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops=http.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data= URLEncoder.encode("auth_id","UTF-8")+"="+URLEncoder.encode(ab,"UTF-8") + "&&" +
                        URLEncoder.encode("nofdays","UTF-8")+"="+URLEncoder.encode(String.valueOf(bc),"UTF-8") ;
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

    class  SendFees extends AsyncTask {
        String result;
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Toast.makeText(getApplicationContext(),result+"hello", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            result="";
            String wc= (String) objects[0];
            int wb= (int) objects[1];


            String conn=Configuration.partial_link+"fees.php";
            try {
                URL url=new URL(conn);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops=http.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data= URLEncoder.encode("auth_id","UTF-8")+"="+URLEncoder.encode(wc,"UTF-8") + "&&" +
                        URLEncoder.encode("fess","UTF-8")+"="+URLEncoder.encode(String.valueOf(wb),"UTF-8") ;
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

