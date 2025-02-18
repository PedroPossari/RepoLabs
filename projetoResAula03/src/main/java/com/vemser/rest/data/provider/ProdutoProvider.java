package com.vemser.rest.data.provider;

import com.vemser.rest.data.factory.ProdutoDataFactory;
import net.datafaker.Faker;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Locale;
import java.util.stream.Stream;



public class ProdutoProvider {

    private static final Faker faker = new Faker(new Locale("pt-BR"));
    private static final ProdutoDataFactory produto = new ProdutoDataFactory();
    public static Stream<Arguments> produtoProvider() {
        return Stream.of(
                Arguments.of(produto.gerarProdutoValido(), "Produto válido"),
                Arguments.of(produto.gerarProdutoSemNome(), "Nome do produto não pode estar vazio"),
                Arguments.of(produto.gerarProdutoComPrecoNegativo(), "Preço do produto não pode ser negativo")
        );
    }

}
