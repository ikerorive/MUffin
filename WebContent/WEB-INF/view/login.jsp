<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="/MUffin/loginSuccess"
	method="post" modelAttribute="userCredential"
	class="text-center p-5">

	<p class="h4 mb-4">Sign in</p>

	<!-- Username -->
	<form:input path="username" type="text" id="defaultLoginFormName"
		class="form-control mb-4" placeholder="Username"/>

	<!-- Password -->
	<form:input path="password" type="password" id="defaultLoginFormPassword"
		class="form-control mb-4" placeholder="Password"/>

	<!-- Sign in button -->
	<button class="btn btn-info btn-block my-4 purple-gradient" type="submit">Sign
		in</button>

	<!-- Register -->
	<p>
		Not a member? <a href="/MUffin/register">Register</a>
	</p>

	<!-- Default form login -->
</form:form>


