package com.vemser.rest.data.factory;

import com.vemser.rest.model.Produto;
import net.datafaker.Faker;

import java.util.Locale;

public class ProdutoDataFactory
{
    static Faker faker = new Faker(new Locale("PT-BR"));

    private static Produto NovoProduto()
    {
        Produto produto = new Produto();
        produto.setNome(faker.commerce().productName());
        produto.setDescricao(faker.lorem().sentence());
        produto.setPreco(faker.number().numberBetween(1, 100));
        produto.setQuantidade(faker.number().numberBetween(1, 100));

        return produto;
    }

    public static Produto ProdutoEmBranco()
    {
        Produto produto = new Produto();
        produto.setNome("");
        produto.setDescricao("");
        produto.setPreco(0);
        produto.setQuantidade(0);

        return produto;
    }

    public static Produto ProdutoSemNome()
    {
        Produto produto = new Produto();
        produto.setNome("");
        return produto;
    }

    public static Produto ProdutoComNomeFixc()
    {
        Produto produto = new Produto();
        produto.setNome("P1");
        return produto;
    }

    public static Produto gerarProdutoValido() {
        Produto produto = new Produto();
        produto.setNome(faker.commerce().productName());
        produto.setDescricao(faker.lorem().sentence());
        produto.setPreco(faker.number().randomDouble(2, 1, 100));
        produto.setQuantidade(faker.number().numberBetween(1, 100));
        return produto;
    }

    public static Produto gerarProdutoSemNome() {
        Produto produto = new Produto();
        produto.setNome("");
        produto.setDescricao(faker.lorem().sentence());
        produto.setPreco(faker.number().randomDouble(2, 1, 100));
        produto.setQuantidade(faker.number().numberBetween(1, 100));
        return produto;
    }

    public static Produto gerarProdutoComPrecoNegativo() {
        Produto produto = new Produto();
        produto.setNome(faker.commerce().productName());
        produto.setDescricao(faker.lorem().sentence());
        produto.setPreco(-10.0);
        produto.setQuantidade(faker.number().numberBetween(1, 100));
        return produto;
    }

    public static Produto ProdutoValido()
    {
        return NovoProduto();
    }
}
