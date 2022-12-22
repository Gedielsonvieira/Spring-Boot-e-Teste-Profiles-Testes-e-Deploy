# Spring Boot e Teste: Profiles, Testes e Deploy

## Mais segurança

### Atualizando o Spring Boot

É importante ler as release notes das novas versões do Spring Boot, para identificar possíveis quebras de
compatibilidades ao atualizar a aplicação.

### Autorização baseada em Roles

A maneira correta de restringir o acesso a determinado endpoint, baseado no perfil do usuário é adicionar a chamada ao
método hasRole(“NOME_DO_ROLE”) no código de configuração do endpoint na classe SecurityConfigurations.

## Profiles

> No Spring Boot esse conceito chamado de profile é que temos um perfil, um ambiente e que dependendo de qual ambiente
> estamos rodando o software, podem ter configurações que sejam distintas, que tenham que sofrer alterações; e podem ter
> funcionalidades também que queremos habilitar ou desabilitar.

### Principal motivo para utilizar profiles:<br>

> Com profiles podemos ter múltiplas configurações distintas na aplicação, para cada tipo de ambiente, como
> desenvolvimento, testes e produção.

### Anotação @Profile

O comportamento do Spring ao encontrar uma classe anotada com **@Profile(“prod”)**
é que a classe apenas é carregada se o profile ativo for prod.

Porém precisamos informar para o Spring qual o perfil/profile que está ativo no momento e para fazer isso no IntelliJ,
seguem os passos:

* 1° - Acessar a classe main
* 2° - Clicar com o botão direito do mouse em cima do run
* 3° - Clicar em <i>modify run configurations</i>
* 4° - Adicionar o perfil desejado em <i>program arguments</i>, Ex: --spring.profiles.active=dev

**Caso não seja definido um profile para a aplicação, o Spring considera que o profile ativo dela é o profile de nome
default, que carrega todas as classes que não estão configuradas com o @Profile**

## Testes Automatizados

### Testes Automatizados com Spring Boot

Testes para saber se as funcionalidades estão funcionando como o esperado
no java temos junit - principal biblioteca p/ testes atomatizados e o spring bot tambem tem um modulo especipico para
fazer testes autoimatixados

o Spring já tem uma especialização para testes de Repository, conseguimos isso através da anotação:

* **@DataJpaTest** - Ela já provê algumas coisas no caso de Repository:
    * Em que todos os métodos de teste já vão ter um controle de transação, para injetar um EntityManager específico
      para testes,
      então já tem todo um tratamento específico para testes de Repository. E por isso é mais apropriado utilizar essa
      anotação ao invés do @SpringBootTest


* A anotação **@SpringBootTest** deve ser utilizada nas classes de testes automatizados para que o Spring inicialize o
  servidor e disponibilize os beans da aplicação.

### Testando o repository com outro Banco de Dados

