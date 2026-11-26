package br.com.fatecads.fatecads;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.fatecads.fatecads.entity.Aluno;
import br.com.fatecads.fatecads.entity.ItemDoPedido;
import br.com.fatecads.fatecads.entity.Pedido;
import br.com.fatecads.fatecads.entity.Produto;

import br.com.fatecads.fatecads.entity.Curso;
import br.com.fatecads.fatecads.entity.Disciplina;
import br.com.fatecads.fatecads.entity.Professor;
import br.com.fatecads.fatecads.repository.AlunoRepository;
import br.com.fatecads.fatecads.repository.CursoRepository;
import br.com.fatecads.fatecads.repository.DisciplinaRepository;
import br.com.fatecads.fatecads.repository.ProfessorRepository;
import br.com.fatecads.fatecads.repository.Pedidorepository;
import br.com.fatecads.fatecads.repository.ProdutoRepository;
import br.com.fatecads.fatecads.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
class FatecadsWebTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

        @Autowired
        private ProdutoRepository produtoRepository;

        @Autowired
        private Pedidorepository pedidoRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void professorPagesRender() throws Exception {
        mockMvc.perform(get("/professor/listar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/usuario/listar"))));

        mockMvc.perform(get("/professor/criar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/usuario/listar"))));
    }

    @Test
    void disciplinaPagesRender() throws Exception {
        mockMvc.perform(get("/disciplina/listar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/usuario/listar"))));

        mockMvc.perform(get("/disciplina/criar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/usuario/listar"))));
    }

    @Test
    void alunoPagesRender() throws Exception {
        mockMvc.perform(get("/alunos/listar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/usuario/listar"))));

        mockMvc.perform(get("/alunos/criar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/usuario/listar"))));
    }

    @Test
    void cursoPagesRender() throws Exception {
        mockMvc.perform(get("/curso/listar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/usuario/listar"))));

        mockMvc.perform(get("/curso/criar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/usuario/listar"))));
    }

    @Test
    void usuarioPagesRender() throws Exception {
        mockMvc.perform(get("/usuario/listar"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/usuario/criar"))
                .andExpect(status().isOk());
    }

    @Test
    void pedidoPagesRender() throws Exception {
        mockMvc.perform(get("/pedido/listar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/pedido/listar\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/produto/listar"))))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/usuario/listar"))));

        mockMvc.perform(get("/pedido/criar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/pedido/listar\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/produto/listar"))));
    }

    @Test
    void savesAlunoWithCurso() throws Exception {
        Curso curso = cursoRepository.save(new Curso(null, "ADS", "Noturno", 2800, null));

        mockMvc.perform(post("/alunos/salvar")
                        .param("nomeAluno", "Aluno Teste")
                        .param("emailAluno", "aluno.teste@example.com")
                        .param("telefoneAluno", "11999999999")
                        .param("cpfAluno", "12345678901")
                        .param("enderecoAluno", "Rua Teste")
                        .param("raAluno", "RA001")
                        .param("curso", curso.getIdCurso().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos/listar"));

        assertTrue(alunoRepository.findAll().stream()
                .anyMatch(aluno -> aluno.getCurso() != null
                        && curso.getIdCurso().equals(aluno.getCurso().getIdCurso())));
    }

    @Test
    void savesCursoWithDisciplina() throws Exception {
        Disciplina disciplina = disciplinaRepository.save(
                new Disciplina(null, "DevOps", "DEV", 80, null));

        mockMvc.perform(post("/curso/salvar")
                        .param("nomeCurso", "ADS")
                        .param("periodo", "Noturno")
                        .param("cargaHoraria", "2800")
                        .param("disciplina", disciplina.getIdDisciplina().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curso/listar"));

        assertTrue(cursoRepository.findAll().stream()
                .anyMatch(curso -> curso.getDisciplina() != null
                        && disciplina.getIdDisciplina().equals(curso.getDisciplina().getIdDisciplina())));
    }

    @Test
    void savesDisciplinaWithProfessor() throws Exception {
        Professor professor = professorRepository.save(
                new Professor(null, "Professor Teste", "11999999999", "Mestrado", "RM001"));

        mockMvc.perform(post("/disciplina/salvar")
                        .param("nomeDisciplina", "DevOps")
                        .param("siglaDisciplina", "DEV")
                        .param("cargaHorariaDisciplina", "80")
                        .param("professor", professor.getIdProfessor().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/disciplina/listar"));

        assertTrue(disciplinaRepository.findAll().stream()
                .anyMatch(disciplina -> disciplina.getProfessor() != null
                        && professor.getIdProfessor().equals(disciplina.getProfessor().getIdProfessor())));
    }

    @Test
    void savesUsuario() throws Exception {
        mockMvc.perform(post("/usuario/salvar")
                        .param("nomeUsuario", "Usuario Teste")
                        .param("emailUsuario", "usuario.teste@example.com")
                        .param("loginUsuario", "usuario.teste")
                        .param("senhaUsuario", "senha123")
                        .param("role", "ROLE_ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuario/listar"));

        assertTrue(usuarioRepository.findAll().stream()
                .anyMatch(usuario -> "usuario.teste".equals(usuario.getLoginUsuario())));
    }

    @Test
    void savesProduto() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "arquivoImagemProduto", "produto.png", "image/png", "imagem-teste".getBytes());

        mockMvc.perform(get("/produto/criar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Imagem do produto")));

        mockMvc.perform(multipart("/produto/salvar")
                        .param("descricaoProduto", "Caderno")
                        .param("valorProduto", "12.50")
                        .param("unidadeMedida", "UN")
                        .param("marcaProduto", "Fatec")
                        .file(image))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produto/listar"));

        assertTrue(produtoRepository.findAll().stream()
                .anyMatch(produto -> "UN".equals(produto.getUnidadeMedida())
                        && produto.getImagemProduto() != null
                        && produto.getImagemProduto().startsWith("data:image/png;base64,")));

        Produto produtoSalvo = produtoRepository.findAll().get(0);
        mockMvc.perform(get("/produto/detalhes/" + produtoSalvo.getIdProduto()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(produtoSalvo.getImagemProduto())));

        mockMvc.perform(get("/produto/editar/" + produtoSalvo.getIdProduto()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(produtoSalvo.getImagemProduto())));

        mockMvc.perform(multipart("/produto/salvar")
                        .param("idProduto", produtoSalvo.getIdProduto().toString())
                        .param("descricaoProduto", "Caderno atualizado")
                        .param("valorProduto", "12.50")
                        .param("unidadeMedida", "UN")
                        .param("marcaProduto", "Fatec"))
                .andExpect(status().is3xxRedirection());

        assertTrue(produtoRepository.findById(produtoSalvo.getIdProduto()).orElseThrow().getImagemProduto()
                .equals(produtoSalvo.getImagemProduto()));
    }

    @Test
        void savesPedidoWithProdutoAndUsuario() throws Exception {
                br.com.fatecads.fatecads.entity.Usuario usuario = new br.com.fatecads.fatecads.entity.Usuario();
                usuario.setNomeUsuario("Usuario Pedido");
                usuario.setEmailUsuario("pedido@example.com");
                usuario.setLoginUsuario("usuario.pedido");
                usuario.setSenhaUsuario("senha123");
                usuario.setRole("ROLE_USER");
                usuario = usuarioRepository.save(usuario);

        Produto produto = new Produto();
        produto.setDescricaoProduto("Caneta");
        produto.setValorProduto(3.50);
        produto.setUnidadeMedida("UN");
        produto.setMarcaProduto("Fatec");
        produto = produtoRepository.save(produto);

        Produto segundoProduto = new Produto();
        segundoProduto.setDescricaoProduto("Caderno");
        segundoProduto.setValorProduto(5.25);
        segundoProduto.setUnidadeMedida("UN");
        segundoProduto.setMarcaProduto("Fatec");
        segundoProduto = produtoRepository.save(segundoProduto);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        mockMvc.perform(post("/pedido")
                        .contentType("application/json")
                        .content("{\"usuario\":{\"idUsuario\":" + usuario.getIdUsuario()
                                + "},\"itens\":[{\"produto\":{\"idProduto\":"
                                + produto.getIdProduto() + "},\"quantidade\":2},{\"produto\":{\"idProduto\":"
                                + segundoProduto.getIdProduto() + "},\"quantidade\":3}]}"))
                .andExpect(status().isOk());

        Pedido pedidoSalvo = pedidoRepository.findAll().get(0);
        assertTrue(pedidoSalvo.getTotalPedido() != null && pedidoSalvo.getTotalPedido() == 22.75);
        assertTrue(pedidoSalvo.getItens().size() == 2);

        mockMvc.perform(get("/pedido/listar"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "/pedido/consultar/" + pedidoSalvo.getIdPedido())));

        mockMvc.perform(get("/pedido/consultar/" + pedidoSalvo.getIdPedido()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Produtos do pedido")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Caneta")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Caderno")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/home\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("/produto/listar"))));

        ItemDoPedido itemAntigo = new ItemDoPedido();
        itemAntigo.setProduto(produto);
        itemAntigo.setQuantidade(1);
        Pedido pedidoAntigo = new Pedido();
        pedidoAntigo.setUsuario(usuario);
        itemAntigo.setPedido(pedidoAntigo);
        pedidoAntigo.setItens(java.util.List.of(itemAntigo));
        pedidoAntigo = pedidoRepository.save(pedidoAntigo);

        mockMvc.perform(get("/pedido/consultar/" + pedidoAntigo.getIdPedido()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Caneta")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("R$ 0,00")));
    }
}
