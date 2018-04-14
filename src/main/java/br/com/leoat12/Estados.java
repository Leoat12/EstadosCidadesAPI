package br.com.leoat12;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="EstadosCidades")
public class Estados{

    private String sigla;
    private String nome;
    private List<String> cidades;

    @DynamoDBHashKey
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getCidades() {
        return cidades;
    }

    public void setCidades(List<String> cidades) {
        this.cidades = cidades;
    }

    @Override
    public String toString() {
        return "[ sigla = " + this.sigla + " nome = " + this.nome + " cidades = " + this.cidades + " ]"; 
    }
}