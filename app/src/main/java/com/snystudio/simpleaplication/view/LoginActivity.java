package com.snystudio.simpleaplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.snystudio.simpleaplication.MainActivity;
import com.snystudio.simpleaplication.R;
import com.snystudio.simpleaplication.config.ApiConfig;
import com.snystudio.simpleaplication.config.ApiService;
import com.snystudio.simpleaplication.config.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText eTusername,eTpassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eTusername=findViewById(R.id.username_login);
        eTpassword=findViewById(R.id.password_login);
        btnLogin=findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionLogin();
            }
        });
    }

    private void actionLogin(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("password",eTpassword.getText().toString());
        jsonObject.addProperty("username",eTusername.getText().toString());

        Call<JsonObject> result=ApiConfig.getApiServices().loginRawJSON(jsonObject);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject root=new JSONObject(response.body().toString());
                    JSONObject data=root.getJSONObject("data");
                    String token=data.getString("token");
                    Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    SessionManager sessionManager=new SessionManager(LoginActivity.this);
                    sessionManager.saveToken(LoginActivity.this,"token",token);
                   // getCheckList();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Intent intent=new Intent(LoginActivity.this, ChecklistActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "gagal on failur", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCheckList(){
        SessionManager sessionManager=new SessionManager(LoginActivity.this);
        Call<JsonObject> result=ApiConfig.getApiServices().getAllChecklist("Bearer "+sessionManager.fetchToken(LoginActivity.this,"token"));
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("hasil", "onResponse: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}