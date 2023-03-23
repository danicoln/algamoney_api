# Fullstack Angular e Spring

<p>Treinamento do curso Fullstack Angular e Spring do AlgaWorks</p>

## Nota para atualização de versões
<p>Este material te guiará na atualização das versões do ambiente de programação necessário para o curso.</p>

## Java JDK 11 ou superior

<p>A versão 2.5.0 do Spring Boot possui compatibilidade com o JDK 8, utilizado nos vídeos. Mas, é indicada a utilização do OpenJDK 11, por ser a versão estável com suporte a longo prazo (LTS - Long Term Support).</p>

<a href="https://adoptopenjdk.net/">Link para download </a>


## Spring Boot
<p>Versão 2.5.0 (configurada na criação do projeto na aula 3.1)</p>

## IDE utilizada
<p>IntelliJ IDEA<p>

## Postman
<a href="https://www.postman.com/downloads/">Link para download </a>

## Banco de Dados
<p>PostgreSQL</p>

# Criando o projeto da API

<p>Este documento irá te auxiliar a fazer esta aula com a versão 2.7.X (por exemplo, 2.7.0) do Spring Boot e suas dependências.</p>

<p>Observação: Este documento é apenas para os que querem usar as versões mais recentes das dependências contidas nesta aula. Ao longo do curso iremos atualizar as dependências, mas caso queira atualizar agora, use este documento como guia.</p>

## Informações

<p>O processo será o mesmo realizado na aula, porém veremos algumas pequenas diferenças nos resultados.</p>

### Na versão 4.11.X do STS (por exemplo, 4.11.0 e em versões superiores), as seguintes dependências tiveram seu nome alterado. 

<ul>
<li>Web -> Spring Web</li>
<li>JPA -> Spring Data JPA</li>
<li>DevTools - Spring Boot DevTools</li>
</ul>

## Maven

<p>Com relação ao maven, algumas poucas diferenças são notadas no arquivo pom.xml gerado:</p>

<li>Versão do Spring Boot</li>

<p>A ferramenta irá sugerir a versão 2.7.X do Spring Boot (por exemplo, 2.7.0).</p>

<pre>
<code>
<span><</span><span>parent</span><span>></span>
    <span><</span><span>groupId</span><span>></span>org.springframework.boot<span><</span><span>/groupId</span><span>></span>
    <span><</span><span>artifactId</span><span>></span>spring-boot-starter-parent<span><</span><span>/artifactId</span><span>></span>
    <span><</span><span>version</span><span>></span>2.7.X<span><</span><span>/version</span><span>></span>
    <span><</span><span>relativePath/</span><span>></span>
    <span><</span><span>/parent</span><span>></span>
</code>
</pre>

<li>Packaging</li>

<p>No arquivo gerado na aula, é criado junto com as informações do projeto, a informação de empacotamento (que no caso, definimos na criação que seria jar).</p>
<p>Nesta versão da ferramenta, esta informação não foi criada, pois jar é a opção default de empacotamento do maven.</p>

<pre>
  <code>
      <span><</span><span>groupId</span><span>></span>com.algaworks.algamoney_api<span><</span><span>/groupId</span><span></span>
      <span><</span><span>artifactId</span><span>></span>algamoney_api<span><</span><span>/artifactId</span><span>></span>
      <span><</span><span>version</span><span>></span>1.0.0-SNAPSHOT<span><</span><span>/version</span><span>></span>
      <span><</span><span>name</span><span>></span><span><</span><span>algamoney_api</span><span>/name</span><span>></span>
      <span><</span><span>description</span><span>></span><span><</span><span>Demo project for Spring Boot</span><span>/description</span><span>></span>
  </code>
</pre>

## Resumo das versões para este caso

<ul>
  <li>Java: 17 LTS</li>
  <li>STS: 4.11.0 LTS</li>
  <li>Spring Boot: 2.7.0 LTS</li>
</ul>

<p>Em geral, estas alterações serão validas para quaisquer versões que você utilizar, desde que seja a mesma ou superior às citadas acima, no nível minor, ou seja, se estiver dentro do 4.11.X (no caso do STS) e 2.7.X (no caso do Spring Boot).</p>


## Atualização do Flyway 8

<p>Devido à atualização da dependência do Flyway para a versão 8 no Spring Boot 2.7.0, é necessário adicionar uma dependência a mais do Flyway, dependendo do banco de dados utilizado.</p>

<p>Caso utilize o MySQL, adicione:</p>
<pre>
  <code>
      <span><</span><span>dependency</span><span>></span>
          <span><</span><span>groupId</span><span>></span>com.algaworks.algamoney_api<span><</span><span>/groupId</span><span></span>
          <span><</span><span>artifactId</span><span>></span>algamoney_api<span><</span><span>/artifactId</span><span>></span>
      <span><</span><span>/dependency</span><span>></span>
  </code>
</pre>

### A tabela schema_version
<p>No Spring 2.5.0, o Flyway é instalado na versão 7.9.1. Nesta versão, a tabela schema_version, gerada de forma automática, aparece com o nome flyway_schema_history.</p>


# Desafio 3.7 : Retornar 404 caso não exista a categoria

## Minha Resolução
![minhaResolucao.png](images/minhaResolucao.png)
<p>Minha resolução no momento do desafio</p>

### Utilizando isPresent()

![img.png](images/img.png)
<p>Esta sugestão é bem parecida com a resolução da própria aula, porém ao invés de ficarmos checando manualmente de objeto é null ou não, o Optional nos dá algumas facilidades.</p>
<p>Neste caso utilizamos o método isPresent, que nada mais é que uma comparação “obj != null”, e finalizamos com um ternário, igual a resolução da aula.</p>

### Utilizando o Map

![resolucaoComMap.png](images/resolucaoComMap.png)
<p>Neste exemplo, fizemos a chamada ao método findById que nos retornou um Optional do tipo Categoria.</p>
<p>Usamos o método map, para transformar o objeto que foi retornado como Optional (isso é feito caso o mesmo não seja null), depois retorna o objeto transformado em Optional novamente.</p>
<p>Como o retorno do próprio map também é um Optional, podemos utilizar o método orElse, para retornarmos notFound, como mostrado na imagem acima.</p>

# 3.10 Validando valores inválidos com Bean Validation

<p>O Hibernate Validator não é mais adicionado como dependência no spring-boot-starter-web. É necessário adicionar a dependência spring-boot-starter-validation para utilizar as validações @NotNull</p>

 ![dependenciaBeanValidation.png](images/dependenciaBeanValidation.png)