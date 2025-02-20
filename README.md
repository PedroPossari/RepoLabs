## Integrantes do Grupo :sunglasses:

- **[José Alves](https://github.com/IBORD)**
- **[Pedro Possari](https://github.com/PedroPossari)**

## 🔧 Arquitetura da Pipeline

- Temos primeiro o **Build** para formar a base do actions, onde instalamos o java e buildamos as dependencias do maven.
- Agora vamos executar os testes de **HealthCheck** que só rodam se a build for executada com sucesso `needs: Build`. Adicionamos uma tag aos testes de health check para ele executar todos com essa tag `-Phealth`.
- Adicionamos a propridade para  `-Dallure.results.directory=allure-results/health` os resultados estão sendo armazenados na pasta do allure-results
- Adicionamos um `-DtestFailureIgnore=false` para ignorar os testes que falharem e isso se repete para os outros teste de **Contrato** e **Funcional**
- Após isso verificamos se o conteudo foi adicionado ao allure-results com o comando `ls -R allure-results/funcional || echo "Nenhum resultado encontrado em allure-results/funcional"`.
- E finalizando a parte dos teste fazemos um upload dos resultados para guardar.
- Na parte do **Reports** temos um needs `[ Build, HealthCheck, Contrato, Funcional ]` para ele esperar todos os passos anteriores. E em cada step temos um `if: always()` para executar mesmo se os trabalhos anteriores falharem.
- Na parte de Carregar historico  `ref: gh-pages` clonamos o reposistorio do GitHub Pages e `path: gh-pages` Define o diretório onde o conteúdo da branch gh-pages será clonado.
- Baixamos todos os uploads feito anteriormente `- name: Baixar resultados dos testes funcionais
        uses: actions/download-artifact@v4
        with:
          name: functional-results-funcional
          path: allure-results/funcional` referenciando o local onde foi feito o upload.
- Após isso fazemos o merge com o conteudo `- name: Merge test results
        run: |
          mkdir -p allure-results
          cp -r allure-results/health/* allure-results/ || true
          cp -r allure-results/contrato/* allure-results/ || true
          cp -r allure-results/funcional/* allure-results/ || true`
- Instalamos o allure via CLI
- Geramos os relatorios `allure generate allure-results --clean -o allure-report`
- Agora publicamos os relatorios no GitHub Pages passando o token de segurança, a branch, o diretorio e a mensagem de commit `personal_token: ${{ secrets.PAT_TOKEN }}
          publish_branch: gh-pages
          publish_dir: allure-report
          commit_message: "Publish Allure report"`
- E por fim geramos um link para visualizar a pagina do allure com os resultados `echo "Acesse o relatório em https://pedropossari.com.br/RepoLabs/"`

```yml
name: pipeline
run-name: ${{ github.actor }} is testing allure 🚀

on: [push]

jobs:
  Build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout do código
        uses: actions/checkout@v4
        with:
          fetch-depth: 0

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: 17

      - name: Build com Maven
        run: mvn -B package -f pom.xml

      - name: Maven Verify
        run: mvn clean compile -f pom.xml

  HealthCheck:
    runs-on: ubuntu-latest
    needs: Build
    steps:
      - name: Checkout do código
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4.2.0
        with:
          distribution: 'oracle'
          java-version: 17

      - name: Executar teste de Health check
        run: mvn test -Phealth -Dallure.results.directory=allure-results/health -DtestFailureIgnore=false

      - name: Verificar conteúdo de allure-results
        run: |
          ls -R allure-results/health || echo "Nenhum resultado encontrado em allure-results/health"

      - name: Upload functional test results
        uses: actions/upload-artifact@v4
        with:
          name: functional-results-health
          path: allure-results/health

  Contrato:
    runs-on: ubuntu-latest
    needs: HealthCheck
    steps:
      - name: Checkout do código
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4.2.0
        with:
          distribution: 'oracle'
          java-version: 17

      - name: Executar teste de contrato de listar usuario
        run: mvn test -Pcontrato -Dallure.results.directory=allure-results/contrato -DtestFailureIgnore=false

      - name: Verificar conteúdo de allure-results
        run: |
          ls -R allure-results/contrato || echo "Nenhum resultado encontrado em allure-results/contrato"

      - name: Upload functional test results
        uses: actions/upload-artifact@v4
        with:
          name: functional-results-contrato
          path: allure-results/contrato

  Funcional:
    runs-on: ubuntu-latest
    needs: Contrato
    steps:
      - name: Checkout do código
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4.2.0
        with:
          distribution: 'oracle'
          java-version: 17

      - name: Executar teste funcionais
        run: mvn clean test -Dallure.results.directory=allure-results/funcional -f pom.xml

      - name: Verificar conteúdo de allure-results
        run: |
          ls -R allure-results/funcional || echo "Nenhum resultado encontrado em allure-results/funcional"

      - name: Upload functional test results
        uses: actions/upload-artifact@v4
        with:
          name: functional-results-funcional
          path: allure-results/funcional

      - run: echo "🍏 This job's status is ${{ job.status }}."

  Reports:
    runs-on: ubuntu-latest
    needs: [ Build, HealthCheck, Contrato, Funcional ]
    steps:
      - name: Checkout do código
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4.2.0
        with:
          distribution: 'oracle'
          java-version: 17

      - name: Carregar histórico do relatório de testes
        uses: actions/checkout@v4
        if: always()
        continue-on-error: true
        with:
          ref: gh-pages
          path: gh-pages

      - name: Baixar resultados dos testes funcionais
        uses: actions/download-artifact@v4
        with:
          name: functional-results-funcional
          path: allure-results/funcional

      - name: Baixar resultados dos testes de contrato
        uses: actions/download-artifact@v4
        with:
          name: functional-results-contrato
          path: allure-results/contrato

      - name: Baixar resultados dos testes de health check
        uses: actions/download-artifact@v4
        with:
          name: functional-results-health
          path: allure-results/health

      - name: Merge test results
        run: |
          mkdir -p allure-results
          cp -r allure-results/health/* allure-results/ || true
          cp -r allure-results/contrato/* allure-results/ || true
          cp -r allure-results/funcional/* allure-results/ || true

      - name: Verificar conteúdo de allure-results
        run: |
          ls -R allure-results || echo "Nenhum resultado encontrado em allure-results"

      - name: Instalar Allure Commandline
        run: |
          sudo apt-get update
          sudo apt-get install -y wget unzip
          wget https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.zip
          sudo unzip allure-2.24.0.zip -d /opt/
          sudo ln -s /opt/allure-2.24.0/bin/allure /usr/bin/allure
          allure --version

      - name: Gerar relatório Allure
        run: |
          allure generate allure-results --clean -o allure-report

      - name: Verificar conteúdo de allure-report
        run: |
          ls -R allure-report || echo "Nenhum relatório encontrado em allure-report"

      - name: Publicar relatório de testes no GitHub Pages
        uses: peaceiris/actions-gh-pages@v3
        if: always()
        with:
          personal_token: ${{ secrets.PAT_TOKEN }}
          publish_branch: gh-pages
          publish_dir: allure-report
          commit_message: "Publish Allure report"

      - name: Verificar link do relatório
        run: |
          echo "Acesse o relatório em https://pedropossari.com.br/RepoLabs/"
```


## 📊 Testes


1. **Health Check**:
- Para realizar esse teste pegamos o end-point de listar todos os usuarios com sucesso e verificamos o retorno esperado (200)

2. **Contrato**:
- Para realizar esse teste pegamos todos os testes de Schemas feitos na api para verificar a veracidade do body dos end-points

3. **Funcional**:
- Para realizar esse teste pegamos todos os testes realizados na api da aplicação, com isso garantido o report das funcionalidades



