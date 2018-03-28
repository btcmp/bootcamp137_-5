<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/WEB-INF/views/template/header.jsp"%>
<%
	Breadcrumb bc = new Breadcrumb((String) pageContext.getAttribute("baseUrl"),
			(String) request.getAttribute("title"));
	request.setAttribute("bc", bc);
	List<String> asset = new ArrayList();;
	asset.add("kategori"); //js
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>Category</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
				<div>
					<input type="text" id="cari" placeholder="Search" style="float: left;">
					<a style="margin-left: 10px;" id="btn-cari" class="btn btn-primary" href="#">Search</a>
					<a style="float: right; width: 15%; margin-bottom: 20px;" id="add" class="btn btn-info" href="#">Create</a>
				</div>
					<table id="kategori-list" class="table table-stripped table-bordered table-hover">
						<thead>
							<tr>
								<th style="width: 25%; text-align: center;">Category Name</th>
								<th style="width: 25%; text-align: center;">Item Stock</th>
								<th style="width: 5%; text-align: center;">#</th>
							</tr>
						</thead>
						<tbody style="text-align: center;">
							<c:forEach items= "${kats}" var="kats">
							 	<tr>
							 		<td>${kats.name}</td>
							 		<td>${kats.itemStock} item</td>
							 		<td>
							 			<!-- class untuk selektor -->
							 			 <a id="${kats.id}" class="editkategori" href="#" >view</a>
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
				<button style="float: left;" type="button" id="btn-cancel-save" class="btn btn-primary">CANCEL</button>
				<button style="float: right;" type="button" id="btn-save" class="btn btn-primary">SAVE</button>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="editkat" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Category</h5>
			</div>
			<div class="modal-body">
				<form action="#">
					<input type="hidden" name="edit-id" id="edit-id"/>
					
					<div class="form-group">
						<input type="text" class="form-control" id="edit-name-kat" aria-describedby="emailHelp" placeholder="Category Name" />
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button style="float: left;" type="button" id="btn-nonactive" class="btn btn-danger">X</button>
				<a href="${pageContext.request.contextPath}/kategori/index" class="btn btn-primary"> CANCEL</a>
				<button style="width: 15%;" type="button" id="btn-update" class="btn btn-primary">SAVE</button>
			</div>
		</div>
	</div>
</div>


<%@ include file="/WEB-INF/views/template/footer.jsp"%>