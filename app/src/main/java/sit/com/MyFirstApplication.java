package sit.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.os.Bundle;
        import android.support.constraint.ConstraintLayout;
        import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.PopupWindow;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONException;
        import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
        import java.util.Map;


public class MyFirstApplication extends AppCompatActivity {
    String url="http://13.232.84.108/sit_android_app/login.php";
    Button closePopupBtn;
    PopupWindow popupWindow;
    ConstraintLayout constraintLayout1;
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    EditText Number;
    EditText Password;
    //  EditText Email;
    Button Login;
    TextView Reg;
    ProgressDialog progressDialog;
    TextView Info;
    Cursor cursor;
    int counter = 3;
    //RegisterActivity loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfapp_main);

      //  constraintLayout1= (ConstraintLayout) findViewById(R.id.constrantone);
        // openHelper=new DatabaseHelper(this);
        //  db=openHelper.getReadableDatabase();
        Number = (EditText) findViewById(R.id.mobile);
        Password = (EditText)findViewById(R.id.userpassword);
        //  Email=(EditText)findViewById(R.id.Reg
        Reg = (TextView)findViewById(R.id.tvsign);
        Login=(Button)findViewById(R.id.login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent;
                String number = Number.getText().toString();
                String pass = Password.getText().toString();


                if(Number.getText().toString().length()==0)
                {
                    Number.setError("Field is not be empty");
                    return;
                }
                if(Password.getText().toString().length()==0)
                {
                    Password.setError("Field is not be empty");
                    return;
                }

                Validating(number,pass);

            }
        });
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MyFirstApplication.this, SignUp.class);
                // register_activity main=new register_activity();
                //main.getLocation();
                startActivity(intent);
            }
        });

    }
    private void Validating(final String number, final String pass){
        //    Toast.makeText(login_activity.this, name, Toast.LENGTH_SHORT).show();
        //  progressDialog=new ProgressDialog(this);
        //progressDialog.setTitle("Validating...");
        //progressDialog.setMax(3000);
        // progressDialog.show();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://13.232.84.108/sit_android_app/login.php";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("mobile", number);
            jsonBody.put("password", pass);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    /*Log.i("LOG_VOLLEY", response);*/
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String val=jsonObject.getString("message");
                        // Toast.makeText(MyFirstApplication.this,"",Toast.LENGTH_LONG).show();

                        if(val.equals("Login success")){
                            Intent intent=new Intent(MyFirstApplication.this,SecondActivity.class);
                            startActivity(intent);
                            Toast.makeText(MyFirstApplication.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Enter Valid Details", Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

