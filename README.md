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












-------------------------------------------------------------------------------------------------------


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
