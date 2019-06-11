<%@page import="java.util.*"%>
<%@page import="model.Evento"%>


<div class="row justify-content-center">

	<%
		List<Evento> list = (List<Evento>) session.getAttribute("eventos");

		for (Evento evt : list) {
	%>

	<div
		class="card-container py-2 
		<%out.println(evt.getName());%> anEvent">
		<p class="positionHidden" hidden>
			<%
				out.print(evt.getLatitude());
			%>,<%
				out.print(evt.getLongitude());
			%>
		</p>
		<div class="card">
			<div class="front">
				<div></div>
				<h1 class="eventTitle display-2">
					<%
						out.println(evt.getName());
					%>
				</h1>
				<img src='<%out.println(evt.getImgUrl());%>' alt="fondoEvento" class='w-100'>
			</div>
			<div class="back">
				<div class="card-body text-center">
					<h5 class="card-title">
						<%
							out.println((evt.getName()));
						%>
					</h5>
					<p class="card-text text ellipsis">
						<span class="text-concat"> <%
 	out.println(evt.getDescription());
 %>
						</span>
					</p>
					
					<form method="POST" enctype="multipart/form-data"
						action="selectEventSuccess">
						<input type="hidden" name="eventId"
							value="<%out.println(evt.getIdEvento());%>">
						<button class="btn btn-info purple-gradient" type="submit">See
							More!</button>
					</form>


				</div>
			</div>
		</div>
	</div>

	<%
		}
	%>

</div>
<br>
<br>

<script>
var longitude;
var latitude;
$(document).ready(function(){
		f();
	});

function f() {
	getLocation();
	
}
setInterval(f, 60000)

function getLocation() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(showPosition);
	} else {
		var txt = "Geolocation is not supported by this browser.";
		console.log(txt);

	}
}

function showPosition(position) {
	var txt = "Latitude: " + position.coords.latitude + "Longitude: "
			+ position.coords.longitude;
	console.log(txt);
	latitude= position.coords.latitude;
	longitude= position.coords.longitude;
	removeEvents(100);

}
function removeEvents(maxDistance){
	var miArray = $(".anEvent > .positionHidden").get();
	for (var i = 0; i < miArray.length; i+=1) {
		  var txt=(miArray[i].innerHTML);
		  var array = txt.split(",");
		  var distanceReal=distance(latitude, longitude, array[0],array[1]);
		  
		  if(maxDistance<distanceReal){
			 miArray[i].parentElement.style.display = "none";
		  }
		}
	
}

function distance(lat1,lon1,lat2,lon2) {
	var R = 6371; // km (change this constant to get miles)
	var dLat = (lat2-lat1) * Math.PI / 180;
	var dLon = (lon2-lon1) * Math.PI / 180;
	var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		Math.cos(lat1 * Math.PI / 180 ) * Math.cos(lat2 * Math.PI / 180 ) *
		Math.sin(dLon/2) * Math.sin(dLon/2);
	var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	var d = R * c;
	return Math.round(d);
}
</script>