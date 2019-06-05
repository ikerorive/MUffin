<%@page import="java.util.*"%>
<%@page import="model.Questions"%>

<div class="cards-container row d-flex justify-content-center m-2">
	<%
		// retrieve your list from the request, with casting 
		List<Questions> list = (List<Questions>) session.getAttribute("questions");

		for (Questions qts : list) {
	%>


	<div class="card">
		<p class="card-title text-center">
			<%
				out.print(qts.getQuestion());
			%>
		</p>
		<div class="card-body text-center">
			<!-- Default inline 1-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="<%out.print(qts.getIdQuestions());%>defaultInline1"
					name="<%out.print(qts.getQuestion());%>inlineDefaultRadiosExample"
					value="<%out.print(qts.getCategoriaPregunta());%>-1"> <label
					class="custom-control-label"
					for="<%out.print(qts.getIdQuestions());%>defaultInline1">1</label>
			</div>

			<!-- Default inline 2-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="<%out.print(qts.getIdQuestions());%>defaultInline2"
					name="<%out.print(qts.getQuestion());%>inlineDefaultRadiosExample"
					value="<%out.print(qts.getCategoriaPregunta());%>-2" >
				<label class="custom-control-label"
					for="<%out.print(qts.getIdQuestions());%>defaultInline2">2</label>
			</div>

			<!-- Default inline 3-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="<%out.print(qts.getIdQuestions());%>defaultInline3"
					name="<%out.print(qts.getQuestion());%>inlineDefaultRadiosExample"
					value="<%out.print(qts.getCategoriaPregunta());%>-3" checked> <label
					class="custom-control-label"
					for="<%out.print(qts.getIdQuestions());%>defaultInline3">3</label>
			</div>
			<!-- Default inline 4-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="<%out.print(qts.getIdQuestions());%>defaultInline4"
					name="<%out.print(qts.getQuestion());%>inlineDefaultRadiosExample"
					value="<%out.print(qts.getCategoriaPregunta());%>-4"> <label
					class="custom-control-label"
					for="<%out.print(qts.getIdQuestions());%>defaultInline4">4</label>
			</div>
			<!-- Default inline 5-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="<%out.print(qts.getIdQuestions());%>defaultInline5"
					name="<%out.print(qts.getQuestion());%>inlineDefaultRadiosExample"
					value="<%out.print(qts.getCategoriaPregunta());%>-5"> <label
					class="custom-control-label"
					for="<%out.print(qts.getIdQuestions());%>defaultInline5">5</label>

			</div>
		</div>

	</div>
	<%
		}
	%>
</div>




<form method="POST" enctype="multipart/form-data"
	action="formularioSuccess"
	class="text-center p-5 mask rgba-white-light">
	<input type="hidden" id="answers" name="answers"></input>
	<button id="questionFinishBtn" class="btn purple-gradient"
		type="submit" disabled>Finish</button>

</form>

<script type="text/javascript">
	$("input[type='radio']").bind("change click", function() {
		$("#questionFinishBtn").attr("disabled", false);
		var favorite = [];

		$.each($("input[type='radio']:checked"), function() {

			favorite.push($(this).val());

		});
		var txt = favorite.join(",");
		$("#answers").val(txt);
	});
</script>