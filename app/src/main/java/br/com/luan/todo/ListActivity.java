package br.com.luan.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private List<Tarefa> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer idTarefa = lista.get(position).getId();
                RepositorioDeTarefas rep = new RepositorioDeTarefas(ListActivity.this);
                rep.excluirTarefa(idTarefa);
                onResume();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                RepositorioDeTarefas rep = new RepositorioDeTarefas(ListActivity.this);
                lista = rep.buscarTarefas();
                rep.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new ListAdapter(ListActivity.this, lista));
                    }
                });
            }
        }).start();

        registerReceiver(listaBroadcastReceiver, new IntentFilter("LISTA"));
    }

    public void novaTarefa(View view){
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }

    private BroadcastReceiver listaBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onResume();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(listaBroadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        unregisterReceiver(listaBroadcastReceiver);
    }
}
