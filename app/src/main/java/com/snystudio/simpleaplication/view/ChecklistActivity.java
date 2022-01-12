package com.snystudio.simpleaplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.snystudio.simpleaplication.R;
import com.snystudio.simpleaplication.config.ApiConfig;
import com.snystudio.simpleaplication.config.SessionManager;
import com.snystudio.simpleaplication.model.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChecklistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ListModel> listModels=new ArrayList<>();
    ChecklistAdapter mAdapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        recyclerView=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChecklistActivity.this,CreateChecklistActivity.class);
                startActivity(intent);

            }
        });
        getCheckList();

    }


    private void setAdapter(){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter=new ChecklistAdapter(this,listModels);
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new ChecklistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ListModel model) {
                //Intent intent=new Intent(ChecklistActivity.this)

            }
        });
        mAdapter.setOnLongClickListener(new ChecklistAdapter.OnItemLongClickListener() {
            @Override
            public void onItemClick(View view, int position, ListModel model) {
                deleteChecklist(model.getId());
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    private void getCheckList(){
        SessionManager sessionManager=new SessionManager(ChecklistActivity.this);
        Call<JsonObject> result= ApiConfig.getApiServices().getAllChecklist("Bearer "+sessionManager.fetchToken(ChecklistActivity.this,"token"));
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject root=new JSONObject(response.body().toString());
                    JSONArray data=root.getJSONArray("data");
                    for (int i = 0; i <data.length() ; i++) {
                        JSONObject datanya=data.getJSONObject(i);
                        ListModel listModel=new ListModel();
                        listModel.setNameList(datanya.getString("name"));
                        listModel.setId(datanya.getInt("id"));
                        listModel.setItems(datanya.getString("items"));
                        listModel.setChecklistStatus(datanya.getBoolean("checklistCompletionStatus"));
                        Log.d("hasil", "onResponse: "+datanya.getString("name"));
                        listModels.add(listModel);
                    }
                    setAdapter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("hasil", "onResponse: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void deleteChecklist(int id){
        SessionManager sessionManager=new SessionManager(ChecklistActivity.this);
        Call<JsonObject> result=ApiConfig.getApiServices().deleteChceklist("Bearer "+sessionManager.fetchToken(ChecklistActivity.this,"token"),id);
        result.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Toast.makeText(ChecklistActivity.this, "Terhapus", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}