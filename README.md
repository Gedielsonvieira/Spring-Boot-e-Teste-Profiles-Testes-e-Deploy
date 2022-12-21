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


