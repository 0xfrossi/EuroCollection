package com.example.europroject.models;

public class Collection {
    private long id;
    private String nome;
    private String tipo;
    private String nota;


    public Collection(long id,String nome,String tipo, String nota){
        this.nome=nome;
        this.tipo=tipo;
        this.nota=nota;
        this.id=id;  //not in db
    }

    public Collection(String nome,String tipo, String nota){
        this.nome=nome;
        this.tipo=tipo;
        this.nota=nota;
       // this.id=-1;  //not in db
    }



    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNota() {
        return nota;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "  " +getNome() +"\n"+ "  "+ getTipo()+ "\n"+"  id: "+ getId();
    }
}
