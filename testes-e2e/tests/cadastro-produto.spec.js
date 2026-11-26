// Testes End-to-End da funcionalidade de produtos do sistema Fatec ADS.
// Cobrem cadastro, listagem, edicao e exclusao pela interface real do navegador.
const { test, expect } = require("@playwright/test");

const ADMIN_USER = "admin";
const ADMIN_PASSWORD = "admin123";

async function login(page) {
  await page.goto("/login");
  await page.locator("#username").fill(ADMIN_USER);
  await page.locator("#password").fill(ADMIN_PASSWORD);
  await page.getByRole("button", { name: "Entrar" }).click();
  await page.waitForURL(/\/(home|produto\/listar)/);
}

function dadosUnicos(sufixo = "") {
  const stamp = Date.now().toString(36);
  return {
    descricao: `E2E ${stamp}${sufixo}`,
    valor: "12.50",
    unidade: "UN",
    marca: "Marca E2E"
  };
}

async function cadastrarProduto(page, dados) {
  await page.goto("/produto/criar");
  await expect(page.locator("#descricaoProduto")).toBeVisible();
  await page.locator("#descricaoProduto").fill(dados.descricao);
  await page.locator("#valorProduto").fill(dados.valor);
  await page.locator("#unidadeMedida").fill(dados.unidade);
  await page.locator("#marcaProduto").fill(dados.marca);
  await page.getByRole("button", { name: "Salvar produto" }).click();
  await page.waitForURL("**/produto/listar");
}

function linhaDoProduto(page, descricao) {
  return page.locator("table tbody tr", { hasText: descricao });
}

test.beforeEach(async ({ page }) => {
  await login(page);
});

test("cadastro de produto aparece na listagem", async ({ page }) => {
  const dados = dadosUnicos();

  await page.goto("/produto/criar");
  await expect(page).toHaveURL(/\/produto\/criar$/);
  await expect(page.locator("#descricaoProduto")).toBeVisible();
  await expect(page.locator("#valorProduto")).toBeVisible();
  await expect(page.locator("#unidadeMedida")).toBeVisible();
  await expect(page.locator("#marcaProduto")).toBeVisible();

  await page.locator("#descricaoProduto").fill(dados.descricao);
  await page.locator("#valorProduto").fill(dados.valor);
  await page.locator("#unidadeMedida").fill(dados.unidade);
  await page.locator("#marcaProduto").fill(dados.marca);
  await page.getByRole("button", { name: "Salvar produto" }).click();

  await expect(page).toHaveURL(/\/produto\/listar$/);

  const linha = linhaDoProduto(page, dados.descricao);
  await expect(linha).toHaveCount(1);
  await expect(linha).toContainText(dados.unidade);
  await expect(linha).toContainText(dados.marca);
  await expect(linha).toContainText("12,50");
});

test("listagem de produtos carrega com a tabela", async ({ page }) => {
  await page.goto("/produto/listar");

  await expect(page).toHaveURL(/\/produto\/listar$/);
  await expect(page.getByRole("heading", { name: "Lista de Produtos" })).toBeVisible();
  await expect(page.getByRole("columnheader", { name: "Descricao" })).toBeVisible();
});

test("edicao de produto atualiza a listagem", async ({ page }) => {
  const dados = dadosUnicos(" ed");
  await cadastrarProduto(page, dados);

  const linha = linhaDoProduto(page, dados.descricao);
  await expect(linha).toHaveCount(1);
  await linha.getByRole("link", { name: "Editar" }).click();

  await expect(page).toHaveURL(/\/produto\/editar\/\d+$/);
  const novaDescricao = `${dados.descricao} up`;
  await page.locator("#descricaoProduto").fill(novaDescricao);
  await page.getByRole("button", { name: "Salvar produto" }).click();

  await expect(page).toHaveURL(/\/produto\/listar$/);
  await expect(linhaDoProduto(page, novaDescricao)).toHaveCount(1);
  await expect(page.getByRole("cell", { name: dados.descricao, exact: true })).toHaveCount(0);
});

test("exclusao de produto remove o item da listagem", async ({ page }) => {
  const dados = dadosUnicos(" ex");
  await cadastrarProduto(page, dados);

  const linha = linhaDoProduto(page, dados.descricao);
  await expect(linha).toHaveCount(1);
  await linha.getByRole("link", { name: "Excluir" }).click();

  await expect(page).toHaveURL(/\/produto\/listar$/);
  await expect(linhaDoProduto(page, dados.descricao)).toHaveCount(0);
});
