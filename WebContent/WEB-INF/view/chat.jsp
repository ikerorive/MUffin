<%@page import="java.util.*"%>
<%@page import="model.Evento"%>
<%@page import="model.User"%>
<%
	// retrieve your list from the request, with casting 
	Evento evt = (Evento) session.getAttribute("selectedEvent");
	User user = (User) session.getAttribute("user");
%>

<input type="hidden" id="userId"
	value="<%out.print(user.getIdUser());%>" />
<input type="hidden" id="username"
	value="<%out.print(user.getUsername());%>" />
<input type="hidden" id="eventoId"
	value="<%out.print(evt.getIdEvento());%>" />
<img src="resources/img/party2.jpg" class="bgChat" />
<div class="d-flex justify-content-around py-0 chatTop">
	<a href="/MUffin/eventoInfo" class=" btn purple-gradient"> <i
		class="fas fa-info"></i>
	</a> <a href="/MUffin/suscribeToEvent" class="btn purple-gradient"
		id="suscribeButton">Subscribe</a>
</div>
<div class="whiteBg"></div>
<br>
<br>
<!-- ------------------------------------------------------------------------ -->

<div class="d-flex justify-content-center">
	<div class="chat-message ">
		<ul class="chat">
			<li class="right clearfix">
				<div class="chat-body clearfix">
					<div class="header">
						<strong class="primary-font">MUffin Team</strong> <small
							class="float-right text-muted"><i class="far fa-clock"></i>
						</small>
					</div>
					<p>Se debe observar respeto y buenas maneras a la hora de
						expresarse. No se pueden publicar mensajes que puedan ofender a
						otros usuarios del foro o tengan contenido racista o sexista.</p>
				</div>
			</li>

		</ul>
	</div>
</div>

<!-- ------------------------------------------------------------------------ -->
<br>
<br>
<br>
<br>
<br>
<div class="writeMsg">
	<!-- Footer -->


	<!-- Footer Elements -->
	<div class="container">


		<!--Grid column-->
		<div>

			<form class="input-group">
				<input id="messageField" type="text"
					class="form-control form-control-sm" placeholder="Your message"
					aria-describedby="basic-addon2">
				<div class="input-group-append">
					<button id="txtEnviar" class="btn btn-sm purple-gradient my-0"
						type="button">
						<i class="far fa-paper-plane"></i>
					</button>
				</div>
			</form>

		</div>
		<!--Grid column-->

	</div>
	<!--Grid row-->
</div>

<script>
	//http://localhost:8080/Muffin.v.2/webresources/generic/crearEvento?oper1=5&oper2=6
	window.setInterval(function() {
		/// call your function here

		actualizarMsg();
	}, 5000);
	$(document).ready(whenReady());

	function whenReady() {
		console.log("IS READY");
		actualizarMsg();
		var userid = $("#userId").val();

		var eventoid = $("#eventoId").val();

		fetch(
				"http://localhost:8080/Muffin.v.2/webresources/generic/getInteres?idUser="
						+ userid + "&evento=" + eventoid, {

				}).then(function(response) {
			response.text().then(function(text) {
				//$(".mezua").remove();
				
				if(text=='1'){
					$("#suscribeButton").attr("disabled", true);
					console.log("no suscrito " + text);
					
				}else{
					$("#txtEnviar").attr("disabled", true);
					console.log("suscrito " + text);
				}
				

			});
			//appendMyMessage();
		});
	}

	function actualizarMsg() {

		var username = $("#username").val();

		var eventoid = $("#eventoId").val();

		fetch(
				"http://localhost:8080/Muffin.v.2/webresources/chat/getMensajeInicio?chatId="
						+ eventoid, {

				})
				.then(
						function(response) {
							response
									.json()
									.then(
											function(json) {
												$(".mezua").remove();
												var str = JSON.stringify(json,
														null, 2);
												var obj = JSON.parse(str);
												var array = (obj.array);
												array
														.forEach(function(
																valor, indice,
																array) {
															//console.log("En el índice " + indice + " hay este valor: " + valor);
															var array1 = valor
																	.split(";");

															var html;
															if (username == array1[0]) {

																html = '<li class="right clearfix mezua">';
																//lado="right";
															} else {

																html = '<li class="left clearfix mezua">';
																//lador="left";
															}

															html += '<div class="chat-body clearfix">';
															html += '<div class="header">';
															html += '<strong class="primary-font">'
																	+ array1[0]
																	+ '</strong> <small'   
																html +='class="float-right text-muted"><i class="far fa-clock"></i>';
															html += ''
																	+ array1[2]
																	+ '</small>';
															html += '</div>';
															html += '<p>'
																	+ array1[1]
																	+ '</p>';
															html += '</div>';
															$(".chat").append(
																	html);
															//appendMyMessage();

														});
												//appendMyMessage();
											});
						})

	}

	$("#txtEnviar")
			.click(
					function() {

						var msg = $("#messageField").val();
						var username = $("#username").val();
						console.log("USERNAME " + username);
						var userid = $("#userId").val();
						console.log("USERID " + userid);
						var eventoid = $("#eventoId").val();
						console.log(msg + username + userid + eventoid);
						fetch(
								"http://localhost:8080/Muffin.v.2/webresources/chat/enviarMensaje?userId="
										+ userid + "&username=" + username
										+ "&mensaje=" + msg + "&chatId="
										+ eventoid, {

								})
								.then(
										function(response) {
											response
													.json()
													.then(
															function(json) {
																$(".mezua")
																		.remove();
																var str = JSON
																		.stringify(
																				json,
																				null,
																				2);
																var obj = JSON
																		.parse(str);
																var array = (obj.array);
																array
																		.forEach(function(
																				valor,
																				indice,
																				array) {
																			//console.log("En el índice " + indice + " hay este valor: " + valor);
																			var array1 = valor
																					.split(";");

																			var html;
																			if (username == array1[0]) {

																				html = '<li class="right clearfix mezua">';
																				//lado="right";
																			} else {

																				html = '<li class="left clearfix mezua">';
																				//lador="left";
																			}

																			html += '<div class="chat-body clearfix">';
																			html += '<div class="header">';
																			html += '<strong class="primary-font">'
																					+ array1[0]
																					+ '</strong> <small'   
																			html +='class="float-right text-muted"><i class="far fa-clock"></i>';
																			html += ''
																					+ array1[2]
																					+ '</small>';
																			html += '</div>';
																			html += '<p>'
																					+ array1[1]
																					+ '</p>';
																			html += '</div>';
																			$(
																					".chat")
																					.append(
																							html);
																			//appendMyMessage();

																		});
																appendMyMessage();
															});
										})

					});

	function appendMyMessage() {

		var html;

		html = '<li class="right clearfix mezua">';

		html += '<div class="chat-body clearfix">';
		html += '<div class="header">';
		html += '<strong class="primary-font"> ' + $("#username").val();
		+' </strong>';
		html += '</div>';
		html += '<p>' + $("#messageField").val();
		+'</p>';
		html += '</div>';
		$(".chat").append(html);
		$("#messageField").val("");

	}
</script>
