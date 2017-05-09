package br.com.luan.todo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    private EditText titulo;
    private EditText descricao;
    private EditText data;
    private EditText horario;
    public int ano, mes, dia, hora, minuto;

    public FormActivity() {
        Calendar c = Calendar.getInstance();

        ano = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);
        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        titulo = (EditText) findViewById(R.id.titulo);
        descricao = (EditText) findViewById(R.id.descricao);

        data = (EditText) findViewById(R.id.data);
        horario = (EditText) findViewById(R.id.horario);

        String mesS = addZero(mes+1);
        String diaS = addZero(dia);

        data.setText(diaS + "/" + mesS + "/" + ano);

        String horaS = addZero(hora);
        String minutoS = addZero(minuto);

        horario.setText(horaS + ":" + minutoS);

    }

    private DatePickerDialog.OnDateSetListener dataSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
            ano = anoSelecionado;
            mes = mesSelecionado;
            dia = diaSelecionado;

            String mesS = addZero(mes+1);
            String diaS = addZero(dia);

            data.setText(diaS + "/" + mesS + "/" + ano);
        }
    };

    private TimePickerDialog.OnTimeSetListener horarioSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int horaSelecionada, int minutoSelecionado) {
            hora = horaSelecionada;
            minuto = minutoSelecionado;

            String horaS = addZero(hora);
            String minutoS = addZero(minuto);

            horario.setText(horaS + ":" + minutoS);
        }
    };

    public String addZero(int x){
        if(x < 10){
            return "0" + x;
        }else{
            return ""+ x;
        }
    }

    public void dataBtnClick(View view){
        showDialog(0);
    }

    public void horarioBtnClick(View view){
        showDialog(1);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 0){
            return new DatePickerDialog(this, dataSetListener, ano, mes,dia);
        }else{
            return new TimePickerDialog(this, horarioSetListener, hora, minuto, false);
        }
    }

    public void salvar(View view){
        final Tarefa t = new Tarefa();
        t.setTitulo(titulo.getText().toString());
        t.setDescricao(descricao.getText().toString());

        Calendar c = Calendar.getInstance();
        c.set(ano, mes, dia, hora, minuto);

        t.setAlarme(c.getTimeInMillis());

        new Thread(new Runnable() {
            @Override
            public void run() {
                RepositorioDeTarefas rep = new RepositorioDeTarefas(FormActivity.this);
                Long id = rep.inserirTarefa(t);
                rep.close();

                t.setId(id.intValue());

                Intent intencao = new Intent(FormActivity.this, MeuReceiver.class);
                intencao.putExtra("tarefa", t);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(FormActivity.this, t.getId(), intencao, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, t.getAlarme(), pendingIntent);

                voltarParaLista();
            }
        }).start();
    }

    public void voltarParaLista(){
        Intent retorno = new Intent();
        setResult(RESULT_OK, retorno);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
