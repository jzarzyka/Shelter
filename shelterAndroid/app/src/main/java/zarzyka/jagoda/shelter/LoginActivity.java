package zarzyka.jagoda.shelter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import zarzyka.jagoda.shelter.admin.AdminActivity;
import zarzyka.jagoda.shelter.models.LoggedInUser;
import zarzyka.jagoda.shelter.requests.user.Login;
import zarzyka.jagoda.shelter.worker.WorkerActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);

        final Button signin = findViewById(R.id.signin);
        signin.setOnClickListener(view -> signIn());

    }

    private void signIn() {
        if (TextUtils.isEmpty(login.getText().toString())) {
            login.setError(getString(R.string.empty_field));
            login.requestFocus();

        } else if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError(getString(R.string.empty_field));
            password.requestFocus();

        } else {
            loginRequest(login.getText().toString(), password.getText().toString());
        }
    }

    private void loginRequest(String getLogin, String getPassword) {
        Gson gson = new Gson();

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("login", getLogin);
        requestBody.addProperty("password", getPassword);

        Response.Listener<JSONObject> responseListener = response -> {
            try {
                LoggedInUser loginResponse = gson.fromJson(response.toString(), LoggedInUser.class);
                String role = loginResponse.getRole();

                if (!role.isEmpty()) {
                    loginResponse.saveSession(this);
                    loginResponse.saveUserLogin(this, getLogin);

                    switch (role) {
                        case "ADMIN":
                            Intent loginAdmin = new Intent(getApplicationContext(), AdminActivity.class);
                            startActivity(loginAdmin);
                            break;

                        case "SHELTER_WORKER":
                            Intent loginWorker = new Intent(getApplicationContext(), WorkerActivity.class);
                            startActivity(loginWorker);
                            break;

                        default:
                            break;
                    }

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            getApplicationContext().getString(R.string.error_happened),
                            Toast.LENGTH_SHORT
                    ).show();
                }

            } catch (Exception e) {
                Toast.makeText(
                        getApplicationContext(),
                        getApplicationContext().getString(R.string.error_happened),
                        Toast.LENGTH_SHORT
                ).show();
            }
        };

        Response.ErrorListener responseErrorListener = error -> {
            if (error instanceof TimeoutError) {
                Toast.makeText(
                        getApplicationContext(),
                        getApplicationContext().getString(R.string.timeout_error),
                        Toast.LENGTH_SHORT
                ).show();

            } else if (error instanceof NoConnectionError) {
                Toast.makeText(
                        getApplicationContext(),
                        getApplicationContext().getString(R.string.no_internet_connection),
                        Toast.LENGTH_SHORT
                ).show();

            } else {
                NetworkResponse networkResponse = error.networkResponse;

                if (networkResponse != null && networkResponse.data != null) {
                    try {
                        String jsonError = new String(networkResponse.data);
                        JSONObject jsonObject = new JSONObject(jsonError);
                        if (jsonObject.has("message")) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    getApplicationContext().getString(R.string.wrong_credentials),
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    } catch (JSONException e) {
                        Toast.makeText(
                                getApplicationContext(),
                                getApplicationContext().getString(R.string.error_happened),
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            getApplicationContext().getString(R.string.error_happened),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        };

        Login loginRequest = new Login(requestBody, responseListener, responseErrorListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
