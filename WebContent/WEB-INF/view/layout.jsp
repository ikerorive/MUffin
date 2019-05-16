<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link rel="stylesheet" href="resources/css/stylesheet.css">
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
<body>

	<img src="resources/img/party2.jpg" class="bg" />

	<div align="right">
		<tiles:insertAttribute name="header" />
	</div>
	<div class="mainPage">
		<tiles:insertAttribute name="body" />
	</div>
	<div  class="bottom">
		<tiles:insertAttribute name="footer" />
	</div>

	<!--  <img src="resources/img/fun.png" class="fix" /> -->

</body>
</html>
