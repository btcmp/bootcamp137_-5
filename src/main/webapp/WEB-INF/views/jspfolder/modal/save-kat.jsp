<!-- Modal -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal fade" id="savekat" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Category</h5>
			</div>
			<div class="modal-body">
				<form action="#">
					<div class="form-group">
						<input type="text" class="form-control" id="save-name" aria-describedby="emailHelp" placeholder="Category Name" />
					</div>

				</form>
			</div>
			<div class="modal-footer">
				<button type="button" id="btn-cancle" class="btn btn-primary">CANCLE</button>
				<button type="button" id="btn-save" class="btn btn-primary">SAVE</button>
			</div>
		</div>
	</div>
</div>