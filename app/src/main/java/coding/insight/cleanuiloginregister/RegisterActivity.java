package coding.insight.cleanuiloginregister;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


public class RegisterActivity extends AppCompatActivity {

    EditText name1,email,ph_no1,psw1;
    Button cirRegisterButton;
    TextView title;
    String result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name1=(EditText) findViewById(R.id.name1);
        email=(EditText) findViewById(R.id.email);
        ph_no1=(EditText) findViewById(R.id.ph_no1);
        psw1=(EditText) findViewById(R.id.psw1);
        cirRegisterButton=(Button) findViewById(R.id.cirRegisterButton);
        title=(TextView) findViewById(R.id.title);
    }
    public void backToMain(View v){
        String na=name1.getText().toString();
        String ag=email.getText().toString();
        String ph=ph_no1.getText().toString();
        String ps=psw1.getText().toString();

        SignUpBackend1 bg= new SignUpBackend1();
        bg.execute(na,ag,ph,ps);
        finish();
    }
    class  SignUpBackend1 extends AsyncTask {

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
