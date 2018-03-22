<!-- Modal -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal fade" id="editcountry" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">NEW DATA</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="#">
					<input type="hidden" name="edit-id" id="edit-id"/>
					<div class="form-group">
						<label for="edit-nama">COUNTRY NAME</label> 
						<input type="text" class="form-control" id="edit-nama" aria-describedby="emailHelp" placeholder="Country Name" />
					</div>

					<div class="form-group">
						<label for="edit-reg">REGION</label>
						<select name="edit-reg" id="edit-reg">
							<c:forEach var="reg" items="${regs}">
								<option value="${reg.id}"> ${reg.name}</option>
							</c:forEach>
						</select>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button type="button" id="btn-update" class="btn btn-primary">UPDATE</button>
			</div>
		</div>
	</div>
</div>