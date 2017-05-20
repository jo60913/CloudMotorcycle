package com.example.cloudclient.clients;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


import java.util.List;
import java.util.logging.Filter;

public class ClientRecord extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ClientDB clientDB;
    private SQLiteDatabase SD;
    SimpleCursorAdapter adapter;
    ListView clientlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_record);
        setTitle("客戶紀錄");
        clientDB = ClientDB.getInstance(this);
        SD = clientDB.getReadableDatabase();
        Cursor c = SD.query("client",null,null,null,null,null,null);

        clientlist = (ListView)findViewById(R.id.clientList);
        adapter = new SimpleCursorAdapter(this,R.layout.clientlist,c,new String[]{"name","phone","vehicle","plate","milage"},new int[]{R.id.clientname,R.id.clientplate,R.id.clientvehicle,R.id.clientinfo,R.id.clientmilage},0);
        clientlist.setAdapter(adapter);
        clientlist.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        SearchManager searchmanager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchview = (SearchView)menu.findItem(R.id.search).getActionView();
        searchview.setSearchableInfo(searchmanager.getSearchableInfo(getComponentName()));
        searchview.setSubmitButtonEnabled(true);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*Toast.makeText(ClientRecord.this, getApplicationContext().toString(), Toast.LENGTH_SHORT).show();
                //Cursor c = SD.query("client",null,"name=? or vehicle=? or plate=?",new String[]{query,query,query},null,null,null);
                Cursor c = SD.query("client",null,null,null,null,null,null);
                adapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.clientlist,c,new String[]{"name","phone","vehicle","plate"},new int[]{R.id.clientname,R.id.clientplate,R.id.clientvehicle,R.id.clientinfo},0);
                clientlist.setAdapter(adapter);*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*clientlist.setAdapter(adapter);
                android.widget.Filter filter = adapter.getFilter();
                filter.filter("黃偉哲");
                adapter.getFilter().filter(newText.toString());*/
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add_client)
        {
            Intent it = new Intent(this,AddClient.class);
            startActivityForResult(it,10);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bag = new Bundle();
        bag.putLong("ClientID",id);
        Intent it = new Intent(this,Client.class);
        it.putExtras(bag);
        startActivity(it);
    }

    @Override
    protected void onResume() {
        super.onResume();
        clientDB = ClientDB.getInstance(this);
        SD = clientDB.getReadableDatabase();
        Cursor c = SD.query("client",null,null,null,null,null,null);
        adapter = new SimpleCursorAdapter(this,R.layout.clientlist,c,new String[]{"name","phone","vehicle","plate","milage"},new int[]{R.id.clientname,R.id.clientplate,R.id.clientvehicle,R.id.clientinfo,R.id.clientmilage},0);
        clientlist.setAdapter(adapter);
    }
}
