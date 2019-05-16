
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.*"%>


<%
	String b64 = "";

	try {
		BufferedImage bImage = (BufferedImage) session.getAttribute("avatar");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bImage, "jpg", baos);
		baos.flush();
		byte[] imageInByteArray = baos.toByteArray();
		baos.close();
		b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		System.out.println("Caught an IllegalArgumentException..." + e.getMessage());
	}
%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-light white scrolling-navbar">
	<div class="container">

		<a href="/MUffin"><img src="resources/img/logoBlack.png"
			height="36" width="36" alt="MUffin logo"></a>



		<!-- Collapse -->
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<!-- Links -->
		<div class="collapse navbar-collapse" id="navbarSupportedContent">

			<!-- Left -->
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="/MUffin/login">Login</a></li>

				<li class="nav-item"><a class="nav-link"
					href="/MUffin/register">Register</a></li>
			</ul>

			<!-- Right -->
			<ul class="navbar-nav nav-flex-icons">
				<%
					if (session.getAttribute("user") != null) {
				%>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" id="navbarDropdownMenuLink"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img
						id="avatar" src="data:image/jpg;base64, <%=b64%>" alt="avatar"
						height="36" width="36" /></a>
					<div class="dropdown-menu dropdown-primary"
						aria-labelledby="navbarDropdownMenuLink">
						<a class="dropdown-item"
							href="/SpringMVCFormValidationPruebas/logOff">Log Off</a>
					</div></li>
				<%
					}
				%>
			</ul>

		</div>

	</div>
</nav>
<!-- Navbar -->