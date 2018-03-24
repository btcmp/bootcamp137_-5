<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/WEB-INF/views/template/header.jsp"%>
<%
	Breadcrumb bc = new Breadcrumb((String) pageContext.getAttribute("baseUrl"),
			(String) request.getAttribute("title"));
	request.setAttribute("bc", bc);
	List<String> asset = new ArrayList();
	asset.add("adjustment");
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>Adjustment List</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<p>
						<button class="btn btn-primary" id="tambah-data">Create</button>
					</p>
					<table id="adjustment-list"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>Adjustment Date</th>
								<th>Notes</th>
								<th>Status</th>
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
				<form id="form-adjustment" action="#" method="post"
					data-parsley-validate="">
					<div class="form-group">
						<label for="nama" class="form-control">Create Adjustment : <span>Nama Outlet</span></label> 
					</div>
					<div class="form-group">
						<label for="nama">Notes:</label> <textarea name="adjust-notes"
							data-parsley-required="true" class="form-control" id="adjust-notes"></textarea>
					</div>
					<div class="form-group">
						<label for="nama">Adjustment Stock:</label>
					</div>
					<div class="form-group">
						<button type="button" class="btn btn-primary pull-right"
									id="add-item">Add Item</button>
					</div>
					<div class="form-group">
						<table class="table table-bordered table-striped"
							id="list-item">
							<thead>
								<tr>
									<th>Item</th>
									<th>In Stock</th>
									<th>Adj.Qty.</th>
									<th>#</th>
								</tr>
							</thead>
							<tbody id="list-item-body">

							</tbody>
						</table>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4"><button type="button" class="btn btn-primary" id="tambah-data">Back</button></div>
							<div class="col-md-4 text-center"><button type="button" class="btn btn-primary" id="tambah-data">Cancel</button></div>
							<div class="col-md-4"><button type="submit" class="btn btn-primary pull-right" id="btn-adjust-save">Save</button></div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<div id="modal-item" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="modal-item-title">Create New Variant</h4>
			</div>
			<div class="modal-body">
				<div id="warning-variant" class="callout callout-warning hidden">
					<h4>Oh snap!</h4>
					<p>This form seems to be invalid :(</p>
				</div>
				<form id="form-variant" action="#" method="post"
					data-parsley-validate="">

					<div class="form-group">
						<input id="item-name-variant" class="form-control"/>
					</div>

					<div class="form-group">
						<table class="table table-bordered table-striped"
							id="form-list-item">
							<thead>
								<tr>
									<th>Item</th>
									<th>In Stock</th>
									<th>Adj.Qty.</th>
								</tr>
							</thead>
							<tbody id="form-list-item">

							</tbody>
						</table>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4"><button type="button" class="btn btn-primary" id="tambah-data">Back</button></div>
							<div class="col-md-4 text-center"><button type="button" class="btn btn-primary" id="tambah-data">Cancel</button></div>
							<div class="col-md-4"><button type="button" class="btn btn-primary pull-right" id="btn-add-item">Add</button></div>
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