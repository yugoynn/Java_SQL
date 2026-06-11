# Sistema de Gerenciamento Ninja

Sistema desktop em Java + Swing com banco de dados MySQL para cadastrar ninjas,
missoes, vincular ninjas a missoes (com regras de negocio), gerar totalizadores
e consultar views.

## Requisitos

- Java 17+
- Maven 3.8+
- MySQL instalado localmente

## Como rodar

### 1. Configure o banco

Crie as tabelas, dados e views executando os scripts na ordem:

```
database/migrations/001_criar_tabelas.sql
database/migrations/002_inserir_dados.sql
database/migrations/003_views.sql
```

### 2. Configure as credenciais

```bash
cp src/main/resources/database.properties.example src/main/resources/database.properties
```

Edite `database.properties` com sua URL, usuario e senha do MySQL.

### 3. Compile e execute

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.projeto.Main"
```

## Estrutura (camadas)

```
com.projeto
├── Main.java                 inicia a aplicacao
├── connection/Conexao.java   conexao com o MySQL
├── model/                    entidades (Ninja, Missao, NinjaMissao, Totalizador)
├── dao/                      acesso ao banco (uma classe por entidade + ViewDAO)
├── service/                  regras de negocio
└── view/                     telas Swing
```

## Telas

- Tela Principal (menu)
- Cadastro de Ninjas
- Cadastro de Missoes
- Vincular Ninja e Missao
- Totalizadores
- Consulta de Views

Veja `docs/EXPLICACAO.md` para o detalhamento das regras implementadas.
