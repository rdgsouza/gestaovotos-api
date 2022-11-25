   Desafio Técnico
   
   Objetivo do desafio: 

   No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação.
   A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação.
   Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:

   1 - Cadastrar uma nova pauta;

   2 - Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo
determinado na chamada de abertura ou 1 minuto por default);

   3 - Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é
identificado por um id único e pode votar apenas uma vez por pauta);

   4 - Contabilizar os votos e dar o resultado da votação na pauta

   Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces
pode ser considerada como autorizada. A escolha da linguagem, frameworks e bibliotecas é livre (desde que
não infrinja direitos de uso).

   É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.
   

   # Procedimento para rodar o projeto: 
   
   Requisitos:  <br>
   JDK 11.0.17 ou superior - Apenas um JRE pode não ser suficiente, um JDK completo é recomendado.  <br>
   Spring Boot 2.7.6  <br>
   MySql 8  <br>
   Docker version 20.10.21  <br>

   
   O projeto pode ser rodado tanto pelo Docker quanto pela IDE por ex. Intellij ou Spring Tool Suite.
   
   Para rodar o projeto por ex. pela IDE Intellij. <br>
   Obs: Como precisamos trabalhar em 2 ambientes de desenvolvimento que é o development e production pra isso criamos esses 2 profiles:
   
   application-development.properties e o
   application-production.properties
   
   Antes de rodar o projeto pela IDE é preciso passar qual o profile que será usado. Cada profile tem suas devidas configurações.
   
   Para escolher o profile por ex. pela IDE Intellij. Vá até no menu - Run - Profile - Edit Configurations.. No campo 'Active profiles' digite 'development' caso queira usar o profile de desenvolvimento. 
   Caso queira usar o profile de produção digite 'production'
   
   Após escolher o profile é só clicar no ícone que executa o projeto e pronto!
    
    Obs: Quando executado o projeto pelo profile development. Temos um arquivo chamado afterMigrate.sql que insere no banco de dados algumas infomações apenas pra fins de testes mais quando usado o profile production não é usado o arquivo afterMigrate.sql, mantendo assim o banco de dados de produção sempre consistente.
    
    
    Para rodar o projeto pelo Docker. Vá até a pasta do projeto onde se encontra os arquivos Dockerfile e docker-compose.yml e e digite o seguinte comando:
    
    ./mvnw package -Dmaven.test.skip=true -Pdocker

    Esse comando faz o empacotamento do projeto e ao mesmo tempo gera a imagem da nossa aplicação.
    
    Em seguida digite o seguinte comando:
    
    MYSQL_ROOT_PASSWORD=1040651820 DB_HOST=gestaovotos-mysql SPRING_PROFILES_ACTIVE=development JDBC_DATABASE_USERNAME=root JDBC_DATABASE_PASSWORD=1040651820 docker-compose up
    
    Obs: Observe que existem variáveis de ambiente. 
    A variável 'DB_HOST' vai receber o host do mysql. Esse host se encontra expecificado no docker-compose.yml. A variável recebe o que foi definido lá e própio Docker vai resolver o host de conexão com o mysql.
    A outra variável chamada 'SPRING_PROFILES_ACTIVE' é o profile do Spring no qual vai rodar o projeto de acordo com o que for passado tendo em vista que existem dois profiles:
    development e production.
    A outra variável chamada 'JDBC_DATABASE_PASSWORD' nela você define qual a senha para seu banco de dados que sera levantado no Docker
    A outra variável chamada 'MYSQL_ROOT_PASSWORD' nela passamos a senha que defimos para o mysql. A nossa aplicação faz a conexão com o banco de dados. Precisamos dessa informação para fazer a conexão. Essa variável de ambiente esta definina nos dois arquivos de profile que é o application-development.properties e o application-production.properties.
    
   Obs: O projeto quando rodado com o Docker levanta dois containers da mesma aplicação. Com isso implementei o balanceamento de carga para melhorar a performance da aplicação. 
   Usei o nginx para gerenciar esse balanceamento. 
   Essa configuração se encontra no arquivo docker-compose.yml.
   
   
   Sobre a documentação da API
   
   A api foi documentada usando o SpringDoc que tem como base Swagger!
   
   Para acessar a documentação completa da api com o projeto rodando localmente. Acesse a seguinte url no seu navegador:
   
   http://localhost:8080/swagger-ui/index.html#/
   
   
   Para acessar a documentação completa da api com o projeto rodando na nuvem. Acesse a seguinte url no seu navegador:
   
   http://20.195.202.49:80/swagger-ui/index.html#/
   
   
   Para fazer o teste da aplicação pelo Postman, disponibilizei a collection das requisições que serão utilizadas para consumir a API.
   
   Obs: Para fazer o teste no Postman com a aplicação que disponibilizei na nuvem rodando dentro de containers Docker
   No momento que for fazer as requisições na aplicação. No 'localhost' coloque o seguinte ip: 20.195.202.49 e troque a porta 8080 pela porta 80. 
 
   Ficando assim a URI para requisição: <br>
   
   Ex: localhost:8080/v1/pautas/1 <br>
      
       20.195.202.49:80/v1/pautas/1
   
   
   Faça o download do arquivo de collection das requisiçoões e importe para seu Postman: https://drive.google.com/file/d/13hP_yArVL9SuuIbcS3SVH1DOSJfL__Dy/view?usp=share_link <br>
   
 
   Por fim é isso que precisamos para rodar o projeto!
