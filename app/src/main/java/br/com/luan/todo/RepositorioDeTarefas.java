package br.com.luan.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDeTarefas {

    private SQLiteHelper dbHelper;
    private SQLiteDatabase db;

    public RepositorioDeTarefas(Context ctx) {
        dbHelper = new SQLiteHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public Long inserirTarefa (Tarefa tarefa) {
        ContentValues valores = new ContentValues();

        valores.put("titulo", tarefa.getTitulo());
        valores.put("descricao", tarefa.getDescricao());
        valores.put("alarme", tarefa.getAlarme());

        return db.insert("tarefas", null, valores);
    }

    public void excluirTarefa (Integer id) {
        String[] idTarefa = new String[] {"" + id};
        db.delete("tarefas", "id = ?", idTarefa);
    }

    public List<Tarefa> buscarTarefas (){
        Cursor c = db.query("tarefas", new String[]{"id", "titulo", "descricao", "alarme"}, null, null, null, null, null);

        List<Tarefa> lista = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                Tarefa t = new Tarefa();
                t.setId(c.getInt(c.getColumnIndex("id")));
                t.setTitulo(c.getString(c.getColumnIndex("titulo")));
                t.setDescricao(c.getString(c.getColumnIndex("descricao")));
                t.setAlarme(c.getLong(c.getColumnIndex("alarme")));
                lista.add(t);
            }while(c.moveToNext());
        }
        return lista;
    }

    public void close() {
        if (db != null) {
            db.close();
        }

        if (dbHelper != null) {
            dbHelper.close();
        }
    }

}
