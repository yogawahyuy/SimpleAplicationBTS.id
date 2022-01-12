package com.snystudio.simpleaplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.snystudio.simpleaplication.R;
import com.snystudio.simpleaplication.config.ApiConfig;
import com.snystudio.simpleaplication.config.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateChecklistActivity extends AppCompatActivity {
    EditText name;
    Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_checklist);
        name=findViewById(R.id.name);
        btnCreate=findViewById(R.id.btncreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save(){
        SessionManager sessionManager=new SessionManager(CreateChecklistActivity.this);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name",name.getText().toString());
        Call<JsonObject> result=ApiConfig.getApiServices().saveChecklist("Bearer "+sessionManager.fetchToken(CreateChecklistActivity.this,"token"),jsonObject);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("hasil", "onResponse: "+response.body().toString());
                try {
                    JSONObject root=new JSONObject(response.body().toString());
                    int code=root.getInt("statusCode");
                    if (code==2000){
                        Intent intent=new Intent(CreateChecklistActivity.this,ChecklistActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}