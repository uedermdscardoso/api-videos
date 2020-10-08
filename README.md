## DOCUMENTAÇÃO

<h2>Sumário</h2>
<ul>
  <li>Passos</li>
  <li>Swagger</li>
  <li>Testes Unitários</li>
  <li>Endpoints</li>
</ul>

<h2>Passos</h2>

<ol>
  <li>Crie duas mídias - <b>(Use os vídeos disponibilizados na pasta '/videos' do repositório)</b></li>
  <li>Lista todas as mídias inclusive aquelas que foram excluídas - <b>(Passando o parâmetro como '1' que significa 'true' para todos)</b></li>
  <li>Obtém apenas uma mídia através de um id</li>
  <li>Atualize uma das mídias</li>
  <li>Remove uma mídia passando o id de uma das mídias</li>
  <li>Lista todas as mídias exceto aquela que foram excluídas - <b>Passando o valor '0' que significado 'false' pra todos</b></li>
</ol>


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





