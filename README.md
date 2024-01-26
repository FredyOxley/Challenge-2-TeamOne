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

#### Regras de Negócio
+ O nome do produto deve ser único.
+ A descrição do produto deve ter no mínimo 10 caracteres.
+ O valor do produto deve ser um número positivo.


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
1. Certifique-se de que o Maven está instalado (mvn - v)
2. Navegue até o diretório raiz onde o arquivo pom.xml está localizado.
3. Execute o comando mvn clean install para limpar qualquer compilação anteriror e baixar as dependências.
4. Após os passos anteriores, execute o comando mvn spring-boot:run para iniciar a aplicação.
5. A aplicação deverá estar em execução e acessível em http://localhost:8080.
