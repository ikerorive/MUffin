<%@page import="java.util.*"%>
<%@page import="model.Evento"%>

<div class="cards-container row d-flex justify-content-center">



	<%
		// retrieve your list from the request, with casting 
		List<Evento> list = (List<Evento>) session.getAttribute("eventos");

		for (Evento evt : list) {
	%>

	<script>
		console.log("prueba");
	</script>
	<!--   First Card -->
	<div
		class="card-container py-2
		<%out.println(evt.getName());%>">
		<div class="card">
			<div class="front">
				<h1 class="eventTitle display-2">
					<%
						out.println(evt.getName());
					%>
				</h1>
				<img src='<%out.println(evt.getImgUrl());%>' class='w-100'>
			</div>
			<div class="back">
				<div class="card-body text-center">
					<h5 class="card-title">
						<%
							out.println((evt.getDate()));
						%>
					</h5>
					<p class="card-text">
						<%
							out.println(evt.getDescription());
						%>
					</p>
					<p>
						<i class="fas fa-user-check"></i> 22/<%
							out.println(evt.getMaxSize());
						%>
					</p>
					<a href="#" class="btn btn-primary purple-gradient">I'm in!</a>


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
	function distance(lat1, lon1, lat2, lon2, unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		} else {
			var radlat1 = Math.PI * lat1 / 180;
			var radlat2 = Math.PI * lat2 / 180;
			var theta = lon1 - lon2;
			var radtheta = Math.PI * theta / 180;
			var dist = Math.sin(radlat1) * Math.sin(radlat2)
					+ Math.cos(radlat1) * Math.cos(radlat2)
					* Math.cos(radtheta);
			if (dist > 1) {
				dist = 1;
			}
			dist = Math.acos(dist);
			dist = dist * 180 / Math.PI;
			dist = dist * 60 * 1.1515;
			if (unit == "K") {
				dist = dist * 1.609344
			}
			if (unit == "N") {
				dist = dist * 0.8684
			}
			return dist;
		}
	}
</script>