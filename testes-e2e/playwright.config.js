// Configuracao do Playwright para os testes E2E do sistema Fatec ADS.
// A aplicacao Spring Boot e iniciada automaticamente usando o perfil "e2e",
// que aponta para um banco H2 em memoria e nao toca no PostgreSQL original.
const { defineConfig, devices } = require("@playwright/test");
const path = require("path");

const APP_PORT = 8080;
const APP_URL = `http://localhost:${APP_PORT}`;
const JAR_PATH = path.resolve(
  __dirname,
  "..",
  "fatecads",
  "target",
  "fatecads-0.0.1-SNAPSHOT.jar"
);
const APP_DIR = path.resolve(__dirname, "..", "fatecads");

module.exports = defineConfig({
  testDir: "./tests",
  fullyParallel: false,
  workers: 1,
  retries: 0,
  timeout: 60_000,
  expect: { timeout: 10_000 },
  reporter: [
    ["list"],
    ["html", { outputFolder: "playwright-report", open: "never" }]
  ],
  use: {
    baseURL: APP_URL,
    headless: true,
    screenshot: "on",
    trace: "retain-on-failure",
    video: "off"
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"] }
    }
  ],
  webServer: {
    command: `java -jar "${JAR_PATH}" --spring.profiles.active=e2e`,
    cwd: APP_DIR,
    url: `${APP_URL}/login`,
    reuseExistingServer: false,
    timeout: 180_000,
    stdout: "pipe",
    stderr: "pipe"
  }
});
