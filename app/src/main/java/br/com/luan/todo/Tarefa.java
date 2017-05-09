package br.com.luan.todo;

import java.io.Serializable;

public class Tarefa implements Serializable {

    private Integer id;
    private String titulo;
    private String descricao;
    private Long alarme;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getAlarme() {
        return alarme;
    }

    public void setAlarme(Long alarme) {
        this.alarme = alarme;
    }
}
