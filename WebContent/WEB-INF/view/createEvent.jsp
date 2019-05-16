<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="/MUffin/createEventoSuccess" method="post"
	modelAttribute="evento" class="text-center p-5 mask rgba-white-light">

	<p class="h4 mb-4">Create Event</p>
	<!-- Username -->
	<form:input path="name" type="text" id="defaultRegisterFormFirstName"
		class="form-control  mb-4" placeholder="Name" />
	<form:input path="description" type="text"
		id="exampleFormControlTextarea2" class="form-control  mb-4"
		placeholder="Description" />
	<!-- Sign up button -->
	<button class="btn btn-info my-4 btn-block purple-gradient"
		type="submit" value="Confirm">Sign up</button>

</form:form>