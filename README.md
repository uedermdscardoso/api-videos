## DOCUMENTAÇÃO

<h2>Sumário</h2>
<ol>
  <li>Lembrete</li>
  <li>Passos</li>
  <li>Configurações</li>
  <li>Swagger</li>
  <li>Testes Unitários</li>
  <li>Endpoints</li>
</ol>

<h2>Lembrete</h2> 

<ul>
  <li>Para executar o projeto, clica com botão direito no seu projeto, clique em <b>Run As</b>, depois <b>Maven build...</b>, adicione <b>spring-boot:run</b> no <b>Goals</b> e clique em <b>Run</b></li>
  <li>Verifique se o seu banco de dados está rodando e está configurado corretamente no arquivo application.properties</li>
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

Observação: Para a criação do bucket e obtenção do access key/secret key, pode ser feito através desse artigo <a href="https://medium.com/@shamnad.p.s/how-to-create-an-s3-bucket-and-aws-access-key-id-and-secret-access-key-for-accessing-it-5653b6e54337">aqui</a>

Informe o nome do bucket, URL no domain, accessKey e secretKey no arquivo application.properties <b>( package 'src/main/resources' )</b>.
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


<h2>Testes Unitários</h2>

Observação: Atenta-se se contém o vídeo chamado <b>city-and-car-3d.mp4</b> no caminho <b>src/test/resources/dev/uedercardoso/apivideos</b>, verifique se uma mídia contrém o <b>id 15</b> para os testes de addMedia, updateMedia e removeMedia. E se existe <b>id 11</b> para o teste getUniqueMedia; 

Os testes unitários estão na pasta <b>src/test/java</b> e o vídeo de exemplo que será usado pelo teste unitário está nesse caminho <b>src/test/resources/dev/uedercardoso/apivideos</b>.

<h3>Execução</h3>
<ol>
  <li>Clicar botão direito do seu projeto</li>
  <li>Clique na opção 'Run As'</li> 
  <li>Clique na opção 'JUnit Test'</li>
</ol>

<h2>Endpoints</h2>

Observação: Precisa passar três campos: <b>name</b> (String), <b>duration</b> (Integer) e <b>video</b> (File) no body e no tipo form-data <b>(Usando postman)</b>.

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias      <b>Method: POST</b>
<br /> <b>Descrição:</b> Adicionar mídia

<hr />

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias/0      <b>Method: GET</b>
<br /> <b>Descrição:</b> Obtém apenas as mídias que não foram excluídas

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias/1      <b>Method: GET</b>
<br /> <b>Descrição:</b> Obtém todas as mídias inclusive aquelas que não foram excluídas

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias/media/<b>:id</b>      <b>Method: GET</b>
<br /> <b>Descrição:</b> Obtém apenas uma mídia do id que foi informado na URL

<hr />

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias      <b>Method: PUT</b>
<br /> <b>Descrição:</b> Atualiza as informações da mídia

O objeto abaixo é um exemplo de como deve ser enviado como <b>raw</b> para atualizar mídia <b>( com id )</b>
```
{
	"id": 13,
	"name": "Media 3 - Alterado",
	"url": "https://medias-teste-pratico.s3-sa-east-1.amazonaws.com/cd423148-c8d3-4fe8-94d0-27d3ddfb1cfc-media",
	"duration": 5,
	"date": "2020-10-08",
	"deleted": false
}
```

<hr />

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/medias/media/<b>:id</b>      <b>Method: PUT</b>
<br /> <b>Descrição:</b> Remove logicamente uma mídia





