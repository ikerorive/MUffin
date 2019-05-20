<form method="POST" enctype="multipart/form-data" action="uploadAction"
	class="text-center p-5 mask rgba-white-light">
	<p class="h4 mb-4">Change Avatar</p>
	<div class="input-group">
		<div class="input-group-prepend">
			<span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
		</div>
		<div class="custom-file">
			<input type="file" name="file" class="custom-file-input"
				id="inputGroupFile01" aria-describedby="inputGroupFileAddon01"
				accept=".jpg" required> <label class="custom-file-label"
				for="inputGroupFile01">Choose file</label>
		</div>
	</div>
	<button id="done-button"
		class="btn btn-info my-4 btn-block purple-gradient" type="submit">Upload</button>
</form>

<script type="text/javascript">
	$('#inputGroupFile01')
			.bind(
					'change',
					function() {
						if ((this.files[0].size / 1024) > 63) {
							alert('The file is too big, please select a image of less of 63KBs');
							$('#done-button').attr("disabled", true);
						}
						else
						{
							$('#done-button').attr("disabled", false);
						}

					});
</script>