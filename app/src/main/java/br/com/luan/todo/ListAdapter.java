package br.com.luan.todo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListAdapter extends BaseAdapter{

    private String[] planetas = new String[]{"Mercurio", "Venus", "Terra", "Marte", "Jupiter", "Saturno", "Netuno", "Plutão"};
    private List<Tarefa> lista;
    private Context context;

    public ListAdapter(Context context, List<Tarefa> lista){
        super();
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tarefa t = lista.get(position);

        TextView text = new TextView(context);

        float dip = 60;
        float densidade = context.getResources().getDisplayMetrics().density;
        int px = (int) (dip * densidade + 0.5f);
        text.setHeight(px);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t.getAlarme());

        String titulo = t.getTitulo().toString();
        String descricao = t.getDescricao().toString();
        String dia = addZero(c.get(Calendar.DAY_OF_MONTH));
        String mes = addZero(c.get(Calendar.MONTH) + 1);
        String ano = c.get(Calendar.YEAR) + "";
        String hora = addZero(c.get(Calendar.HOUR_OF_DAY));
        String minuto = addZero(c.get(Calendar.MINUTE));

        text.setText(dia + "/" + mes + "/" + ano + " - " + hora + ":" + minuto + " - Título: " + titulo + " - Descrição: " + descricao);
        return text;
    }

    public String addZero(int x){
        if(x < 10){
            return "0" + x;
        }else{
            return ""+ x;
        }
    }
}
