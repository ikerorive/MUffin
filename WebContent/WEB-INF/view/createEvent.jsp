<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" type="text/css"
	href="dist/bootstrap-clockpicker.min.css">
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css"
	integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA=="
	crossorigin="" />
<script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js"
	integrity="sha512-nMMmRyTVoLYqjP9hrbed9S+FzjZHW5gY1TWCHA5ckwXZBadntCNs8kEqAWdrb9O7rxbCaA4lKTIWjDXZxflOcA=="
	crossorigin=""></script>
<form:form action="/MUffin/createEventoSuccess" method="post"
	modelAttribute="evento" class="text-center p-5 mask rgba-white-light">

	<p class="h4 mb-4">Create Event</p>
	<!-- Username -->
	<form:input path="name" type="text" id="defaultRegisterFormFirstName"
		class="form-control  mb-4" placeholder="Name" />
	<form:input path="description" type="text"
		id="exampleFormControlTextarea2" class="form-control  mb-4"
		placeholder="Description" />
	<form:input path="imgUrl" type="text" id="exampleFormControlTextarea2"
		class="form-control  mb-4" placeholder="Image URL" />
	<form:input path="maxSize" type="number"
		id="exampleFormControlTextarea2" class="form-control  mb-4"
		placeholder="Maximum Size" />
	<form:input path="strDate" type="date" id="exampleFormControlTextarea2"
		class="form-control  mb-4" required="true" />
	<form:input path="strHour" type="time" id="exampleFormControlTextarea2"
		class="form-control  mb-4" required="true" />

	<div class="input-group mb-3">

		<input type="text" class="form-control lgtd" id="lng"
			placeholder="Longitude" aria-label="Example text with button addon"
			aria-describedby="button-addon1" name="longitude"> <input
			type="text" class="form-control" id="lat" placeholder="Latitude"
			aria-label="Example text with button addon"
			aria-describedby="button-addon1" name="latitude">
		<div class="input-group-prepend">
			<button
				class="btn btn-md purple-gradient m-0 px-3 py-2 z-depth-0 waves-effect"
				type="button" id="button-addon1" data-toggle="modal"
				data-target="#basicExampleModal">
				<i class="fas fa-map-marked-alt"></i>
			</button>
		</div>
	</div>

	<select name="eventType" class="browser-default custom-select mb-4">
		<option value="" disabled selected>Select Category</option>
		<option value="1">Deporte</option>
		<option value="2">Tecnologia</option>
		<option value="3">Moda</option>
		<option value="4">Cultura</option>
		<option value="5">Ocio</option>
		<option value="6">Gastronomia</option>
	</select>

	<!-- Sign up button -->
	<button class="btn btn-info my-4 btn-block purple-gradient"
		type="submit" value="Confirm">Sign up</button>

</form:form>

<div class="modal fade" id="basicExampleModal" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Select the event
					location</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div id="mapid" style="width: 450px; height: 400px;"></div>
			</div>
			<div class="modal-footer">
				<p class="lgtd"></p>
				<p class="lttd"></p>
			</div>
		</div>
	</div>
</div>



<script type="text/javascript" src="dist/bootstrap-clockpicker.min.js"></script>
<script type="text/javascript">
	$('.clockpicker').clockpicker();
</script>

<script>
	var mymap = L.map('mapid').setView([ 43.0623377, -2.5073983 ], 14);

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

	var popup = L.popup();

	function onMapClick(e) {
		popup.setLatLng(e.latlng).setContent(
				"You clicked the map at " + e.latlng.toString()).openOn(mymap);
		$('#lng').val(e.latlng.lng.toString());
		$('#lat').val(e.latlng.lat.toString());
		$('.lgtd ').text(e.latlng.lng.toString());
		$('.lttd').text(e.latlng.lat.toString());
	}

	mymap.on('click', onMapClick);
</script>

