# Challenge II - PB Springboot Dez 2023
O projeto consiste no desenvolvimento de uma API REST para um e-commerce.

> Status: Desenvolvimento ⚠️

### Funcionalidades
1. Produto: A funcionalidade Produto permite que os usuários criem, leiam, atualizem e excluam produtos.

#### Operações CRUD:
<table>
  <tr>
    <td>Métodos</td>
    <td>URL</td>
    <td>Descrição</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/products</td>
    <td>Lista produto: deve retornar todos os produtos.
</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/products/:id</td>
    <td>Buscar produto: Retorna as informações de um produto específico.</td>
  </tr>
    </tr>
  <tr>
    <td>POST</td>
    <td>/products</td>
    <td>Cadastrar pedido: Cria um novo produto.</td>
  </tr>
    </tr>
  <tr>
    <td>PUT</td>
    <td>/products/:id</td>
    <td>Atualizar produto: atualiza as informações de um pedido existente.</td>
  </tr>
  </tr>
    <tr>
    <td>DELETE</td>
    <td>/products/:id</td>
    <td>Excluir produto: Excluir um produto existente.</td>
  </tr>
</table>


#### Tabela Produtos:
+ id : chave primária, auto-incremento
+ name : nome do produto
+ value : preço/valor do produto
+ description : descrição do produto





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

### Como Rodar a Aplicação
1.x
2.y
