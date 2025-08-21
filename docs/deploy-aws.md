# Deploy na AWS

Este documento descreve como realizar o deploy da aplicação na Amazon Web Services.

## Segurança

- **IAM Roles**: utilize roles dedicadas para serviços e instâncias, evitando o uso de credenciais estáticas.
- **Security Groups**: restrinja portas e origens de tráfego, liberando apenas o necessário para a aplicação.
- **Políticas de least privilege**: conceda somente as permissões mínimas necessárias para cada usuário ou serviço.
- **Secrets Manager**: armazene chaves e senhas no AWS Secrets Manager e habilite rotação automática quando possível.
- **Monitoramento e logging**: use o Amazon CloudWatch para métricas e alarmes e mantenha logs estruturados para facilitar auditoria e correlação de eventos.

