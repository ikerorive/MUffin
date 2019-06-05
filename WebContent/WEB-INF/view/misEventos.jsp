<%@page import="java.util.*"%>
<%@page import="model.Evento"%>


<div class="row justify-content-center">

	<%
		// retrieve your list from the request, with casting 
		List<Evento> list = (List<Evento>) session.getAttribute("eventos");

		for (Evento evt : list) {
	%>

	<div
		class="card-container py-2 
		<%out.println(evt.getName());%> anEvent">
		<div class="card">
			<div class="front">
				<div></div>
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
							out.println((evt.getName()));
						%>
					</h5>
					<p class="card-text text ellipsis">
						<span class="text-concat"> <%
 	out.println(evt.getDescription());
 %>
						</span>
					</p>
					<p>
						<i class="fas fa-user-check"></i> 22/<%
							out.println(evt.getMaxSize());
						%>
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
