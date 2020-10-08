## DOCUMENTAÇÃO

<h2>Sumário</h2>
<ul>
  <li>Passos</li>
  <li>Configurações</li>
  <li>Swagger</li>
  <li>Testes Unitários</li>
  <li>Endpoints</li>
</ul>

<h2>Passos</h2>

<ol>
  <li>Verifique as configurações com a Amazon S3</li>
  <li>Crie duas mídias - <b>(Use os vídeos disponibilizados na pasta '/videos' do repositório)</b></li>
  <li>Lista todas as mídias inclusive aquelas que foram excluídas - <b>(Passando o parâmetro como '1' que significa 'true' para todos)</b></li>
  <li>Obtém apenas uma mídia através de um id</li>
  <li>Atualize uma das mídias</li>
  <li>Remove uma mídia passando o id de uma das mídias</li>
  <li>Lista todas as mídias exceto aquela que foram excluídas - <b>(Passando o valor '0' que significado 'false' pra todos)</b></li>
</ol>


<h2>Configurações</h2>

Observação: Já tem um bucket criado com devidas configurações e podem ser usadas para teste.

<h3>Amazon S3</h3>

Informe o nome do bucket, URL no domain, accessKey e secretKey no arquivo application.properties <b>( package 'src/main/resources' )</b> e os passados para a criação do bucket e obtenção do access key/secret key pode ser feito através desse artigo <a href="https://medium.com/@shamnad.p.s/how-to-create-an-s3-bucket-and-aws-access-key-id-and-secret-access-key-for-accessing-it-5653b6e54337" target="_blank">aqui</a>.
```
aws.bucketName=your-bucket-name
aws.domain=s3-sa-east-1.amazonaws.com
aws.accessKey=your-accessKey
aws.secretKey=your-secretKey
```

<h3>Detalhes</h3>

<p>

O tamanho máximo do arquivo para ser feito upload é de até <b>50 MB</b> e pode ser alterado no arquivo <b>application.properties</b>
```
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
server.tomcat.max-http-post-size=500000000
server.tomcat.max-swallow-size=500000000
```
</p>

<p>

Atenta-se os dados de conexão do banco de dados que está sendo utilizado
```
spring.datasource.url=jdbc:mysql://{server}:{port}/{your-database}?useTimezone=true&serverTimezone=UTC
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```
</p>
	

<h2>Swagger</h2>

<ul>
	<li>http://<b>{server}</b>:<b>{port}</b>/swagger-ui.html</li>
	<li>http://<b>{server}</b>:<b>{port}</b>/v2/api-docs</li>
</ul>


<h2>Endpoints</h2>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias      <b>Method: POST</b>
<br /> <b>Descrição:</b> Adiciona mídia

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias/0      <b>Method: GET</b>
<br /> <b>Descrição:</b> Obtém apenas as mídias que não foram excluídas

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias/1      <b>Method: GET</b>
<br /> <b>Descrição:</b> Obtém todas as mídias inclusive aquelas que não foram excluídas

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias/media/<b>:id</b>      <b>Method: GET</b>
<br /> <b>Descrição:</b> Obtém apenas uma mídia do id que foi informado na URL

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias      <b>Method: PUT</b>
<br /> <b>Descrição:</b> Atualiza as informações da mídia

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias/media/<b>:id</b>      <b>Method: PUT</b>
<br /> <b>Descrição:</b> Remove logicamente uma mídia





