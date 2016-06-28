package cmru.jairak.rath.cmrurun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, passwordEditText, userEditText;
    private RadioGroup radioGroup;
    private RadioButton avata0RadioButton, avata1RadioButton,
            avata2RadioButton, avata3RadioButton, avata4RadioButton;
    private String nameString, userString, passwordString, avataString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        radioGroup = (RadioGroup) findViewById(R.id.radAvarta);
        avata0RadioButton = (RadioButton)findViewById(R.id.radioButton);
        avata1RadioButton = (RadioButton)findViewById(R.id.radioButton2);
        avata2RadioButton = (RadioButton)findViewById(R.id.radioButton3);
        avata3RadioButton = (RadioButton)findViewById(R.id.radioButton4);
        avata4RadioButton = (RadioButton)findViewById(R.id.radioButton5);
        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        avataString= "0";
                        break;
                    case R.id.radioButton2:
                        avataString= "1";
                        break;
                    case R.id.radioButton3:
                        avataString= "2";
                        break;
                    case R.id.radioButton4:
                        avataString= "3";
                        break;
                    case R.id.radioButton5:
                        avataString= "4";
                        break;
                                    }
            } //onchecked
        });


    } // Main Method

    public void clickSignUpSign(View view) {
        // Get value from Edit Text
        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (nameString.equals("") || userString.equals("") || passwordString.equals("")) {
            // Have Space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณากรองทุกช่อง ครับ");

        } else if (checkChooseAvata()) {
            //Checked
            confirmData();

        } else {
            // Not Checked
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ยังไม่ได้เลือก Avata", "กรุณาเลือกด้วยครับ");
        }

    } // clickSign

    private void confirmData() {

        MyData myData = new MyData();
        int[] avataInts = myData.getAvataInts();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(avataInts[Integer.parseInt(avataString)]);
        builder.setTitle(nameString);
        builder.setMessage("User =" + userString + "\n" + "Password =" + passwordString);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                upLoadUserToServer();
                dialog.dismiss();
            }
        });
        builder.show();

    }  //confirmData

    private void upLoadUserToServer() {


    }

    private boolean checkChooseAvata() {

        boolean status = true;

        if (avata0RadioButton.isChecked()||
                avata1RadioButton.isChecked()||
                avata2RadioButton.isChecked()||
                avata3RadioButton.isChecked()||
                avata4RadioButton.isChecked()) {
            //Have Checked
            status = true;
        } else {
            //Not Checked
            status = false;
        }

        return status;

    }

} // Main Class
