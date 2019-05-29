<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link rel="stylesheet" type="text/css"
	href="//fonts.googleapis.com/css?family=Pacifico" />
<link rel="stylesheet" href="resources/css/stylesheet.css">
<link rel="stylesheet" href="resources/css/chat.css">
<link rel="stylesheet" href="resources/css/animStylesheet.css">
<link rel="stylesheet" href="resources/css/cardStylesheet.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.1/css/all.css">
<!-- Bootstrap core CSS -->
<!--  <link href="resources/MDB-Free_4.6.0/css/bootstrap.min.css"
	rel="stylesheet">-->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<!-- Material Design Bootstrap -->
<link href="resources/MDB-Free_4.6.0/css/mdb.min.css" rel="stylesheet">
<!-- JQuery -->
<script src="resources/MDB-Free_4.6.0/js/jquery-3.3.1.min.js"></script>
<!-- Bootstrap tooltips -->
<script src="resources/MDB-Free_4.6.0/js/popper.min.js"></script>
<!-- Bootstrap core JavaScript -->
<script src="resources/MDB-Free_4.6.0/js/bootstrap.min.js"></script>
<!-- MDB core JavaScript -->
<script src="resources/MDB-Free_4.6.0/js/mdb.min.js"></script>
</head>
<body onload="getLocation()">

	<img src="resources/img/party2.jpg" class="bg" />

	<div align="right">
		<tiles:insertAttribute name="header" />
	</div>
	<br><br><br><br>
	<div class="mainPage">
		<tiles:insertAttribute name="body" />
	</div>
	<div class="bottom">
		<tiles:insertAttribute name="footer" />
	</div>

	<!--  <img src="resources/img/fun.png" class="fix" /> -->

</body>
</html>

<script>
	var longitude;
	var latitude;
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
		var txt = "Latitude: " + position.coords.latitude + "<br>Longitude: "
				+ position.coords.longitude;
		console.log(txt);
		latitude= position.coords.latitude;
		longitude= position.coords.longitude;

	}
</script>