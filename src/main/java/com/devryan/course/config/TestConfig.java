package com.devryan.course.config;

import com.devryan.course.entities.*;
import com.devryan.course.entities.enums.OrderStatus;
import com.devryan.course.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Configuration
@Profile({"test"})
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() >= 50) {
            return;
        }

        // ==========================================
        // 1. CATEGORIAS (6 categorias)
        // ==========================================
        Category catElectronics = new Category(null, "Eletrônicos");
        Category catBooks = new Category(null, "Livros");
        Category catComputers = new Category(null, "Computadores");
        Category catGames = new Category(null, "Games");
        Category catAccessories = new Category(null, "Acessórios");
        Category catOffice = new Category(null, "Móveis e Escritório");

        categoryRepository.saveAll(Arrays.asList(
                catElectronics, catBooks, catComputers, catGames, catAccessories, catOffice
        ));

        // ==========================================
        // 2. PRODUTOS (50 produtos variados com categorias)
        // ==========================================
        List<Product> products = new ArrayList<>();

        // Livros (1 a 10)
        products.add(createProduct("Clean Code", "Manual de boas práticas de programação ágil", 120.0, "", catBooks));
        products.add(createProduct("Design Patterns: Gang of Four", "Padrões clássicos de software orientado a objetos", 185.0, "", catBooks));
        products.add(createProduct("Domain-Driven Design", "Atacando as complexidades no coração do software", 195.5, "", catBooks));
        products.add(createProduct("The Pragmatic Programmer", "Sua jornada rumo à maestria na programação", 140.0, "", catBooks));
        products.add(createProduct("Refactoring", "Aperfeiçoando o design de código existente", 160.0, "", catBooks));
        products.add(createProduct("O Senhor dos Anéis: A Sociedade do Anel", "Clássico da literatura fantástica", 79.9, "", catBooks));
        products.add(createProduct("O Hobbit", "A lendária aventura de Bilbo Bolseiro", 49.9, "", catBooks));
        products.add(createProduct("Duna", "A obra-prima da ficção científica de Frank Herbert", 85.0, "", catBooks));
        products.add(createProduct("Algoritmos: Teoria e Prática", "Livro de referência acadêmica sobre algoritmos", 250.0, "", catBooks, catComputers));
        products.add(createProduct("Arquitetura Limpa", "O guia do artesão para estrutura e design de software", 110.0, "", catBooks));

        // Computadores & Hardware (11 a 20)
        products.add(createProduct("MacBook Pro 16 M3 Max", "Apple Silicon com 36GB de memória unificada e 1TB SSD", 24999.0, "", catComputers, catElectronics));
        products.add(createProduct("MacBook Air 13 M2", "Design ultrafino, bateria para 18 horas e 256GB SSD", 7999.0, "", catComputers, catElectronics));
        products.add(createProduct("Dell XPS 15", "Processador Intel Core i7, 32GB RAM e tela InfinityEdge 4K", 13499.0, "", catComputers));
        products.add(createProduct("Lenovo ThinkPad E14", "Notebook corporativo durável com AMD Ryzen 7 e 16GB RAM", 4599.0, "", catComputers));
        products.add(createProduct("Monitor Dell UltraSharp 27 4K", "Monitor profissional IPS com hub USB-C e calibração sRGB", 3199.0, "", catComputers, catElectronics));
        products.add(createProduct("Monitor Gamer LG UltraGear 27", "Taxa de atualização de 240Hz, 1ms IPS e G-Sync", 1699.0, "", catComputers, catGames));
        products.add(createProduct("Teclado Mecânico Keychron K2", "Teclado mecânico compacto sem fio com switches Gateron", 680.0, "", catComputers, catAccessories));
        products.add(createProduct("Mouse Sem Fio Logitech MX Master 3S", "Sensor Darkfield 8000 DPI com cliques silenciosos", 549.0, "", catComputers, catAccessories));
        products.add(createProduct("SSD NVMe Samsung 990 Pro 2TB", "Velocidade de leitura de 7450 MB/s PCIe 4.0", 1199.0, "", catComputers, catAccessories));
        products.add(createProduct("Memória RAM Corsair 32GB DDR5", "Kit dual channel 6000MHz para alto desempenho", 799.0, "", catComputers));

        // Eletrônicos & Áudio (21 a 30)
        products.add(createProduct("Smart TV Samsung 55 OLED 4K", "Painel OLED com pretos perfeitos e taxa de 120Hz", 5499.0, "", catElectronics));
        products.add(createProduct("Smart TV LG OLED 65 C3", "Processador Alpha 9 G6 com recursos avançados de cinema", 8899.0, "", catElectronics));
        products.add(createProduct("Fone Bluetooth Sony WH-1000XM5", "Cancelamento de ruído com processador duplo e 30h de bateria", 2199.0, "", catElectronics, catAccessories));
        products.add(createProduct("Apple AirPods Pro 2", "Cancelamento ativo de ruído duas vezes superior e USB-C", 1899.0, "", catElectronics, catAccessories));
        products.add(createProduct("Caixa de Som JBL Charge 5", "Som potente com powerbank integrado e proteção IP67", 899.0, "", catElectronics, catAccessories));
        products.add(createProduct("Soundbar JBL Bar 500", "Potência total de 590W com tecnologia Multibeam e Dolby Atmos", 2699.0, "", catElectronics));
        products.add(createProduct("iPhone 15 Pro 128GB", "Estrutura em titânio aeroespacial e câmera de 48MP", 6899.0, "", catElectronics));
        products.add(createProduct("Samsung Galaxy S24 Ultra 256GB", "Corpo em titânio, câmera de 200MP e recursos Galaxy AI", 6499.0, "", catElectronics));
        products.add(createProduct("Kindle Paperwhite 16GB", "Tela antirreflexo de 6.8 polegadas com temperatura ajustável", 649.0, "", catElectronics, catBooks));
        products.add(createProduct("Apple iPad Air 11 M2", "Chip M2 veloz com suporte a Apple Pencil Pro", 5499.0, "", catElectronics, catComputers));

        // Games & Consoles (31 a 40)
        products.add(createProduct("PlayStation 5 Slim 1TB", "Console Sony com leitor Ultra HD Blu-ray integrado", 3799.0, "", catGames, catElectronics));
        products.add(createProduct("Xbox Series X 1TB", "O console mais rápido e poderoso da Microsoft com 12 TFLOPS", 4299.0, "", catGames, catElectronics));
        products.add(createProduct("Nintendo Switch OLED", "Tela OLED vibrante de 7 polegadas e suporte ajustável", 2199.0, "", catGames, catElectronics));
        products.add(createProduct("Controle Sem Fio DualSense PS5", "Gatilhos adaptáveis dinâmicos e resposta tátil envolvente", 429.0, "", catGames, catAccessories));
        products.add(createProduct("Controle Sem Fio Xbox Series", "Superfícies esculpidas e botão de compartilhamento rápido", 399.0, "", catGames, catAccessories));
        products.add(createProduct("Headset Gamer HyperX Cloud II", "Drivers de 53mm com som surround virtual 7.1", 499.0, "", catGames, catAccessories));
        products.add(createProduct("Jogo Elden Ring - PS5", "Aclamado RPG de mundo aberto da FromSoftware", 249.0, "", catGames));
        products.add(createProduct("The Legend of Zelda: Tears of the Kingdom", "Aventura épica nos céus e terras de Hyrule", 299.0, "", catGames));
        products.add(createProduct("Jogo God of War Ragnarok - PS5", "Jornada emocionante de Kratos e Atreus pelos Nove Reinos", 229.0, "", catGames));
        products.add(createProduct("Volante Logitech G29 com Pedais", "Volante para simulador de corrida com feedback de força", 1799.0, "", catGames, catAccessories));

        // Móveis, Escritório & Acessórios (41 a 50)
        products.add(createProduct("Cadeira Ergonômica Herman Miller Aeron", "Referência mundial em ergonomia e design sustentável", 9800.0, "", catOffice));
        products.add(createProduct("Cadeira Gamer DT3 Racing", "Espuma moldada de alta densidade com suporte lombar", 1399.0, "", catOffice, catGames));
        products.add(createProduct("Mesa Digital com Altura Ajustável", "Mesa elétrica com dois motores e quatro posições de memória", 2499.0, "", catOffice));
        products.add(createProduct("Luminária de Monitor BenQ ScreenBar", "Iluminação com ajuste automático de brilho para monitores", 899.0, "", catOffice, catAccessories));
        products.add(createProduct("Suporte Articulado a Gás para Monitor", "Braço mecânico com movimentação completa até 9kg", 249.0, "", catOffice, catAccessories));
        products.add(createProduct("Hub USB-C 8 em 1 Anker", "Portas HDMI 4K, slots SD e carregamento Power Delivery 100W", 349.0, "", catAccessories, catComputers));
        products.add(createProduct("Webcam Logitech C920s Pro Full HD", "Resolução 1080p a 30fps com obturador de privacidade", 389.0, "", catAccessories, catComputers));
        products.add(createProduct("Microfone Condensador Shure MV7", "Microfone híbrido USB/XLR profissional para streaming", 1799.0, "", catAccessories, catElectronics));
        products.add(createProduct("Mochila Impermeável Executiva para Notebook", "Compartimento acolchoado para laptops de até 16 polegadas", 289.0, "", catAccessories));
        products.add(createProduct("Filtro de Linha iClamper Energia 5", "DPS de alta proteção contra raios e surtos elétricos", 99.0, "", catAccessories));

        productRepository.saveAll(products);

        // ==========================================
        // 3. USUÁRIOS (50 usuários com dados realistas)
        // ==========================================
        String[][] userData = {
                {"Maria Brown", "maria.brown@gmail.com", "988888801"},
                {"Alex Green", "alex.green@gmail.com", "988888802"},
                {"Bob Grey", "bob.grey@gmail.com", "988888803"},
                {"Carlos Silva", "carlos.silva@email.com", "988888804"},
                {"Ana Oliveira", "ana.oliveira@email.com", "988888805"},
                {"Lucas Pereira", "lucas.pereira@email.com", "988888806"},
                {"Beatriz Santos", "beatriz.santos@email.com", "988888807"},
                {"Gabriel Costa", "gabriel.costa@email.com", "988888808"},
                {"Mariana Souza", "mariana.souza@email.com", "988888809"},
                {"Felipe Almeida", "felipe.almeida@email.com", "988888810"},
                {"Camila Rodrigues", "camila.rodrigues@email.com", "988888811"},
                {"Rodrigo Ferreira", "rodrigo.ferreira@email.com", "988888812"},
                {"Juliana Lima", "juliana.lima@email.com", "988888813"},
                {"Thiago Martins", "thiago.martins@email.com", "988888814"},
                {"Larissa Barbosa", "larissa.barbosa@email.com", "988888815"},
                {"Rafael Carvalho", "rafael.carvalho@email.com", "988888816"},
                {"Amanda Ribeiro", "amanda.ribeiro@email.com", "988888817"},
                {"Bruno Castro", "bruno.castro@email.com", "988888818"},
                {"Fernanda Gomes", "fernanda.gomes@email.com", "988888819"},
                {"Diego Araujo", "diego.araujo@email.com", "988888820"},
                {"Patricia Mendes", "patricia.mendes@email.com", "988888821"},
                {"Gustavo Rocha", "gustavo.rocha@email.com", "988888822"},
                {"Leticia Cardoso", "leticia.cardoso@email.com", "988888823"},
                {"Vinicius Dias", "vinicius.dias@email.com", "988888824"},
                {"Vanessa Moreira", "vanessa.moreira@email.com", "988888825"},
                {"Eduardo Nunes", "eduardo.nunes@email.com", "988888826"},
                {"Jessica Freitas", "jessica.freitas@email.com", "988888827"},
                {"Leonardo Vieira", "leonardo.vieira@email.com", "988888828"},
                {"Bruna Ramos", "bruna.ramos@email.com", "988888829"},
                {"Matheus Teixeira", "matheus.teixeira@email.com", "988888830"},
                {"Natalia Cavalcanti", "natalia.cavalcanti@email.com", "988888831"},
                {"Daniel Monteiro", "daniel.monteiro@email.com", "988888832"},
                {"Aline Correia", "aline.correia@email.com", "988888833"},
                {"Marcelo Farias", "marcelo.farias@email.com", "988888834"},
                {"Renata Cunha", "renata.cunha@email.com", "988888835"},
                {"Caio Moura", "caio.moura@email.com", "988888836"},
                {"Tatiane Pinto", "tatiane.pinto@email.com", "988888837"},
                {"Igor Nogueira", "igor.nogueira@email.com", "988888838"},
                {"Priscila Rezende", "priscila.rezende@email.com", "988888839"},
                {"Danilo Campos", "danilo.campos@email.com", "988888840"},
                {"Helena Guimaraes", "helena.guimaraes@email.com", "988888841"},
                {"Vitor Borges", "vitor.borges@email.com", "988888842"},
                {"Flavia Pacheco", "flavia.pacheco@email.com", "988888843"},
                {"Samuel Macedo", "samuel.macedo@email.com", "988888844"},
                {"Luana Silveira", "luana.silveira@email.com", "988888845"},
                {"Douglas Fonseca", "douglas.fonseca@email.com", "988888846"},
                {"Isabela Brandao", "isabela.brandao@email.com", "988888847"},
                {"Henrique Neves", "henrique.neves@email.com", "988888848"},
                {"Sabrina Tavares", "sabrina.tavares@email.com", "988888849"},
                {"Arthur Miranda", "arthur.miranda@email.com", "988888850"}
        };

        List<User> users = new ArrayList<>();
        for (String[] u : userData) {
            users.add(new User(null, u[0], u[1], u[2], "123456"));
        }
        userRepository.saveAll(users);

        // ==========================================
        // 4. PEDIDOS (50 pedidos vinculados a usuários)
        // ==========================================
        OrderStatus[] statuses = OrderStatus.values();
        List<Order> orders = new ArrayList<>();
        Instant baseTime = Instant.parse("2026-01-10T10:00:00Z");

        for (int i = 0; i < 50; i++) {
            User client = users.get(i);
            Instant moment = baseTime.plus(i * 2L, ChronoUnit.DAYS).plus(i * 13L, ChronoUnit.MINUTES);
            OrderStatus status = statuses[i % statuses.length];
            orders.add(new Order(null, moment, status, client));
        }
        orderRepository.saveAll(orders);

        // ==========================================
        // 5. ITENS DE PEDIDO & PAGAMENTOS
        // ==========================================
        List<OrderItem> orderItems = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);

            // Item 1
            Product p1 = products.get(i % products.size());
            int q1 = (i % 3) + 1;
            orderItems.add(new OrderItem(order, p1, q1, p1.getPrice()));

            // Item 2 (índice deslocado coprimo para garantir produto diferente de p1)
            Product p2 = products.get((i + 13) % products.size());
            int q2 = ((i + 1) % 2) + 1;
            orderItems.add(new OrderItem(order, p2, q2, p2.getPrice()));

            // Se for pedido pago, enviado ou entregue, cria o pagamento correspondente
            if (order.getOrderStatus() == OrderStatus.Paid ||
                order.getOrderStatus() == OrderStatus.Shipped ||
                order.getOrderStatus() == OrderStatus.Delivered) {
                Payment payment = new Payment(null, order.getMoment().plus(1, ChronoUnit.HOURS), order);
                order.setPayment(payment);
            }
        }

        orderItemRepository.saveAll(orderItems);
        orderRepository.saveAll(orders);
    }

    private Product createProduct(String name, String description, Double price, String imgUrl, Category... categories) {
        Product p = new Product(null, name, description, price, imgUrl);
        p.getCategories().addAll(Arrays.asList(categories));
        return p;
    }
}
