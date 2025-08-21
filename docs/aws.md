# Guia AWS

## Escalabilidade

Aplicações em produção precisam se adaptar a picos de tráfego e quedas de demanda. A AWS oferece recursos nativos para escalar de forma automática e integrar com serviços gerenciados.

### Auto Scaling Groups
- Defina um *Launch Template* ou *Launch Configuration* com a AMI e tamanho das instâncias.
- Especifique a quantidade mínima, desejada e máxima de instâncias.
- Configure políticas de escala com métricas do CloudWatch (ex.: CPU, requisições por segundo).
- Associe o ASG a sub-redes privadas e, quando necessário, a *Load Balancers*.

### Balanceadores de Carga (ALB)
- Crie um Application Load Balancer em sub-redes públicas e associe *target groups* que apontam para o Auto Scaling Group.
- Configure *listeners* (HTTP/HTTPS) e *health checks* para remover instâncias não saudáveis.
- Habilite *sticky sessions* ou HTTPS offload conforme a necessidade da aplicação.

### Integrações com serviços gerenciados
- **RDS**: utilize um banco relacional gerenciado; configure *security groups* e parâmetros de conexão para que as instâncias no ASG acessem o endpoint do banco.
- **ElastiCache**: adicione camadas de cache com Redis ou Memcached; exponha o endpoint nos ambientes e ajuste políticas de segurança para liberar o acesso.
- Combine esses serviços com o Auto Scaling para que novas instâncias já iniciem configuradas com os endpoints e credenciais corretos.
