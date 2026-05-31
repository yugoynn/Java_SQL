# Projeto

- Java 17+
- Maven 3.8+
- PostgreSQL instalado localmente
- IntelliJ IDEA ou NetBeans

---

## Como rodar o projeto

### 1. Clone o repositório
```bash
git clone https://github.com/SEU_USUARIO/projeto-faculdade.git
cd projeto-faculdade
```

### 2. Configure o banco de dados
- Crie um banco no seu PostgreSQL local
- Copie o arquivo de exemplo e preencha com suas credenciais:
```bash
cp src/main/resources/database.properties.example src/main/resources/database.properties
```
- Edite o arquivo `database.properties` com sua URL, usuário e senha
- Execute o script SQL: `database/migrations/001_criar_tabelas.sql`

### 3. Importe na IDE
- **IntelliJ:** `File > Open` → selecione a pasta do projeto (ele detecta o Maven automaticamente)
- **NetBeans:** `File > Open Project` → selecione a pasta

### 4. Compile e rode
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.projeto.Main"
```

---

## Estrutura do projeto

```
projeto-faculdade/
├── src/
│   ├── main/
│   │   ├── java/com/projeto/   ← Código-fonte Java
│   │   └── resources/          ← Configurações (database.properties — NÃO versionar)
│   └── test/
│       └── java/com/projeto/   ← Testes
├── database/
│   └── migrations/             ← Scripts SQL
├── docs/                       ← Documentação
├── pom.xml                     ← Dependências Maven
└── .gitignore
```

