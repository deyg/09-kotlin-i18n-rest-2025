
# Deploy na AWS

## Resiliência

Para garantir alta disponibilidade e rápida recuperação de falhas:

- Utilize múltiplas zonas de disponibilidade para evitar pontos únicos de falha.
- Configure backups automatizados dos dados críticos.
- Implemente health checks para monitorar e reiniciar instâncias problemáticas.

=======

# Deploy na AWS

Este documento descreve como realizar o deploy da aplicação na Amazon Web Services.

## Segurança

- **IAM Roles**: utilize roles dedicadas para serviços e instâncias, evitando o uso de credenciais estáticas.
- **Security Groups**: restrinja portas e origens de tráfego, liberando apenas o necessário para a aplicação.
- **Políticas de least privilege**: conceda somente as permissões mínimas necessárias para cada usuário ou serviço.
- **Secrets Manager**: armazene chaves e senhas no AWS Secrets Manager e habilite rotação automática quando possível.
- **Monitoramento e logging**: use o Amazon CloudWatch para métricas e alarmes e mantenha logs estruturados para facilitar auditoria e correlação de eventos.

=======
# Deploy AWS

Guia rápido para empacotar o projeto e publicar uma imagem Docker na AWS usando **ECR** e **ECS Fargate** (ou **Elastic Beanstalk**).

---

## 1) Empacotar o projeto

### Via Maven (JAR)
```bash
mvn -U clean package -DskipTests
```
O artefato final fica em `target/demo-0.0.1-SNAPSHOT.jar`.

### Via Docker
```bash
# gerar JAR e montar imagem
mvn -U clean package -DskipTests
docker build -t kotlin-i18n-rest:latest .
```

---

## 2) Repositório no Amazon ECR

1. Autentique o CLI e selecione a região:
   ```bash
   aws configure
   ```
2. Crie o repositório:
   ```bash
   aws ecr create-repository --repository-name kotlin-i18n-rest
   ```
3. Faça login no registro:
   ```bash
   aws ecr get-login-password --region <REGIAO> \
     | docker login --username AWS --password-stdin <AWS_ACCOUNT_ID>.dkr.ecr.<REGIAO>.amazonaws.com
   ```

---

## 3) Enviar a imagem
```bash
docker tag kotlin-i18n-rest:latest \
  <AWS_ACCOUNT_ID>.dkr.ecr.<REGIAO>.amazonaws.com/kotlin-i18n-rest:latest
docker push <AWS_ACCOUNT_ID>.dkr.ecr.<REGIAO>.amazonaws.com/kotlin-i18n-rest:latest
```

---

## 4) Executar na AWS

### Opção A: ECS Fargate
1. Crie um **Cluster** Fargate no console do ECS.
2. Defina uma **Task Definition** com o container apontando para a imagem do ECR.
3. Crie um **Service** ligado ao cluster, escolhendo sub-redes e security groups.
4. Acesse o endpoint público gerado pelo load balancer (se configurado).

### Opção B: Elastic Beanstalk
1. Instale o EB CLI (`pip install awsebcli`).
2. Inicialize o ambiente:
   ```bash
   eb init -p docker kotlin-i18n-rest
   ```
3. Crie e publique o ambiente:
   ```bash
   eb create kotlin-i18n-rest-env
   eb deploy
   ```
4. Abra a URL fornecida pelo Beanstalk.

---

Pronto! O serviço Kotlin/Spring Boot estará disponível na infraestrutura AWS escolhida.

