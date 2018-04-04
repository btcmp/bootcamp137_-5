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
					<div class="col-md-3">
						<div class="input-group">
							<input id="search-by-date" class="form-control" /> <span
								class="input-group-btn">
								<button type="button" id="reset-filter"
									class="btn btn-info btn-flat">Reset</button>
							</span>
						</div>
					</div>
					<div class="col-md-9">
						<div class="col-md-9">
							<a target="_blank" href="${baseUrl}/adjustment/report "><button class="btn btn-primary pull-right" id="export-data">Export</button></a>
						</div>
						<div class="col-md-3">
							<button class="btn btn-primary pull-right" id="add-data">Create
								Adjustment</button>
						</div>
					</div>
					<table id="adjust-list"
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
				<h4 class="modal-title" id="myModal-title">Create New
					Adjustment</h4>
			</div>
			<div class="modal-body">
				<div class="callout callout-warning hidden">
					<h4>Oh snap!</h4>
					<p>This form seems to be invalid :(</p>
				</div>
				<form id="form-adjust" action="#" method="post"
					data-parsley-validate="">
					<div class="form-group">
						<label for="nama" class="form-control">Create Adjustment :
							<span>${ outlet.name }</span>
						</label>
					</div>
					<div class="form-group">
						<label for="nama">Notes:</label>
						<textarea name="adjust-notes" class="form-control" data-parsley-required="true"
							id="adjust-notes"></textarea>
					</div>
					<div class="form-group">
						<label for="nama">Adjustment Stock:</label>
					</div>

					<div class="form-group">
						<table class="table table-bordered table-striped" id="list-item">
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
						<button type="button" class="btn btn-primary btn-block"
							id="add-item">Add Item</button>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<button type="button" class="btn btn-primary" id="btn-adjust-back">Back</button>
							</div>
							<div class="col-md-4 text-center">
								<button type="button" class="btn btn-primary" id="btn-adjust-cancel">Cancel</button>
							</div>
							<div class="col-md-4">
								<button type="submit" class="btn btn-primary pull-right"
									id="btn-adjust-save" disabled>Save</button>
							</div>
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
				<h4 class="modal-title" id="modal-item-title">Add Items</h4>
			</div>
			<div class="modal-body">
				<form id="form-variant" action="#" method="post"
					data-parsley-validate="">

					<div class="form-group">
						<input id="item-name-variant" />
					</div>

					<div class="form-group">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Item</th>
									<th>In Stock</th>
									<th>Adj.Qty.</th>
									<th>#</th>
								</tr>
							</thead>
							<tbody id="form-list-item">

							</tbody>
						</table>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<button type="button" class="btn btn-primary" id="btn-back-item">Back</button>
							</div>
							<div class="col-md-4 text-center">
								<button type="button" class="btn btn-primary" id="btn-cancel-item">Cancel</button>
							</div>
							<div class="col-md-4">
								<button type="submit" class="btn btn-primary pull-right"
									id="btn-add-item" disabled>Add</button>
							</div>
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
				<select data-id="" class="close" id="status-more">
					
				</select>
				<h4 class="modal-title">Adjustment Detail</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<span>Created By : <label id="adjust-created-by-label"></label></span>
				</div>
				<div class="form-group">
					<span>Adjustment Status : <label id="adjust-status-label"></label></span>
				</div>
				<div class="form-group">
					<span>Notes:</span>
					<textarea name="adjust-notes" class="form-control"
						id="adjust-notes-label" disabled></textarea>
				</div>
				<div class="form-group">
					<label for="nama">Status History:</label>
					<div id="status-history"></div>
				</div>

				<div class="form-group">
					<label for="nama">Adjustment Stock:</label>
				</div>

				<div class="form-group">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Item</th>
								<th>In Stock</th>
								<th>Adj.Qty.</th>
							</tr>
						</thead>
						<tbody id="label-list-item">

						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<div class="modal modal-warning fade" id="modal-status">
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
				<p>Apakah anda yakin ingin mengubah status ?</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline pull-left"
					data-dismiss="modal">Tidak</button>
				<button type="button" id="ubah-status" data-id=""
					class="btn btn-outline">Ya, Ubah Status</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<%@ include file="/WEB-INF/views/template/footer.jsp"%>