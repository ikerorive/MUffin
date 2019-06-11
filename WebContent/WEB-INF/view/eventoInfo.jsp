<%@page import="java.util.*"%>
<%@page import="model.Evento"%>
<%

	Evento evt = (Evento) session.getAttribute("selectedEvent");
%>
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css"
	integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA=="
	crossorigin="" />
<script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js"
	integrity="sha512-nMMmRyTVoLYqjP9hrbed9S+FzjZHW5gY1TWCHA5ckwXZBadntCNs8kEqAWdrb9O7rxbCaA4lKTIWjDXZxflOcA=="
	crossorigin=""></script>

<div class="modal-body row">
	<div class="col-md-6 mask m-2 rgba-white-strong h-100 evInfo">
		<h1>
			<%
				out.println(evt.getName());
			%>
		</h1>
		<small class="text-muted"><em class="far fa-clock"></em> <%
 	out.println(evt.getDate());
 %></small>
		<p>
			<%
				out.println(evt.getDescription());
			%>
		</p>

	</div>
	<div class="col-md-5">
		<div>
			<a data-toggle="modal" data-target="#userModal"> <span
				id="asistentes"></span>/<%
 	out.println(evt.getMaxSize());
 %> <em class="fas fa-users"></em>
			</a>
		</div>
		<br>
		<div id="mapid" style="width: 100%; height: 400px;"></div>

		<script>
			var mymap = L.map('mapid').setView(
					[
		<%out.print(evt.getLatitude());%>
			,
		<%out.print(evt.getLongitude());%>
			], 14);

			L
					.tileLayer(
							'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw',
							{
								maxZoom : 18,
								attribution : 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, '
										+ '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, '
										+ 'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
								id : 'mapbox.streets'
							}).addTo(mymap);

			L.marker([ 43.066505, -2.5015354 ]).addTo(mymap).bindPopup(
					"<b>Event</b><br />Event Position");

			mymap.on('click', onMapClick);
		</script>

	</div>
</div>

<div class="modal fade" id="userModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-scrollable" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Attendants</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<!-- Personas suscritas	-->

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>

<script>
	var respuesta;
	$(document)
			.ready(
					function() {
							fetch(
									"http://localhost:8080/Muffin.v.2/webresources/generic/getCantidadEvento?evento="+<%out.print(evt.getIdEvento());%>,
									{

									}).then(function(response) {
								response.text().then(function(text) {
									$("#asistentes").text(text);
									console.log("RESPUESTA----> " + text);

								});
						})

					});
</script>

