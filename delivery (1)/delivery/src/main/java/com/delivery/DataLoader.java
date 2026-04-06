package com.delivery;

import com.delivery.model.Categoria;
import com.delivery.model.Prato;
import com.delivery.repository.CategoriaRepository;
import com.delivery.repository.PratoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final PratoRepository pratoRepository;

    public DataLoader(CategoriaRepository categoriaRepository, PratoRepository pratoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.pratoRepository = pratoRepository;
    }

    @Override
    public void run(String... args) {
        Categoria lanches = new Categoria();
        lanches.setNome("Lanches");
        lanches.setDescricao("Hambúrgueres e sanduíches");
        categoriaRepository.save(lanches);

        Categoria bebidas = new Categoria();
        bebidas.setNome("Bebidas");
        bebidas.setDescricao("Refrigerantes, sucos e água");
        categoriaRepository.save(bebidas);

        Categoria sobremesas = new Categoria();
        sobremesas.setNome("Sobremesas");
        sobremesas.setDescricao("Doces e sorvetes");
        categoriaRepository.save(sobremesas);

        Prato xBurguer = new Prato();
        xBurguer.setNome("X-Burguer");
        xBurguer.setDescricao("Pão, hambúrguer 150g, queijo, alface e tomate");
        xBurguer.setPreco(new BigDecimal("18.90"));
        xBurguer.setDisponivel(true);
        xBurguer.setCategoria(lanches);
        pratoRepository.save(xBurguer);

        Prato xBacon = new Prato();
        xBacon.setNome("X-Bacon");
        xBacon.setDescricao("Pão, hambúrguer 150g, bacon, queijo e maionese especial");
        xBacon.setPreco(new BigDecimal("22.90"));
        xBacon.setDisponivel(true);
        xBacon.setCategoria(lanches);
        pratoRepository.save(xBacon);

        Prato veggie = new Prato();
        veggie.setNome("Veggie Burguer");
        veggie.setDescricao("Hambúrguer de grão-de-bico, rúcula e tomate seco");
        veggie.setPreco(new BigDecimal("20.90"));
        veggie.setDisponivel(false);
        veggie.setCategoria(lanches);
        pratoRepository.save(veggie);

        Prato refri = new Prato();
        refri.setNome("Refrigerante Lata");
        refri.setDescricao("Coca-Cola, Guaraná ou Fanta 350ml");
        refri.setPreco(new BigDecimal("6.00"));
        refri.setDisponivel(true);
        refri.setCategoria(bebidas);
        pratoRepository.save(refri);

        Prato suco = new Prato();
        suco.setNome("Suco Natural");
        suco.setDescricao("Laranja, Maracujá ou Abacaxi 400ml");
        suco.setPreco(new BigDecimal("9.00"));
        suco.setDisponivel(true);
        suco.setCategoria(bebidas);
        pratoRepository.save(suco);

        Prato brownie = new Prato();
        brownie.setNome("Brownie com Sorvete");
        brownie.setDescricao("Brownie quente com sorvete de creme e calda de chocolate");
        brownie.setPreco(new BigDecimal("14.90"));
        brownie.setDisponivel(true);
        brownie.setCategoria(sobremesas);
        pratoRepository.save(brownie);

        System.out.println("===========================================");
        System.out.println("  Dados iniciais carregados com sucesso!");
        System.out.println("  Acesse: http://localhost:8080/api/categorias");
        System.out.println("  H2 Console: http://localhost:8080/h2-console");
        System.out.println("===========================================");
    }
}
