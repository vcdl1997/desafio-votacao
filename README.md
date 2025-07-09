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
  * 🧪 JMeter: Ferramenta utilizada para testes de carga e stress da aplicação.

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

## 🏗️ 2. Arquitetura do Projeto (`app/`)

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
    (e assim por diante...)  
  - **mappers/**: Mapeadores responsáveis por converter entre entidades e DTOs.  
  - **usecases/**: Casos de uso da aplicação, organizados por recurso.  
    - **associados/**  
    - **sessoes/**  
    - **votos/**  
    - **pautas/**

- **domain/**  
  Camada central das regras de negócio.  
  - **entities/**: Entidades do domínio, contendo as regras e comportamentos essenciais.  
  - **enums/**: Enumerações utilizadas no contexto do domínio.  
  - **repositories/**: Interfaces de repositórios, que descrevem as operações de persistência.  
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

## ✅ 6. Testes de carga e stress da aplicação


