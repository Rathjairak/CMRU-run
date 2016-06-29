package cmru.jairak.rath.cmrurun;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private static final String urlLogo = "http://swiftcodingthai.com/cmru/cmru_logo.png";
    private static final String urlJSON = "http://swiftcodingthai.com/cmru/php_get_user_rathjairak.php";
    private ImageView imageView;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView6);
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);

        //Load Logo
        Picasso.with(this).load(urlLogo).resize(150, 180).into(imageView);

    }  // Main Method

    //Create Inner Class
    private class SynUser extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;
        private String strURL;
        private boolean statusABoolean = true;
        private String truePasswordString, nameUserString;


// สร้าง Constructor Alt + Insert

        public SynUser(Context context, String strURL) {
            this.context = context;
            this.strURL = strURL;
        }

        @Override // error จาก Internet ติดๆ ดับๆ รายงาน error exception e ยอมรับได้
        protected String doInBackground(Void... params) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("29June", "e doInBack ==>" + e.toString());
                return null;
            }


        } // doInBack

        //overide method กด Alt + Int เลือก overide method


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("29June", "JSON ==>" + s);
            // ติดตามการทำงานได้โดยกด Alt 6 กดติดตาม Debug 29 June
            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (userString.equals(jsonObject.getString("User"))) {

                        statusABoolean = false;
                        truePasswordString = jsonObject.getString("Password");
                        nameUserString = jsonObject.getString("Name");
                    }//if

                } //for
                if (statusABoolean) {
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "ไม่มี User นี้","ไม่มี"+userString+"ในฐานข้อมูลเรา");

                } else if (passwordString.equals(truePasswordString)){
                    //password true
                    Toast.makeText(context, "Welcome" +nameUserString, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("Name", nameUserString);
                    startActivity(intent);
                    finish();

                } else {
                    // password fail
                    MyAlert myAlert = new MyAlert();
                    myAlert.myDialog(context, "Password False", "Please Try Again");
                }



            } catch (Exception e) {
                Log.d("29June", "e onPost ==>" + e.toString());
            }

        } // onPost
    } // SynUser Class


    public void clickSignIn(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรอกข้อความ");
        } else {
            CheckUserPassword();
        }

    }

    private void CheckUserPassword() {
        SynUser synUser = new SynUser(this, urlJSON);
        synUser.execute();
    }

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }
}   // Main Class
