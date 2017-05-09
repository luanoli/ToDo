package br.com.luan.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class MeuReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Tarefa tarefa = (Tarefa) intent.getSerializableExtra("tarefa");

        Intent intentNotificacao = new Intent("NOTIFICACAO");
        intentNotificacao.putExtra("tarefa", tarefa);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, tarefa.getId(), intentNotificacao, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification n = new Notification.Builder(context)
                .setTicker("tarefa")
                .setContentTitle("ToDo")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(tarefa.getTitulo() + " - " + tarefa.getDescricao())
                .setWhen(tarefa.getAlarme())
                .setAutoCancel(true)
                .build();

        n.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        nm.notify(tarefa.getId(), n);
    }
}
