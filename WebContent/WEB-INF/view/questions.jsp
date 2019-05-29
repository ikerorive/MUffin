<div class="cards-container row d-flex justify-content-center m-2">
	<div class="card">
		<p class="card-title text-center">Questionquestionquestionquestion?</p>
		<div class="card-body text-center">
			<!-- Default inline 1-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input" id="defaultInline1"
					name="inlineDefaultRadiosExample" value="1"> <label
					class="custom-control-label" for="defaultInline1">1</label>
			</div>

			<!-- Default inline 2-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input" id="defaultInline2"
					name="inlineDefaultRadiosExample" value="2"> <label
					class="custom-control-label" for="defaultInline2">2</label>
			</div>

			<!-- Default inline 3-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input" id="defaultInline3"
					name="inlineDefaultRadiosExample" value="3"> <label
					class="custom-control-label" for="defaultInline3">3</label>
			</div>
			<!-- Default inline 4-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input" id="defaultInline4"
					name="inlineDefaultRadiosExample" value="4"> <label
					class="custom-control-label" for="defaultInline4">4</label>
			</div>
			<!-- Default inline 5-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input" id="defaultInline5"
					name="inlineDefaultRadiosExample" value="5"> <label
					class="custom-control-label" for="defaultInline5">5</label>

			</div>
		</div>

	</div>


	<div class="card">
		<p class="card-title text-center">Questionquestionquestionquestion?</p>
		<div class="card-body text-center">
			<!-- Default inline 1-->
			<div class="custom-control1 custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="1defaultInline1" name="1inlineDefaultRadiosExample" value="1">
				<label class="custom-control-label" for="1defaultInline1">1</label>
			</div>

			<!-- Default inline 2-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="1defaultInline2" name="1inlineDefaultRadiosExample" value="2">
				<label class="custom-control-label" for="1defaultInline2">2</label>
			</div>

			<!-- Default inline 3-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="1defaultInline3" name="1inlineDefaultRadiosExample" value="3">
				<label class="custom-control-label" for="1defaultInline3">3</label>
			</div>
			<!-- Default inline 4-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="1defaultInline4" name="1inlineDefaultRadiosExample" value="4">
				<label class="custom-control-label" for="1defaultInline4">4</label>
			</div>
			<!-- Default inline 5-->
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input"
					id="1defaultInline5" name="1inlineDefaultRadiosExample" value="5">
				<label class="custom-control-label" for="1defaultInline5">5</label>

			</div>
		</div>

	</div>
</div>




<form method="POST" enctype="multipart/form-data" action="uploadAction"
	class="text-center p-5 mask rgba-white-light">
	<button id="questionFinishBtn" class="btn purple-gradient"
		type="submit">Finish</button>

</form>

<script type="text/javascript">
	$(document).ready(function() {

		$("#questionFinishBtn").click(function() {

			var favorite = [];

			$.each($("input[type='radio']:checked"), function() {

				favorite.push($(this).val());

			});

			alert("Selected values" + favorite.join(", "));

		});

	});
</script>