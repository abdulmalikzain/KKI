package abdul.malik.intaihere;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import abdul.malik.intaihere.Utils.RequestHandler;
import abdul.malik.intaihere.Utils.Server;

import static abdul.malik.intaihere.LoginActivity.TAG_EMAIL;
import static abdul.malik.intaihere.LoginActivity.TAG_USERNAME;
import static abdul.malik.intaihere.LoginActivity.my_shared_preferences;
import static abdul.malik.intaihere.LoginActivity.session_status;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUsername, editTextEmail, editTextPassword, editTextTelephone;


//    private static final String TAG_USERNAME = "username";
//    private static final String TAG_EMAIL = "email";
//    private static final String TAG_ID = "id";
    SharedPreferences sharedpreferences;
//
//    public static final String my_shared_preferences = "my_shared_preference";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //if the user is already logged in we will directly start the profile activity
            if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        editTextUsername = (EditText) findViewById(R.id.txt_usernamereg);
        editTextEmail = (EditText) findViewById(R.id.txtEmailAddress);
        editTextPassword = (EditText) findViewById(R.id.txt_passwordreg);
        editTextTelephone = (EditText) findViewById(R.id.txt_telephonereg);


        findViewById(R.id.btn_registerreg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        findViewById(R.id.btn_loginreg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String telephone = editTextTelephone.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("telephone", telephone);

                //returing the response
                return requestHandler.sendPostRequest(Server.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
//                        User user = new User(
//                                userJson.getString("id"),
//                                userJson.getString("username"),
//                                userJson.getString("email"),
//                                userJson.getString("telephone"),
//                                userJson.getString("latitude"),
//                                userJson.getString("longitude"),
//                                userJson.getString("image")
//                        );

                        // menyimpan login ke session
//                        sharedpreferences = getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putBoolean(session_status, true);
////                        editor.putString(TAG_ID, id);
//                        editor.putString(TAG_EMAIL, email);
//                        editor.putString(TAG_USERNAME, username);
////                        editor.putString("alamat", alamat);
//                        editor.putString("telephone", telephone);
//                        editor.commit();
                        //storing the user in shared preferences
//                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Pengguna Sudah terdaftar", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
