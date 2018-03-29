<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/WEB-INF/views/template/header.jsp"%>
<%
	Breadcrumb bc = new Breadcrumb((String) pageContext.getAttribute("baseUrl"),
			(String) request.getAttribute("title"));
	request.setAttribute("bc", bc);
	List<String> asset = new ArrayList();;
	asset.add("outlet"); //js
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>Outlet</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
				<div>
					<input type="text" id="cari" placeholder="Search" style="float: left;" />
					<a style="float: left; margin-left: 10px;" id="btn-cari" class="btn btn-primary">Search</a>
					<a style="float: right; width: 15%; margin-bottom: 20px;" id="add" class="btn btn-info" href="#">Create</a>
				</div>
					<table id="outlet-list" class="table table-stripped table-bordered table-hover">
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
							<c:forEach items= "${outlets}" var="otls">
							 	<tr>
							 		<td>${otls.name}</td>
							 		<td>${otls.address}</td>
							 		<td>${otls.phone}</td>
							 		<td>${otls.email}</td>
							 		<td>${otls.active}</td>
							 		<td>
							 			<!-- class untuk selektor -->
							 			 <a id="${otls.id}" class="editoutlet" href="#" >edit</a>
							 			 <%-- <a id="${otls.id}" class="id-nonactive btn btn-danger" href="#" >X</a> --%>
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

<div class="modal fade" id="saveout" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Input Outlet</h5>
			</div>
			<div class="modal-body">
				<form action="#">
					<div class="form-group">
						<input type="text" class="form-control" id="save-name" aria-describedby="emailHelp" placeholder="Outlet Name" />
					</div>
					
					<div class="form-group">
						<input type="text" class="form-control" id="save-address" aria-describedby="emailHelp" placeholder="Address" />
					</div>
					
					<div class="row form-group">
						<div class="col-sm-4">
							<select class="form-control" id="save-pro">
								<option value="">Provinsi</option>
								<c:forEach var="prov" items="${provinsi}">
									<option id="sprov" value="${prov.id}"> ${prov.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-4">
							<select class="form-control" name="save-reg" id="save-reg">
								<option value=""> Region</option>
							</select>
						</div>
						<div class="col-sm-4">
							<select class="form-control" name="save-dis" id="save-dis">
								<option value="">District</option>
							</select>
						</div>
					</div>
					
					<div class="row form-group">
						<div class="col-sm-4"><input type="text" class="form-control" id="save-code" placeholder="Postal Code" /></div>
						<div class="col-sm-4"><input type="text" class="form-control" id="save-phone" aria-describedby="emailHelp" placeholder="Phone" /></div>
						<div class="col-sm-4"><input type="text" class="form-control" id="save-email" aria-describedby="emailHelp" placeholder="Email" /></div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button style="float: left;" type="button" id="btn-cancel-save" class="btn btn-primary">CANCEL</button>
				<button style="float: right;" type="button" id="btn-save" class="btn btn-primary">SAVE</button>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="editout" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div style="width: 60%;" class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Outlet</h5>
			</div>
			<div class="modal-body">
				<form action="#">
					<input type="hidden" name="edit-id" id="edit-id"/>
					
					<div class="form-group">
						<input type="text" class="form-control" id="edit-name-out" aria-describedby="emailHelp" placeholder="Outlet Name" />
					</div>
					
					<div class="form-group">
						<input type="text" class="form-control" id="edit-address-out" aria-describedby="emailHelp" placeholder="Address" />
					</div>
					
					<div class="row form-group">
						<div class="col-sm-4">
							<select class="form-control" id="edit-prov-out">
								<option value="">Provinsi</option>
								<c:forEach var="prov" items="${provinsi}">
									<option value="${prov.id}"> ${prov.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-4">
							<select class="form-control" name="edit-reg-out" id="edit-reg-out">
								<option value=""> Region</option>
							</select>
						</div>
						<div class="col-sm-4">
							<select class="form-control"id="edit-dis-out">
								<option value="">District</option>
							</select>
						</div>
					</div>
					
					<div class="row form-group">
						<div class="col-sm-4"><input type="text" class="form-control" id="edit-code-out" placeholder="Postel Code" /></div>
						<div class="col-sm-4"><input type="text" class="form-control" id="edit-phone-out" aria-describedby="emailHelp" placeholder="Phone" /></div>
						<div class="col-sm-4"><input type="text" class="form-control" id="edit-email-out" aria-describedby="emailHelp" placeholder="Email" /></div>
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="${pageContext.request.contextPath}/outlet/index">CANCEL</a>
				<button style="float: left;" type="button" id="btn-nonactive" class="btn btn-danger">X</button>
				<button style="width: 15%;" type="button" id="btn-update" class="btn btn-primary">UPDATE</button>
			</div>
		</div>
	</div>
</div>


<%@ include file="/WEB-INF/views/template/footer.jsp"%>