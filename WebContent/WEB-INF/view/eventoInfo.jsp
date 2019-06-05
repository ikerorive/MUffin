<%@page import="java.util.*"%>
<%@page import="model.Evento"%>
<%
	// retrieve your list from the request, with casting 
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
		<small class="text-muted"><i class="far fa-clock"></i> <%
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
			<a data-toggle="modal" data-target="#userModal"> 15/20 <i
				class="fas fa-users"></i>
			</a>
			<div class="progress">
				<div class="progress-bar" role="progressbar" style="width: 75%"
					aria-valuenow="15" aria-valuemin="0" aria-valuemax="20"></div>
			</div>
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
										+ 'Imagery � <a href="https://www.mapbox.com/">Mapbox</a>',
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
				<!--  	<p>peio23</p>
				<p>mikel_mkl</p>
				<p>anderp2</p>
				<p>aitor33</p>
				<p>jonM</p>
				<p>ikerw</p>
				<p>wunguyungu</p>
				<p>thelegend27</p>
				<p>marro4</p>
				<p>borj3nzz</p>
				<p>qwer</p>
				<p>wakaso</p>
				<p>donMubarak</p>
				<p>erBixo</p>
				<p>cabesa</p>
				-->

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
								"http://localhost:8080/cambiarEventosServer/webresources/generic/uno",
								{

								}).then(function(response) {
							response.text().then(function(text) {
								console.log("RESPUESTA----> " + text);

							});
						})

					});
</script>

