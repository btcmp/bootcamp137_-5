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
	asset.add("items");
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>List Items</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<p>
						<button class="btn btn-primary" id="add-data">Add
							Items</button>
					</p>
					<table id="items-list"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>Name</th>
								<th>Category</th>
								<th>Unit Price</th>
								<th>In Stock</th>
								<th>Stock Alert</th>
								<th>#</th>
							</tr>
						</thead>

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
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<img alt="" src="" style="width: 100%; height: 100%" />
							</div>
							<div class="col-md-8">
								<div class="form-group">
									<input name="items-name" type="text" placeholder="Item Name"
										data-parsley-required="true" class="form-control"
										id="items-name"> <input name="items-id" type="hidden"
										class="form-control" id="items-id">
								</div>
								<div class="form-group">
									<select id="items-category-id" class="form-control"
										data-parsley-required="true">
										<option value="">Category</option>
										<c:forEach items="${category}" var="jrs">
											<option value="${jrs.id }">${jrs.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-6">
								<span style="font-size: 26px; font-weight: 600;">Variant</span>
							</div>
							<div class="col-md-6">
								<button type="button" class="btn btn-primary pull-right"
									id="add-variant">Add Variant</button>
							</div>
						</div>
					</div>
					<div class="form-group">
						<table class="table table-bordered table-striped"
							id="list-variant">
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
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4"><button type="button" class="btn btn-primary" id="add-data">Back</button></div>
							<div class="col-md-4 text-center"><button type="button" class="btn btn-primary" id="add-data">Cancel</button></div>
							<div class="col-md-4"><button type="submit" class="btn btn-primary pull-right" id="btn-items-save">Save</button></div>
						</div>
					</div>
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
				<div id="warning-variant" class="callout callout-warning hidden">
					<h4>Oh snap!</h4>
					<p>This form seems to be invalid :(</p>
				</div>
				<form id="form-variant" action="#" method="post"
					data-parsley-validate="">

					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<input name="variant-name" type="text"
									placeholder="Variant Name" data-parsley-required="true"
									class="form-control" id="variant-name"> <input
									name="variant-id" type="hidden" class="form-control"
									id="variant-id">
							</div>
							<div class="col-md-4">
								<input name="variant-price-mask" type="text"
									placeholder="Unit Price" value="0" data-parsley-required="true"
									class="form-control" id="variant-price-mask">
								<input name="variant-price" type="hidden" data-parsley-required="true" id="variant-price">
							</div>
							<div class="col-md-4">
								<input name="variant-sku" type="text" placeholder="SKU"
									data-parsley-required="true" class="form-control"
									id="variant-sku">
							</div>
						</div>
					</div>

					<div class="form-group">
						<h3>Set Begining Stock</h3>
						<div class="row">
							<div class="col-md-6">
								<input name="inventory-begining" type="text"
									placeholder="Begining Stock" data-parsley-required="true"
									class="form-control" id="inventory-begining">
							</div>
							<div class="col-md-6">
								<input name="inventory-alert-at" type="text"
									placeholder="Alert At" data-parsley-required="true"
									class="form-control" id="inventory-alert-at">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4"><button type="button" class="btn btn-primary" id="add-data">Back</button></div>
							<div class="col-md-4 text-center"><button type="button" class="btn btn-primary" id="add-data">Cancel</button></div>
							<div class="col-md-4"><button type="button" state="create" class="btn btn-primary pull-right" id="btn-add-variant">Add</button></div>
						</div>
					</div>
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
						<td>:</td>
						<td id="barang-detail-id"></td>
					</tr>
					<tr>
						<td>Kode Barang</td>
						<td>:</td>
						<td id="barang-detail-kode"></td>
					</tr>
					<tr>
						<td>Nama Barang</td>
						<td>:</td>
						<td id="barang-detail-nama"></td>
					</tr>
					<tr>
						<td>Harga</td>
						<td>:</td>
						<td id="barang-detail-harga"></td>
					</tr>
					<tr>
						<td>Stock</td>
						<td>:</td>
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
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Peringatan !!!!!</h4>
			</div>
			<div class="modal-body">
				<p>Apakah anda yakin ingin menghapus data ?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline pull-left"
					data-dismiss="modal">Tidak</button>
				<button type="button" id="hapus-data" data-id=""
					class="btn btn-outline">Ya, Hapus Data</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<%@ include file="/WEB-INF/views/template/footer.jsp"%>