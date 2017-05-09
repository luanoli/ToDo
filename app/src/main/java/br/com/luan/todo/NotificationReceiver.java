package br.com.luan.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final Tarefa tarefa = (Tarefa) intent.getSerializableExtra("tarefa");

        new Thread(new Runnable() {
            @Override
            public void run() {
                RepositorioDeTarefas rep = new RepositorioDeTarefas(context);
                rep.excluirTarefa(tarefa.getId());
                rep.close();
                Intent intentLista = new Intent("LISTA");
                context.sendBroadcast(intentLista);
            }
        }).start();

    }
}
