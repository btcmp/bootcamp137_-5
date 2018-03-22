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
										<option value="admin">Admin</option>
										<option value="backOffice">Back Office</option>
										<option value="cashier">Cashier</option>
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
								<tr>
									<td>Rifky</td>
									<td>Rifky@gmail</td>
									<td><input type="checkbox" checked disabled></td>
									<td>Outlet 1, Outlet 2</td>
									<td>Back Office</td>
									<td>
										<button type="button">Edit</button>
										<button type="button">Delete</button>
									</td>
								</tr>
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
					<h5 class="modal-title">Assign Outlet to Employee</h5>
				</div>
				<div class="modal-body">
					<form class="form-room">
						<table class="table" id="outlet-table">
							<tbody>
								<tr>
									<td>Outlet 1</td>
									<td><input type="checkbox" id="outlet 1"></td>
								</tr>
								<tr>
									<td>Outlet 2</td>
									<td><input type="checkbox" id="outlet 2"></td>
								</tr>
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

<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModal-title">Create New Items</h4>
			</div>
			<div class="modal-body">
				<div class="callout callout-warning hidden">
					<h4>Oh snap!</h4>
					<p>This form seems to be invalid :(</p>
				</div>
				<form id="form-items" action="#" method="post"
					data-parsley-validate="">
					<div class="col-md-4">
						<img alt="" src="" style="width:100%;height:100%"/>
					</div>
					<div class="col-md-8">
						<div class="form-group">
							<input name="items-name" type="text" placeholder="Item Name"
								data-parsley-required="true" class="form-control" id="items-name"> <input
								name="items-id" type="hidden" class="form-control" id="items-id">
						</div>
						<div class="form-group">
							<select id="items-category-id" class="form-control" data-parsley-required="true">
							<option value="">Category</option>
							<c:forEach items="${category}" var="jrs">
								<option value="${jrs.id }">${jrs.name }</option>
							</c:forEach>
							</select>
						</div>
					</div>
					<h3>Variant</h3><button type="button" class="btn btn-primary" id="tambah-variant">Add Variant</button>
					<table class="table table-bordered table-stripped" id="list-variant">
						<thead>
							<tr>
								<th>Variant Name</th>
								<th>Unit Price</th>
								<th>SKU</th>
								<th>Begining Stock</th>
								<th>#</th>
							</tr>
						</thead>
						<tbody id="list-variant-body">
						
						</tbody>
					</table>
					<button class="btn btn-primary" id="tambah-data">Back</button>
					<button class="btn btn-primary" id="tambah-data">Cancel</button>
					<button class="btn btn-primary" id="tambah-data">Save</button>
				</form>
			</div>
		</div>
	</div>
</div>

<div id="modal-variant" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModal-title">Create New Variant</h4>
			</div>
			<div class="modal-body">
				<div id ="warning-variant" class="callout callout-warning hidden">
					<h4>Oh snap!</h4>
					<p>This form seems to be invalid :(</p>
				</div>
				<form id="form-variant" action="#" method="post"
					data-parsley-validate="">
					
					<div class="row">
						<div class="col-md-4">
							<input name="variant-name" type="text" placeholder="Variant Name"
								data-parsley-required="true" class="form-control" id="variant-name"> 
							<input name="variant-id" type="hidden" class="form-control" id="variant-id">
						</div>
						<div class="col-md-4">
							<input name="variant-unit-price" type="text" placeholder="Unit Price"
								data-parsley-required="true" class="form-control" id="variant-unit-price">
						</div>
						<div class="col-md-4">
							<input name="variant-sku" type="text" placeholder="SKU"
								data-parsley-required="true" class="form-control" id="variant-sku">
						</div>
					</div>
		
					<h3>Set Begining Stock</h3>
					<div class="row">
						<div class="col-md-6">
					<input name="variant-begining-qty" type="text" placeholder="Begining Stock"
								data-parsley-required="true" class="form-control" id="variant-begining-stock">
						</div>
						<div class="col-md-6">
					<input name="variant-alert-at" type="text" placeholder="Alert At"
								data-parsley-required="true" class="form-control" id="variant-alert-at">
					</div>
					</div>
					<button class="btn btn-primary" id="tambah-data">Back</button>
					<button class="btn btn-primary" id="tambah-data">Cancel</button>
					<button class="btn btn-primary" id="tambah-data">Save</button>
				</form>
			</div>
		</div>
	</div>
</div>

<div id="modal-detail" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Detail Data Barang</h4>
			</div>
			<div class="modal-body">
				<table class="table table-stripped table-hover">
					<tr>
						<td>ID</td>
						<td> : </td>
						<td id="barang-detail-id"></td>
					</tr>
					<tr>
						<td>Kode Barang</td>
						<td> : </td>
						<td id="barang-detail-kode"></td>
					</tr>
					<tr>
						<td>Nama Barang</td>
						<td> : </td>
						<td id="barang-detail-nama"></td>
					</tr>
					<tr>
						<td>Harga</td>
						<td> : </td>
						<td id="barang-detail-harga"></td>
					</tr>
					<tr>
						<td>Stock</td>
						<td> : </td>
						<td id="barang-detail-stock"></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<div class="modal modal-danger fade" id="modal-danger">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Peringatan !!!!!</h4>
              </div>
              <div class="modal-body">
                <p>Apakah anda yakin ingin menghapus data ?</p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Tidak</button>
                <button type="button" id="hapus-data" data-id="" class="btn btn-outline">Ya, Hapus Data</button>
              </div>
            </div>
            <!-- /.modal-content -->
          </div>
          <!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->
<%@ include file="/WEB-INF/views/template/footer.jsp"%>