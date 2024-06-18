
# P4F Challenge

Visão Geral: A plataforma de e-commerce da Loja Goku, uma loja de alimentos online em crescimento com uma taxa de crescimento anual de 20%, busca melhorar seu sistema para garantir a integridade dos endereços de entrega dos clientes. O sistema atual carece de um processo de registro de endereços confiável, levando a endereços incorretos ou incompletos, resultando em sobrecarga pós-venda.

# Requisitos:

Desenvolver um sistema de registro de usuários para manter registros de endereços.

Desenvolver um sistema de registro de endereços.

Criar uma página para consultar endereços por CEP.

Implementar uma página de administração com funcionalidade de login para usuários registrados.

# Requisitos Técnicos:

Desenvolver serviços que possam atender tanto à plataforma web quanto às plataformas móveis, para futuramente expandir o sistema
Implementar uma estrutura de cache para garantir o desempenho do sistema.

Integrar uma solução de segurança para prevenir acesso não autorizado a informações sensíveis



## Screenshots

(api.PNG)


## Stack utilizada


**Back-end:** java 17, Spring Framework, Maven

**Database:** MongoDB

**Test:** JUnit, Mockito

**Security:** JWT token

**Docker**


## Instalação

Tenha o Docker instalado

```bash
  cd goku-e-commerce
  docker-compose build
  docker-compose up
```
Após isso entre na documentação abaixo e teste a API
## Documentação da API

http://localhost:8080/swagger-ui/index.html#/

## Uso/Exemplos
Utilize como exemplo para teste
```JSON
{
 "username": "user",
 "login": "user@example.com",
 "password": "password",
 "userRole": "USER"
}

{
"cep": "01000000"
}
```


