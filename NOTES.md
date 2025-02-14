# TO-DO LIST:

- Colocar um check antes de salvar para ver se existe uma entity repetida (ver aula 12.11 Optional <User> findByEmail(
  String email))
- Colocar regras de negócio para ver quem pode adicionar pedidos num restauramte, e por ai vai
- Revisar e testar a parte de atualização utilizando o ignoreProperties para que os relacionamentos não sejam removidos
- Falta fazer a entidade FotoProduto
- Implementar o mapeamento @ManyToMany entre as entidades Group e Permission
- Resolver os bugs do projeto. Testar TODOS os métodos verificando TODOS os logs de TODAS as entidades
- Ainda não temos entity, controller, service e VOs para
    - GroupPermission
    - RestaurantPaymentMethod
    - UserGroup
- Testar equals, hashCode e toString
- Conferir o comportamento de BeanUtils.copyProperties
- Escrever massa de testes, usar utc_timestamp ou now() para campos de data e hora
- Conferir a ordem dos atributos da entidade order e da sua tabela
- Conferir todos os códigos created dentro dos controllers
- Ver uma forma de usar as exceções customizadas - colocar as exceptions de BAD_REQUEST
- Ver DataIntegrityViolationException retornar status 404 em vez de 500
- implementar e escrever testes de integração para entity already exists
- implementar parte de permissões para poder testar deletar grupo em uso
- Implementar RestaurantPaymentMethod para poder testar remover payment method in use
- Implementar Permissions nos Groups para poder testar remover permission in use
- Continuar testes de API: Testes estão apresentando um comportamento não esperado.
  Seria bom investigar uma maneira do flyway limpar o banco a cada execução
- Alterar OffsetDateTime para os campos de data
- Procurar por Spring jackson annotations
- Adicionar @Valid em todo o projeto
- Implementar endpoints que associam restaurantes e formas de pagamento (ver aula 12.12 se necessário)
- Alterar lista de forma pagamento - restaurante para Set pois não aceita valores duplicados
- Fazer desafio 12.13
- Fazer desafio 12.14
- Fazer desafio 12.15
- Fazer desafio 12.16
- Fazer desafio 12.17
- Fazer 12.18
- Fazer 12.19
- Fazer desafio 12.21
- Rever o endpoint de criação de pedido para ver se faz sentido o cliente passar alguns valores como subtotal, etc...
  OrderedItem deve ser passado na criação do pedido
- Fazer 12.22
- Fazer desafio 12.23
- Sobrescrever o método de set password do user para utilizar criptografia e colocar isso dentro da própria classe do
  usuário
- Colocar UUID para todas as tabelas
- Fazer 13.3
- Fazer 13.6
- Fazer 13.7
- Podemos ter um VO intermediário contendo um resumo mais enxuto para ser usado nos endpoints de findAll
  e um VO mais completo para os endpoints findById
- Implementar endpoints de estatísticas conforme aulas 13.13, 13.14 e 13.15
- Colocar tag em todos os controllers para melhorar o título. a tag vem logo na assinatura do controller
  https://docs.swagger.io/swagger-core/v2.2.0/apidocs/io/swagger/v3/oas/annotations/tags/Tag.html
- Podemos colocar a anotação @Schema em cima de cada classe de entity e VO para descrevermos o seu propósito
- URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand().toUri();
- Adicionar anotações Swagger nos VOs de response. Request já foi feito
- Implementar parte de responsáveis pelo restaurante
- Trocar logger para usar da classe Sl4fj, anotando a classe como @Slf4j e usando log.info, log.warn, log.debug,
  log.error
- resource server implementado automaticamente. necessário configurar o endpoint de check_token
- 22.11. Configurando o Resource Server com a nova stack do Spring Security
- Usar BCryptPasswordEncoder ao salvar senhas no banco de dados. Ver aula 23.15
- 