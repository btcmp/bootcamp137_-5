<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/WEB-INF/views/template/header.jsp"%>
<%
	Breadcrumb bc = new Breadcrumb((String) pageContext.getAttribute("baseUrl"),
			(String) request.getAttribute("title"));
	request.setAttribute("bc", bc);
	List<String> asset = new ArrayList();;
	asset.add("supplier"); //js
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>Suppliers</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
				<div>
					<input type="text" id="carri" placeholder="Search" style="float: left;">
					<a style="margin-left: 10px;" id="btn-carri" class="btn btn-primary">Search</a>
					<a style="float: right; width: 15%; margin-bottom: 20px;" id="new" class="btn btn-info" href="#">Create</a>
				</div>
					<table id="supplier-list" class="table table-stripped table-bordered table-hover">
						<thead>
							<tr>
								<th style="width: 18%; text-align: center;">Name</th>
								<th style="width: 18%; text-align: center;">Address</th>
								<th style="width: 18%; text-align: center;">Phone</th>
								<th style="width: 18%; text-align: center;">Email</th>
								<th style="width: 18%; text-align: center;">Active</th>
								<th style="width: 10%; text-align: center;">#</th>
							</tr>
						</thead>
						<tbody style="text-align: center;">
							<c:forEach items= "${sups}" var="sups">
							 	<tr>
							 		<td>${sups.name}</td>
							 		<td>${sups.address}</td>
							 		<td>${sups.phone}</td>
							 		<td>${sups.email}</td>
							 		<td>${sups.active}</td>
							 		<td>
							 			<!-- class untuk selektor -->
							 			 <a id="${sups.id}" class="editsupplier" href="#" >edit</a>
							 			 <%-- <a id="${sups.id}" class="id-nonactive btn btn-danger" href="#" >X</a> --%>
							 		</td>
							 	</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</section>

<div class="modal fade" id="savesup" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Supplier Detail</h5>
			</div>
			<div class="modal-body">
				<form id="form-save" action="#">
					<div class="form-group">
						<input required data-parsley-length= "[4,50]" type="text" class="form-control" id="save-name-sup" aria-describedby="emailHelp" placeholder="Supplier Name" />
					</div>
					
					<div class="form-group">
						<input data-parsley-minlength= "4" type="text" class="form-control" id="save-address-sup" aria-describedby="emailHelp" placeholder="Address" />
					</div>
					
					<div class="row form-group">
						<div class="col-sm-4">
							<select class="form-control" id="save-pro-sup" required>
								<option value="">Provinsi</option>
								<c:forEach var="prov" items="${provs}">
									<option id="save-prov" value="${prov.id}"> ${prov.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-4">
							<select class="form-control" name="save-reg" id="save-reg-sup" required>
								<option value=""> Region</option>
							</select>
						</div>
						<div class="col-sm-4">
							<select class="form-control" name="save-dis" id="save-dis-sup" required>
								<option value="">District</option>
							</select>
						</div>
					</div>
					
					<div class="row form-group">
						<div class="col-sm-4"><input type="text" class="form-control" id="save-code-sup" placeholder="Postal Code" /></div>
						<div data-parsley-length="[4,16]" class="col-sm-4"><input type="text" class="form-control" id="save-phone-sup" aria-describedby="emailHelp" placeholder="Phone" /></div>
						<div data-parsley-required="true" class="col-sm-4 datavalid"><input type="email" class="form-control" id="save-email-sup" aria-describedby="emailHelp" placeholder="Email" /></div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="${pageContext.request.contextPath}/supplier/index">CANCEL</a>
				<button style="float: right;" type="button" id="btn-save-sup" class="btn btn-primary">SAVE</button>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="editsup" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div style="width: 60%;" class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Supplier</h5>
			</div>
			<div class="modal-body">
				<form id="form-edit" action="#">
					<input type="hidden" name="edit-id" id="edit-id-sup"/>
					
					<div class="form-group">
						<input required data-parsley-length="[4,50]" type="text" class="form-control" id="edit-name-sup" aria-describedby="emailHelp" placeholder="Outlet Name" />
					</div>
					
					<div class="form-group">
						<input data-parsley-minlength="4" type="text" class="form-control" id="edit-address-sup" aria-describedby="emailHelp" placeholder="Address" />
					</div>
					
					<div class="row form-group">
						<div class="col-sm-4">
							<select class="form-control" id="edit-prov-sup" required>
								<option value="">Provinsi</option>
								<c:forEach var="prov" items="${provs}">
									<option value="${prov.id}"> ${prov.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-4">
							<select class="form-control" id="edit-reg-sup" required>
								<option value=""> Region</option>
							</select>
						</div>
						<div class="col-sm-4">
							<select class="form-control"id="edit-dis-sup" required>
								<option value="">District</option>
							</select>
						</div>
					</div>
					
					<div class="row form-group">
						<div class="col-sm-4"><input type="text" class="form-control" id="edit-code-sup" placeholder="Postel Code" /></div>
						<div data-parsley-length="[4,16]" class="col-sm-4"><input type="text" class="form-control" id="edit-phone-sup" aria-describedby="emailHelp" placeholder="Phone" /></div>
						<div data-parsley-required="true" class="col-sm-4 datavalid"><input type="email" class="form-control" id="edit-email-sup" aria-describedby="emailHelp" placeholder="Email" /></div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="${pageContext.request.contextPath}/supplier/index">CANCEL</a>
				<button style="float: left;" type="button" id="btn-nonactive" class="btn btn-danger">X</button>
				<button style="width: 15%;" type="button" id="btn-update-sup" class="btn btn-primary">UPDATE</button>
			</div>
		</div>
	</div>
</div>


<%@ include file="/WEB-INF/views/template/footer.jsp"%>