> Por padrão o teste do Spring Boot considera que você vai fazer o teste com um banco de dados em memória.
> Então se na sua dependência você não tivesse o H2 ou outro banco em memória, você só tivesse o MySQL, por exemplo, o
> Spring Boot daria uma mensagem dizendo “Você até tem um banco de dados, que é o MySQL, mas para fazer os testes eu uso
> um banco de dados em memória e você não tem ele como dependência no projeto”.
> E para solucionar isso indicamos ao Spring através da anotação **@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)** que os testes da interface ‘Repository` devem ser realizados em outro banco de
> dados que não seja o h2.

#### Boa pratica de testes

> Ter uma separação dos dados da aplicação, do ambiente de desenvolvimento de maneira isolada dos dados que é usado para
> os testes (um BD para usar durante o desenvolvimento e um para usar durante os testes), e isso se dá como forma de
> evitar erros pois ao usar o BD da aplicação pode ser que ele esteja vazio ou populado então para que não haja
> interferencia nos testes automatizados cria-se um outro BD para teste.
> E para fazer o Spring forçar o profile ativo como sendo o de teste, colocamos a anotação **@ActiveProfiles(“test”)**,
> e
> quando rodar essa classe de teste ele vai considerar que o ambiente que está ativo no momento é o ambiente de teste.

### Testando um controller

> Para testar um Controller não vamos injetar o Controller, vamos injetar outra classe que é uma classe utilitária do
> módulo de teste do Spring e ela se chama MockMvc.
> Essa é a classe que faz um Mock, que simula uma requisição MVC. Então não vamos injetar diretamente o
> Controller, vamos injetar como se fosse o Postman. Vamos pensar que esse é o Postman e ele vai disparar a requisição.
> E
> ela vai cair automaticamente no nosso Controller.

* **@WebMvcTest** - Ao testar o controller, utilizamos essa anotação pois assim o Spring carrega no contexto da
  aplicação só as
  classes da parte MVC. Então só os Controllers, RestController, ControllerAdvice, tudo que tem a ver com a camada MVC
  do
  projeto.


* **@SpringBootTest** - Caso precisamos carregar outros módulos da nossa aplicação utilizamos a anotação mais genérica,
  carregando todas as nossas classes.


* **@AutoConfigureMockMvc** - Serve para conseguirmos injetar um MockMvc

### Importante

* Na notação **@Profile** podemos passar um ou vários profiles, ou seja, uma classe pode ser carregada para um ou mais
  profiles.


* Nos testes, como não estamos rodando o projeto pela classe ForumApplication, por aquele Main, o Spring não vai
  ler o argumento da maquina virtual(program arguments). Então ele não vai saber qual é o profile ativo do momento. Ele
  vai carregar aquele “default”, ou seja, no teste não conseguimos mudar o perfil pelo program arguments do run pois os
  testes não rodam pelo main.

## Deploy

### Gerando o jar da aplicação via Maven

> Objetivo da aula - Fazer um Deploy e gerar o pacote de uma aplicação com Spring Boot.

Ao realizar um Build, uma aplicaçãos Spring boot por padrão gera um aquivo .jar

Para conseguirmos gerar o build e gerar o pacote da aplicação devemos seguir os passos:

* 1° - Acessar o diretório raiz no terminal
* 2° - Executar o comando: **mvn clean package** - comando utilizado para que o Maven crie o jar da aplicação.
  Esse é justamente o comando mais apropriado para que o Maven compile as classes do projeto, execute os testes
  automatizados e por fim crie o jar da aplicação.**(O build da aplicação é realizado via maven, com o comando mvn clean
  package.)**
* 3° - Através disso o pacote .jar vai ser gerado
* 4° - Para acessar o pacote .jar e executá-lo executamos o comando ls para ver os diretórios, acessamos a pasta
  target e executamos o camando java -jar nome do arquivo a ser rodado.
* 5 ° - **ctrl + c** para encerrar a aplicação pelo terminal

### Externalizando senhas com variáveis de ambiente

> Objetivo aula - criar configurações específicas para o profile ambiente de produção.

#### Boa prática ao passar dados sensiveis do arquivo properties

* Geralmente se passa essas configurações via variáveis de ambiente, para não ficar expondo isso no próprio código da
  aplicação.
    * Dessa forma essas propriedades serão lidas pelas variáveis de ambiente que serão configuradas no ambiente de
      produção, no servidor de produção.

#### Duas maneiras de passar as variaveis de ambiente para o Spring:

* **Exportado no sistema operacional:**<br>
  export FORUM_DATABASE_URL=DATABASE:h2:mem:alura-forum<br>
  export FORUM_DATABASE_USERNAME=sa<br>
  export FORUM_DATABASE_PASSWORD=<br>
  export FORUM_JWT_SECRET=123456<br>


* **Ou direto no comando, no prompt via parametro da jvm**<br>
  java -jar -Dspring.profiles.active=prod -DFORUM_DATABASE_URL=jdbc:h2:mem:alura-forum -DFORUM_DATABASE_USERNAME=sa
  -DFORUM_DATABASE_PASSWORD= -DFORUM_JWT_SECRET=123456 forum.jar

#### Vantagens de se utilizar variáveis de ambiente em propriedades declaradas nos arquivos properties do Spring Boot?

✅ Tornar mais segura a aplicação<br>
Ao se utilizar variáveis de ambiente, evitamos deixar exposto no código fonte da aplicação senhas e outras informações
sensíveis.

✅ Flexibilizar e facilitar a alteração dos valores de determinadas propriedades<br>
Ao se utilizar variáveis de ambiente, podemos alterar facilmente uma ou mais propriedades da aplicação, sem que seja
necessário alterar o código fonte dela.

