# Challenge II - PB Springboot Dez 2023
> Status: Entregue
-------------------------------------------------------------------------------------------------------


O projeto consiste em uma solução robusta desenvolvida em Spring Boot para gerenciar informações de produtos e pedidos. A API oferece todas as operações de um CRUD para a execução de produtos e pedidos.

### Colaboradores

<table>
  <tr>
    <td>E-mail</td>
    <td>GitHub</td>
  </tr>
  <tr>
    <td>pedro.bastos.pb@compasso.com.br</td>
    <td>oPedr1nhoo</td>
  </tr>
   <tr>
    <td>raphael.araujo.pb@compasso.com.br</td>
    <td>raphael-araujo</td>
  </tr>
   <tr>
    <td>alex.spohr.pb@compasso.com.br</td>
    <td>AlexSpohr</td>
  </tr>
   <tr>
    <td>leandro.paupitz.pb@compasso.com.br</td>
    <td>lepaupitz</td>
  </tr>
   <tr>
    <td>rafael.cazali.pb@compasso.com.br</td>
    <td>CazaliU</td>
  </tr>
    <tr>
    <td>fredy.oxley.pb@compasso.com.br</td>
    <td>FredyOxley</td>
  </tr>
</table>

-------------------------------------------------------------------------------------------------------

### Tecnologias Utilizadas
<table>
  <tr>
    <td>Java</td>
    <td>Spring</td>
    <td>MySql</td>
  </tr>
  <tr>
    <td>17.*</td>
    <td>3.2</td>
    <td>8.0</td>
  </tr>
</table>

-------------------------------------------------------------------------------------------------------

### Setup
1. Clone o repositório
```
git clone https://github.com/FredyOxley/Challenge-2-TeamOne.git
```
2. Configure o banco de dados no arquivo `application.properties`

3. Execute a aplicação
```
mvn spring-boot:run
```
A aplicação deverá estar em execução e acessível em http://localhost:8080/

-------------------------------------------------------------------------------------------------------

### Endpoints API Produtos

`GET /api/products`

Exibe a lista de produtos


Resposta:
```
{
  "id": 1,
  "nome": "Nome produto",
  "descricao": "Descrição do produto",
  "valor": 10.5
}
```

`GET /api/products/{id}`

Recupera um produto específico por ID

Resposta:
```
{
  "id": 1,
  "nome": "Nome do Produto",
  "descricao": "Descrição do Produto",
  "valor": 150.0
}
```

`POST /api/products`

Adiciona um novo produto

Solicitação:
```
{
  "nome": "Novo Nome do Produto",
  "descricao": "Nova Descrição do Produto",
  "valor": 150.0
}
```

`DELETE /api/products/{id}`

Remove um produto pelo ID

Resposta:

```
{
  204 No Content
}
```

`PUT /api/products/{id}`

Atualiza informações de um produto específico por ID

Resposta:

```
{
    "id": 3,
    "nome": "Novo Nome do Produto a",
    "descricao": "Nova Descrição do Produto a",
    "valor": 200.0
}
```
-------------------------------------------------------------------------------------------------------

### Endpoints API Pedidos

`GET /api/orders`

Exibe a lista de pedidos

Resposta:
```
{
  "produtos": [
{
  "idProduto": 1,
  "quantidade": 2
},
{
  "idProduto": 2,
  "quantidade": 5
}
],
  "endereco": {
  "numero": 10,
  "complemento": "casa",
  "cep": "01310930"
},
  "metodoPagamento": "PIX"
}
```

`GET /api/orders/{id}`

Exibe a lista de pedidos por ID

Resposta:
```
{
  "produtos": [
{
  "idProduto": 1,
  "quantidade": 2
},
],
  "endereco": {
  "numero": 10,
  "complemento": "casa",
  "cep": "01310930"
},
  "metodoPagamento": "PIX"
}
```

`POST /api/orders/{id}`

Adiciona um novo pedido

```
{
  "produtos": [
{
  "idProduto": 1,
  "quantidade": 2
},
{
  "idProduto": 2,
  "quantidade": 5
}
],
  "endereco": {
  "numero": 10,
  "complemento": "casa",
  "cep": "01310930"
},
  "metodoPagamento": "PIX"
}
```

`PUT /api/orders/{id}`

Atualiza um pedido pelo ID

Solicitação:

```
{
  "produtos": [
{
  "idProduto": 1,
  "quantidade": 2
},
{
  "idProduto": 2,
  "quantidade": 5
}
],
  "endereco": {
  "numero": 20,
  "complemento": "apartamento",
  "cep": "01310930"
},
  "metodoPagamento": "CARTAO DE CRÉDITO"
}
```

### Link Swagger

http://localhost:8080/swagger-ui/index.html



