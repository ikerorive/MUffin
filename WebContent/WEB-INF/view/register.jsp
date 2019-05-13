<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="/MUffin/registerSuccess" method="post"
	modelAttribute="user" class="text-center p-5">

	<p class="h4 mb-4">Register</p>
	<!-- Username -->
	<form:input path="username" type="text"
		id="defaultRegisterFormFirstName" class="form-control  mb-4"
		placeholder="Username" />
	<!-- Password -->
	<form:input path="password" type="password"
		id="defaultRegisterFormPassword" class="form-control  mb-4"
		placeholder="Password" />
	<form:input path="email" type="email" id="defaultRegisterFormEmail"
		class="form-control  mb-4" placeholder="Email" />
	<select name="tipoUsuario" class="browser-default custom-select mb-4">
		<option value="" disabled selected>Select Role</option>
		<option value="1">Customer</option>
		<option value="2">Business</option>
	</select>
	<!-- Sign up button -->
	<button class="btn btn-info my-4 btn-block purple-gradient"
		type="submit" value="Confirm">Sign up</button>

</form:form>