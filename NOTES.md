# TO-DO LIST:

1. Colocar um check antes de salvar para ver se existe uma entity repetida (ver aula 12.11 Optional <User> findByEmail(
   String email))
2. Colocar regras de negócio para ver quem pode adicionar pedidos num restauramte, e por ai vai
3. Revisar e testar a parte de atualização utilizando o ignoreProperties para que os relacionamentos não sejam removidos
4. Falta fazer a entidade FotoProduto
5. Implementar o mapeamento @ManyToMany entre as entidades Group e Permission
6. Resolver os bugs do projeto. Testar TODOS os métodos verificando TODOS os logs de TODAS as entidades
7. Ainda não temos entity, controller, service e VOs para
    - GroupPermission
    - RestaurantPaymentMethod
    - UserGroup
8. Testar equals, hashCode e toString
9. Conferir o comportamento de BeanUtils.copyProperties
10. Escrever massa de testes, usar utc_timestamp ou now() para campos de data e hora
11. Conferir a ordem dos atributos da entidade order e da sua tabela
12. Conferir todos os códigos created dentro dos controllers
13. Ver uma forma de usar as exceções customizadas - colocar as exceptions de BAD_REQUEST
14. Ver DataIntegrityViolationException retornar status 404 em vez de 500
15. implementar e escrever testes de integração para entity already exists
16. implementar parte de permissões para poder testar deletar grupo em uso
17. Implementar RestaurantPaymentMethod para poder testar remover payment method in use
18. Implementar Permissions nos Groups para poder testar remover permission in use
19. Continuar testes de API: Testes estão apresentando um comportamento não esperado.
    Seria bom investigar uma maneira do flyway limpar o banco a cada execução
20. Alterar OffsetDateTime para os campos de data
21. Procurar por Spring jackson annotations
22. Adicionar @Valid em todo o projeto
23. Implementar endpoints que associam restaurantes e formas de pagamento (ver aula 12.12 se necessário)
24. Alterar lista de forma pagamento - restaurante para Set pois não aceita valores duplicados
25. Fazer desafio 12.13
26. Fazer desafio 12.14
27. Fazer desafio 12.15
28. Fazer desafio 12.16
29. Fazer desafio 12.17
30. Fazer 12.18
31. Fazer 12.19
32. Fazer desafio 12.21
33. Rever o endpoint de criação de pedido para ver se faz sentido o cliente passar alguns valores como subtotal, etc...
    OrderedItem deve ser passado na criação do pedido
34. Fazer 12.22
35. Fazer desafio 12.23
36. Sobrescrever o método de set password do user para utilizar criptografia e colocar isso dentro da própria classe do
    usuário
37. Colocar UUID para todas as tabelas
38. Fazer 13.3
39. Fazer 13.6
40. Fazer 13.7
41. Podemos ter um VO intermediário contendo um resumo mais enxuto para ser usado nos endpoints de findAll
    e um VO mais completo para os endpoints findById
42. Implementar endpoints de estatísticas conforme aulas 13.13, 13.14 e 13.15
43. Colocar tag em todos os controllers para melhorar o título. a tag vem logo na assinatura do controller
    https://docs.swagger.io/swagger-core/v2.2.0/apidocs/io/swagger/v3/oas/annotations/tags/Tag.html
44. Podemos colocar a anotação @Schema em cima de cada classe de entity e VO para descrevermos o seu propósito
45. URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand().toUri();
46. Adicionar anotações Swagger nos VOs de response. Request já foi feito
47. Implementar parte de responsáveis pelo restaurante
48. Trocar logger para usar da classe Sl4fj, anotando a classe como @Slf4j e usando log.info, log.warn, log.debug,
    log.error
49. resource server implementado automaticamente. necessário configurar o endpoint de check_token
50. 22.11. Configurando o Resource Server com a nova stack do Spring Security
51. Usar BCryptPasswordEncoder ao salvar senhas no banco de dados. Ver aula 23.15
52. Rever todos os testes de integração
53. Ver erro que ocorre no console quando uma query de página é retornada
54. Alterar entidade usuário para abrigar novos campos como username, timezone, currency, fullName (alterar no
    authorization server também)
55. Alterar o after migrate para incluir esses novos campos
56. Rever aula sobre formato de data e hora
57. Testar o método DELETE com IDS que não existem