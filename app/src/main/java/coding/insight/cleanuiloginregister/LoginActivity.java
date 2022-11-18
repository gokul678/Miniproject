package coding.insight.cleanuiloginregister;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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


public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button log_in,sign_up;
    TextView hospital_log_in,local_body_log_in,admin_log_in;
    String result="",user,pas,auth_id,auth_name,sem,dep;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        log_in=(Button) findViewById(R.id.log_in);


//        sign_up=(Button) findViewById(R.id.sign_up);
//        hospital_log_in=(TextView) findViewById(R.id.hospital_log_in);
//        local_body_log_in=(TextView) findViewById(R.id.local_body_log_in);
//        admin_log_in=(TextView) findViewById(R.id.admin_log_in);
    }

       public void signUp(View v){
      Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
       startActivity(intent);
    }
    public void goToHome(View v){
        user=username.getText().toString();
        pas=password.getText().toString();
        LogInBackend lb=new LogInBackend();
        lb.execute(user,pas);
    }



    //    public void goToLBLogIn(View v){
//        Intent intent = new Intent(getApplicationContext(), LocalBodyLogInActivity.class);
//        startActivity(intent);
//    }
//    public void goToAdminLogIn(View v){
//        Intent intent = new Intent(getApplicationContext(), AdminLogInActivity.class);
//        startActivity(intent);
//    }
    class  LogInBackend extends AsyncTask {

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
            if(result.equals("success")) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);


                intent.putExtra("x", auth_name);
                intent.putExtra("y",sem);
                intent.putExtra("z",dep);
                intent.putExtra("w",auth_id);

//                Toast.makeText(getApplicationContext(),auth_name, Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),sem, Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),dep, Toast.LENGTH_LONG).show();

                startActivity(intent);

//                Intent intentd = new Intent(getApplicationContext(), Selectdate.class);
//                 intentd.putExtra("w",auth_id);
            }

            else if(result.equals("failure")){
                Toast.makeText(getApplicationContext(),"Authentication Failed", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Something Went Wrong"+result, Toast.LENGTH_LONG).show();
            }
//            Intent intentd = new Intent(getApplicationContext(), HomeActivity.class);



        }

//        Intent intentd = new Intent(getApplicationContext(), HomeActivity.class);
//                 intentd.putExtra("w",auth_id);


        @Override
        protected Object doInBackground(Object[] objects) {

            result="";
            auth_name="";
            dep="";
            sem="";
            auth_id="";
            String phone= (String) objects[0];
            String psw= (String) objects[1];

            String conn=Configuration.partial_link+"login.php";
            try {
                URL url=new URL(conn);
                HttpURLConnection http= (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops=http.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String data= URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8") + "&&" +
                        URLEncoder.encode("psw","UTF-8")+"="+URLEncoder.encode(psw,"UTF-8") ;
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips=http.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
//                String line="";
//                while ((line =reader.readLine())!=null){
//                    result += line;
//                }

                result= reader.readLine();
                auth_name= reader.readLine();
                sem= reader.readLine();
                dep= reader.readLine();
                auth_id=reader.readLine();
                reader.close();
                ips.close();
                http.disconnect();
            } catch (MalformedURLException e) {
                result=e.getMessage();
            } catch (IOException e) {
                result=e.getMessage();
            }
            return null;
        }

    }



        // Set the request code to any code you like, you can identify the
        // callback via this code

    }


