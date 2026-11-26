# Testes End-to-End (E2E) — Sistema Fatec ADS

Pasta independente com os testes automatizados de navegador do projeto
`DevOpsEntregaCont` (módulo Spring Boot `fatecads`).

- **Java:** 17
- **Spring Boot:** 4.0.3
- **Playwright:** 1.65.1 (Chromium)
- **Aplicação:** `http://localhost:8080`
- **Usuário administrador (seed):** `admin` / `admin123`

---

## O que é o Playwright?

Playwright é uma biblioteca de automação de navegadores mantida pela Microsoft.
Ela abre um navegador real (Chromium, Firefox ou WebKit), interage com a página
como uma pessoa faria (clicar, digitar, navegar) e faz asserções sobre o que
aparece na tela. É a ferramenta usada aqui para os testes **End-to-End**.

## Qual é o objetivo dos testes E2E?

Os testes E2E validam o sistema pela **interface do navegador**, de ponta a ponta:
navegador → controller → service → repositório → banco → template renderizado.
Eles complementam os testes de backend já existentes (`FatecadsWebTests.java`,
com Spring MockMvc), que verificam requisições e respostas sem abrir navegador.

O foco principal é a funcionalidade de **cadastro de produtos**, além de
listagem, edição e exclusão.

## Como os testes foram organizados

```
testes-e2e/
├── package.json            # dependências e scripts npm
├── playwright.config.js    # configuração do Playwright (Chromium, relatório HTML, webServer)
├── tests/
│   └── cadastro-produto.spec.js  # todos os cenários E2E
├── README.md               # este arquivo
└── (gerados na execução)
    ├── playwright-report/  # relatório HTML
    └── test-results/       # capturas de tela e traces
```

O arquivo `playwright.config.js` sobe a aplicação automaticamente
(`java -jar target/fatecads-0.0.1-SNAPSHOT.jar --spring.profiles.active=e2e`)
antes de rodar os testes e a encerra ao final. O perfil `e2e`
(`fatecads/src/main/resources/application-e2e.properties`) usa um **banco H2
em memória**, isolado do PostgreSQL de desenvolvimento. Assim os testes nunca
leem, alteram ou apagam dados reais.

Os testes usam dados únicos (descrição gerada com timestamp em base36) para não
conflitar com cadastros existentes, e a edição/exclusão só manipulam produtos
criados pelo próprio teste.

## Como iniciar a aplicação manualmente

Normalmente **não é necessário**: o Playwright sobe a aplicação sozinho.
Para subir manualmente em modo de teste (H2 em memória):

```powershell
cd C:\Users\USUARIO\Desktop\devOps\DevOpsEntregaCont\fatecads
.\mvnw.cmd -DskipTests package
java -jar target\fatecads-0.0.1-SNAPSHOT.jar --spring.profiles.active=e2e
```

Para subir no modo padrão (PostgreSQL local `fatecads_db`, porta 8080):

```powershell
cd C:\Users\USUARIO\Desktop\devOps\DevOpsEntregaCont\fatecads
.\mvnw.cmd spring-boot:run
```

> A porta padrão do projeto é **8080** (`server.port=8080` em
> `application.properties`).

## Como instalar as dependências

```powershell
cd C:\Users\USUARIO\Desktop\devOps\DevOpsEntregaCont\testes-e2e
npm install
```

## Como instalar os navegadores do Playwright

```powershell
npm run install:browsers
# equivalente a: npx playwright install chromium
```

## Como executar todos os testes

Antes da primeira execução, garanta que o JAR está atualizado (o perfil `e2e`
precisa estar empacotado):

```powershell
cd C:\Users\USUARIO\Desktop\devOps\DevOpsEntregaCont\fatecads
.\mvnw.cmd -DskipTests package

cd ..\testes-e2e
npm test
```

O Playwright inicia a aplicação, executa os 4 testes e gera o relatório.

## Como executar apenas o teste de cadastro

```powershell
npm run test:cadastro
# equivalente a: npx playwright test tests/cadastro-produto.spec.js --grep "cadastro de produto"
```

## Como visualizar o relatório dos testes

```powershell
npm run report
# equivalente a: npx playwright show-report
```

O relatório HTML fica em `testes-e2e/playwright-report/index.html` e também pode
ser aberto diretamente no navegador. As capturas de tela de cada teste ficam em
`testes-e2e/test-results/`.

## Funcionalidades testadas

| Teste | O que valida |
|-------|--------------|
| `cadastro de produto aparece na listagem` | Login de admin, abertura de `/produto/criar`, preenchimento de `#descricaoProduto`, `#valorProduto`, `#unidadeMedida`, `#marcaProduto`, clique em **Salvar produto**, redirecionamento para `/produto/listar` e presença real do produto na tabela. |
| `listagem de produtos carrega com a tabela` | Acesso a `/produto/listar` e renderização da tabela de produtos. |
| `edicao de produto atualiza a listagem` | Criação de produto único, abertura de Editar, alteração da descrição, salvamento e verificação da nova descrição na listagem. |
| `exclusao de produto remove o item da listagem` | Criação de produto único, clique em Excluir e verificação de que ele não aparece mais na listagem. |

## Preservação dos testes existentes

Os testes Java com Spring MockMvc (`fatecads/src/test/.../FatecadsWebTests.java`)
não foram alterados e continuam sendo executados com:

```powershell
cd C:\Users\USUARIO\Desktop\devOps\DevOpsEntregaCont\fatecads
.\mvnw.cmd test
```

## Uso de Inteligência Artificial

O **OpenCode** foi utilizado como ferramenta de assistência na análise do
projeto, na criação dos arquivos de configuração do Playwright, na elaboração
dos testes E2E e na execução/validação local dos testes. As decisões e os
comandos executados estão documentados neste README. Nenhuma alteração foi
enviada ao repositório remoto (sem `git push`, `commit`, `merge` ou similares).
