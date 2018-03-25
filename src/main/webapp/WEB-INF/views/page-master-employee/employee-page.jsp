<%@page import="com.miniproject.pos.model.ItemVariant"%>
<%@page import="com.miniproject.pos.model.Items"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/WEB-INF/views/template/header.jsp"%>
<%
	Breadcrumb bc = new Breadcrumb((String) pageContext.getAttribute("baseUrl"),
			(String) request.getAttribute("title"));
	request.setAttribute("bc", bc);
	List<String> asset = new ArrayList();
	asset.add("employee");
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>Employee Page</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<!-- START OF THE CONTENT -->
					<div class="container-fluid">
						<form class="form-room" >
							<div>
								<div class="row form-group">
									<div class="col-sm-4"><input type="text" class="form-control cancelable" id="save-fname" placeholder="first name"></div>
									<div class="col-sm-4"><input type="text" class="form-control cancelable" id="save-lname" placeholder="last name"></div>
									<div class="col-sm-4"><input type="text" class="form-control cancelable" id="save-email" placeholder="email"></div>
								</div>
								<div class="row form-group">
									<div class="col-sm-4">
										<label for="save-title">Title</label>
										<select id="save-title">
											<option value="Mr. ">Mr.</option>
											<option value="Mrs. ">Mrs.</option>
										</select>
									</div>
									<div class="col-sm-4"><button type="button" class="btn btn-primary" id="btn-assign-outlet">Assign Outlet</button></div>
									<div class="col-sm-2 checkbox"><input type="checkbox" id="save-hasAccount">Create Account</div>
								</div>
							</div>
							<hr>
							<div class="row form-group" id="user-form">
								<div class="col-sm-2">
									<label for="save-role">Role</label>
									<select id="save-role">
										<c:forEach items="${listRole}" var="role">
											<option value="${role.id}">${role.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-4 "><input type="text" class="form-control cancelable" id="save-username" placeholder="username"></div>
								<div class="col-sm-4 "><input type="text" class="form-control cancelable" id="save-password" placeholder="password"></div>
							</div>
							<div class="row form-group" align="right">
								<button type="button" class="btn btn-primary" id="btn-cancel">Cancel</button>
								<button type="button" class="btn btn-primary" id="btn-save">Save</button>
							</div>
						</form>
					</div>
					
					<div>
						<h3>Staff List</h3>
						<table id="employee-table" class="table">
							<thead>
								<tr>
									<th>Name</th>
									<th>Email</th>
									<th>Have Account</th>
									<th>Outlet Access</th>
									<th>Role</th>
									<th>#</th>
								</tr>
							</thead>
							
							<tbody>				
								<c:forEach items="${listActiveEmployee}" var="employee">
								<tr>
									<td>${employee.firstName} ${employee.lastName}</td>
									<td>${employee.email}</td>
									<td><input type="checkbox" <c:if test="${employee.haveAccount == true}">checked</c:if> disabled></td>
									<td></td>
									<td>${employee.user.role.name}</td>
									<td>
										<button type="button" class="edit btn btn-secondary" id="${employee.id}">Edit</button>
										<button type="button" class="deactivate btn btn-secondary" id="${employee.id}">Deactivate</button>
									</td>
								</tr>
								</c:forEach>
							</tbody>
							
						</table>
					</div>
					<!-- END OF THE CONTENT -->
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</section>

	<!-- modal assign outlet for employee -->
	<div class="modal fade" id="modal-outlet" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title">Assign Outlet to Employee</h2>
				</div>
				<div class="modal-body">
					<form class="form-room">
						<table class="table" id="outlet-table">
							<tbody id="list-outlet">
								<c:forEach items="${listOutlet}" var="outlet">
								<tr>
									<td>${outlet.name}</td>
									<td><input type="checkbox" class="selected-outlet" id="${outlet.id}"></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn-select-outlet" class="btn btn-primary">Select</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- modal deactivate employee -->
	<div class="modal fade" id="modal-deactivate" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title">Deactivate Employee</h2>
				</div>
				<div class="modal-body">
					<p id="selected-emp">Are You Sure..?</p>
				</div>
				<div class="modal-footer">
					<button type="button" id="btn-cancel-deactivate" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<button type="button" id="btn-exec-deactivate" class="btn btn-primary">Deactivate</button>
				</div>
			</div>
		</div>
	</div>

<%@ include file="/WEB-INF/views/template/footer.jsp"%>