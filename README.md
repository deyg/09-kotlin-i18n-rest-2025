# Kotlin i18n REST — POC (IntelliJ 2025)

POC reprodutível para o time: **Java 17 + Spring Boot 3.3.3 + Kotlin 2.2.10 (language/api 2.0) + Maven + i18n (pt/en)**.

---

## 0) Pré-requisitos
- **JDK 17** instalado (`java -version`).
- **Maven 3.9+** no PATH (`mvn -v`).
- **IntelliJ 2025** (Community OK).

> Dica (IntelliJ): Settings → Build Tools → Maven → *Delegate IDE build/run actions to Maven*.

---

## 1) Estrutura do projeto
Crie uma pasta (ex.: `kotlin-i18n-rest-2025`) e a árvore abaixo:

```
pom.xml
src/
 └─ main/
    ├─ kotlin/com/exemplo/demo/
    │  ├─ Application.kt
    │  ├─ RestExceptionHandler.kt
    │  ├─ config/LocaleConfig.kt
    │  ├─ controller/SaudacaoController.kt
    │  └─ dto/UserDto.kt
    └─ resources/
       ├─ application.yml
       ├─ messages.properties
       └─ messages_pt_BR.properties
```

---

## 2) `pom.xml` (copie e cole)

> Inclui **kotlin-maven-allopen** para habilitar `<plugin>spring</plugin>` e fixa **language/api 2.0**
> (evita o erro `api-version > language-version`).

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.3</version>
    <relativePath/>
  </parent>

  <groupId>com.exemplo</groupId>
  <artifactId>demo</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <name>kotlin-i18n-rest-2025</name>
  <description>REST API Kotlin + Spring Boot + i18n (pt/en)</description>

  <properties>
    <java.version>17</java.version>
    <kotlin.version>2.2.10</kotlin.version>
    <kotlin.lang.version>2.0</kotlin.lang.version>
    <kotlin.api.version>2.0</kotlin.api.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>org.jetbrains.kotlin</groupId>
      <artifactId>kotlin-stdlib</artifactId>
      <version>${kotlin.version}</version>
    </dependency>
    <dependency>
      <groupId>org.jetbrains.kotlin</groupId>
      <artifactId>kotlin-reflect</artifactId>
      <version>${kotlin.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.jetbrains.kotlin</groupId>
        <artifactId>kotlin-maven-plugin</artifactId>
        <version>${kotlin.version}</version>

        <!-- Necessário para habilitar <plugin>spring</plugin> -->
        <dependencies>
          <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-allopen</artifactId>
            <version>${kotlin.version}</version>
          </dependency>
        </dependencies>

        <executions>
          <execution>
            <id>compile</id>
            <phase>compile</phase>
            <goals><goal>compile</goal></goals>
            <configuration>
              <jvmTarget>17</jvmTarget>
              <languageVersion>${kotlin.lang.version}</languageVersion>
              <apiVersion>${kotlin.api.version}</apiVersion>
              <compilerPlugins>
                <plugin>spring</plugin>
              </compilerPlugins>
              <sourceDirs>
                <sourceDir>${project.basedir}/src/main/kotlin</sourceDir>
              </sourceDirs>
              <args>
                <arg>-language-version</arg><arg>${kotlin.lang.version}</arg>
                <arg>-api-version</arg><arg>${kotlin.api.version}</arg>
              </args>
            </configuration>
          </execution>

          <execution>
            <id>test-compile</id>
            <phase>test-compile</phase>
            <goals><goal>test-compile</goal></goals>
            <configuration>
              <jvmTarget>17</jvmTarget>
              <languageVersion>${kotlin.lang.version}</languageVersion>
              <apiVersion>${kotlin.api.version}</apiVersion>
              <sourceDirs>
                <sourceDir>${project.basedir}/src/test/kotlin</sourceDir>
              </sourceDirs>
              <args>
                <arg>-language-version</arg><arg>${kotlin.lang.version}</arg>
                <arg>-api-version</arg><arg>${kotlin.api.version}</arg>
              </args>
            </configuration>
          </execution>
        </executions>
      </plugin>

      <!-- adiciona src/main|test/kotlin como source roots -->
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>build-helper-maven-plugin</artifactId>
        <version>3.5.0</version>
        <executions>
          <execution>
            <id>add-kotlin-sources</id>
            <phase>generate-sources</phase>
            <goals><goal>add-source</goal></goals>
            <configuration>
              <sources><source>src/main/kotlin</source></sources>
            </configuration>
          </execution>
          <execution>
            <id>add-kotlin-test-sources</id>
            <phase>generate-test-sources</phase>
            <goals><goal>add-test-source</goal></goals>
            <configuration>
              <sources><source>src/test/kotlin</source></sources>
            </configuration>
          </execution>
        </executions>
      </plugin>

      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <mainClass>com.exemplo.demo.Application</mainClass>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

---

## 3) Importar e rodar

**No IntelliJ 2025**  
- Abrir pelo `pom.xml` e selecionar **JDK 17** como SDK do projeto.

**No terminal (recomendado):**
```bash
mvn -U clean package -DskipTests
mvn spring-boot:run
```

---

## 4) Testes (i18n)

```bash
# EN (default)
curl http://localhost:8080/api/saudacao

# PT via query param
curl "http://localhost:8080/api/saudacao?lang=pt"

# Validação i18n (mensagens localizadas)
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"name":"","email":"x"}'
curl -X POST "http://localhost:8080/api/users?lang=pt" -H "Content-Type: application/json" -d '{"name":"","email":"x"}'
```

---

## 5) Troubleshooting rápido
1) **`api-version (2.2) > language-version (2.0)`**  
   - Garanta no `kotlin-maven-plugin` **em compile e test-compile**: `languageVersion=2.0`, `apiVersion=2.0` e `<args>` com ambos os flags.  
   - Sempre rode pelo **Maven** (não o *Build Project* da IDE).
2) **`Plugin not found: spring (KotlinMavenPluginExtension)`**  
   - Adicione o **`kotlin-maven-allopen`** como *dependency* dentro do `kotlin-maven-plugin`.
3) **`ClassNotFound com.exemplo.demo.Application`**  
   - Verifique `mainClass` no plugin do Boot, pacotes e caminho do arquivo `Application.kt`.

---

## ZIP pronto (opcional)
Use o zip já configurado (abra no IntelliJ):
`kotlin-i18n-rest-2025-v2.zip`







# Kotlin i18n REST - IntelliJ - 2025
- Java 17
- Spring Boot 3.3.3
- Kotlin 2.2.10
- i18n: messages (EN default, PT-BR), troca via `?lang=pt`

## Build & Run
mvn -U clean package -DskipTests
mvn spring-boot:run

## Testes
curl http://localhost:8080/api/saudacao
curl "http://localhost:8080/api/saudacao?lang=pt"
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d "{\"name\":\"\",\"email\":\"x\"}"
curl -X POST "http://localhost:8080/api/users?lang=pt" -H "Content-Type: application/json" -d "{\"name\":\"\",\"email\":\"x\"}"
