# 🛠️ Configurando o Ambiente de Desenvolvimento

Este documento reúne todas as informações para configurar seu ambiente de desenvolvimento para projetos SpringBoot v3 com Java 21.

---

## 📋 Pré-requisitos

- **Java 21** - (Recomendado o uso de SDKMAN -> [Guia Linux](https://sdkman.io/) ou [Guia Windows](https://www.youtube.com/watch?v=hFiFQcfT9U0))
- **Maven** (Recomendado o uso de SDKMAN -> [Guia Linux](https://sdkman.io/) ou [Guia Windows](https://www.youtube.com/watch?v=hFiFQcfT9U0))
- **Docker & Docker Compose** -> [Guia Ofical](https://docs.docker.com/desktop/)
- **IDE** [Spring Tool Suite 4](https://spring.io/tools/) ou [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download)
- **Postman**(Opcional) [Postman](https://www.postman.com/downloads/) 

---

## 🚀 1. Iniciando o Projeto

Após instalar todas as ferramentas necessárias, siga os passos abaixo para iniciar o projeto:

1. Inicialize o docker e execute o comando abaixo para iniciar os serviços:
   ```sh
    docker-compose -p sistema-votacao up -d
   ```
  Esse comando irá iniciar:
  * 🐘 PostgreSQL: Banco de dados utilizado para persistência das informações da aplicação.
  * ☕ Aplicação Java: Serviço principal do sistema de votação.
  * 🧪 Locust: Ferramenta utilizada para testes de carga e stress da aplicação.

  💡 Observação: Caso deseje executar a aplicação diretamente pela IDE (sem Docker), altere a propriedade spring.datasource.url no application.yml para:
  ```sh
    spring.datasource.url: jdbc:postgresql://localhost:5432/db_server
   ```

2. Importando a Collection do Postman (**Extra**)

   Para facilitar o consumo da API, utilize a collection do Postman disponível na pasta `.postman` do projeto.

   **Passos para importar:**

   - Abra o Postman.
   - Clique em **"Import"** no canto superior esquerdo.
   - Selecione a aba **"Upload Files"**.
   - Escolha o arquivo:  
     ```
     .postman/sistema_votacao.postman_collection.json
     ```
   - Confirme a importação.

   Após a importação, você terá acesso a todos os endpoints da API com exemplos de uso prontos para teste.

Agora seu ambiente de desenvolvimento está configurado e pronto para uso!

## 🏗️ 2. Arquitetura do Projeto

A pasta `src/main/java` foi organizada em camadas para separar cada uma das responsabilidades do sistema e facilitar a manutenção, testabilidade do mesmo. Essa estrutura segue os princípios da Clean Architecture, onde cada camada tem uma função clara e bem definida.

### Estrutura principal do `src/main/java`

- **application/**  
  Camada responsável por receber as requisições, tratar exceções e orquestrar os casos de uso da aplicação.  
  - **controller/**  
    - **handler/**: Componentes para tratamento global de exceções, seguindo o **Padrão de Barreira de Falha (Fault Barrier Pattern)**.  
    - **v1/**: Controladores REST referentes à versão 1 da API.  
  - **dto/**: Objetos de transferência de dados. Organizados por recurso.  
    - **associados/**  
      - **request/**: DTOs utilizados para entrada de dados.  
      - **response/**: DTOs utilizados para retorno dos dados.  
    - **sessoes/**  
    - **votos/**  
    - **pautas/**
  - **mappers/**: Mapeadores responsáveis por converter entre entidades e DTOs.  
  - **usecases/**: Casos de uso da aplicação, organizados por recurso, seguindo o **Princípio da Responsabilidade Única**.  
    - **associados/**  
    - **sessoes/**  
    - **votos/**  
    - **pautas/**

- **domain/**  
  Camada central das regras de negócio.  
  - **entities/**: Entidades do domínio com regras e comportamentos essenciais (**A lógica de negócio pertence ao domínio**)
  - **enums/**: Enumerações utilizadas no contexto do domínio.  
  - **repositories/**: Interfaces que definem os contratos de persistência. A aplicação depende apenas desses contratos, e não das implementações concretas (**Princípio da Inversão de Dependência**).
  - **vo/**: Value Objects, representando conceitos imutáveis do domínio.

- **infrastructure/**  
  Implementações técnicas das dependências externas e configurações específicas.  
  - **repositories/**  
    - **impl/**: Implementações concretas das interfaces de repositórios definidas no domínio.  
    - **specifications/**: Implementações de filtros e consultas específicas baseadas em critérios.  

- **shared/**  
  Componentes e utilitários reutilizáveis em toda a aplicação.  
  - **exceptions/**: Exceções personalizadas que podem ser lançadas em diferentes camadas.  
  - **utils/**: Funções auxiliares e utilitárias comuns ao projeto.

---

## ✅ 3. Testes de carga e stress da aplicação (Locust e Docker Stats)

O **Locust** é uma ferramenta simples e poderosa para realizar testes de carga na sua aplicação. Com o Locust, você consegue simular vários usuários acessando a nossa API ao mesmo tempo, ajudando a identificar como ele se comporta sob alta demanda. 

### Acessando a Interface do Locust

Após iniciar os containers, você pode acessar a interface web de testes através da seguinte URL:

- **Interface Locust:** [http://localhost:8089/](http://localhost:8089/)

### Configurando o Teste

Na página de início do Locust, você verá um formulário para configurar o teste com os seguintes campos:

1. **Ramp up (users started/second):**
   - Define a taxa de criação de usuários por segundo. Exemplo: `10` significa que 10 usuários serão iniciados a cada segundo até alcançar o número total de usuários especificado.

2. **Number of users (peak concurrency):**
   - Especifique o número total de usuários concorrentes a serem simulados no teste. Exemplo: `100` para testar com 100 usuários simultâneos.

3. **Host:**
   - Insira a URL do servidor a ser testado. Exemplo: `http://localhost:8000` ou o endereço do servidor que você está utilizando.

4. **Advanced Options:**
   - **Run time (e.g. 20, 20s, 3m, 2h, 1h20m, 3h30m10s, etc.):**
     - Define o tempo total de execução do teste. Exemplos:
       - `20` (20 segundos)
       - `3m` (3 minutos)
       - `2h` (2 horas)
       - `1h20m` (1 hora e 20 minutos)

### Monitorando o Container em Paralelo

Você também pode acompanhar o status do seu container enquanto o Locust executa os testes, utilizando o comando `docker stats` para visualizar as métricas de uso de recursos (CPU, memória, rede, etc.) do container. Esse comando permite que você veja como o container está se comportando sob carga.

Para monitorar o status do container, basta abrir um terminal paralelo e rodar o seguinte comando:

```bash
  docker stats api
```

Abaixo segue um vídeo de exemplo de como usar o Locust para realizar testes de stress: [Teste de Stress com Locust](https://github.com/vcdl1997/desafio-votacao/tutorial_locust.mp4)


