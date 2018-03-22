<%@page import="com.miniproject.pos.model.Items"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/WEB-INF/views/template/header.jsp"%>
<%
	Breadcrumb bc = new Breadcrumb((String) pageContext.getAttribute("baseUrl"),
			(String) request.getAttribute("title"));
	request.setAttribute("bc", bc);
	List<String> asset = new ArrayList();;
	asset.add("items");
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>Daftar Barang</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
				<p>
					<button class="btn btn-primary" id="tambah-data">Tambah Data</button>
					</p>
					<table id="barang-list" class="table table-stripped table-bordered table-hover">
						<thead>
							<tr>
								<th>No</th>
								<th>Kode Barang</th>
								<th>Nama Barang</th>
								<th>Harga</th>
								<th>Stock</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody id="data-barang">
							<%
								int no = 1;
								List<Items> items = (List<Items>) request.getAttribute("items");
								if (items != null)
									for (Items i : items) {
							%>
							<tr>
								<td><%=no%></td>
								<td><%=i.getId()%></td>
								<td><button class="lihat-data btn btn-success btn-xs" data-id="<%=i.getId()%>" data-toggle="tooltip" data-placement="top" title="Lihat Data"> <i class=" fa fa-check"></i> </button> 
								<button class="update-data btn btn-primary btn-xs" data-id="<%=i.getId()%>" data-toggle="tooltip" data-placement="top" title="Edit Data"> <i class=" fa fa-pencil"> </i></button>
								<button class="hapus-data btn btn-danger btn-xs" data-id="<%=i.getId()%>" data-toggle="tooltip" data-placement="top" title="hapus Data"> <i class=" fa fa-trash"> </i></button></td>
							</tr>
							<%
								no++;
									}
							%>
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
<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="myModal-title">Tambah Data Barang
					Baru</h4>
			</div>
			<div class="modal-body">
				<div class="callout callout-warning hidden">
					<h4>Oh snap!</h4>
					<p>This form seems to be invalid :(</p>
				</div>
				<form id="form-barang" action="#" method="post"
					data-parsley-validate="">
					<div class="form-group">
						<label for="nim">Kode Barang:</label> <input name="kode-barang" type="text"
							data-parsley-required="true" class="form-control" id="kode-barang"> <input
							name="id" type="hidden" class="form-control" id="id">
					</div>
					<div class="form-group">
						<label for="nama">Nama Barang:</label> <input name="nama-barang"
							data-parsley-required="true" type="text" class="form-control" id="nama-barang">
					</div>
					<div class="form-group">
						<label for="email">Harga :</label> <input type="text" data-parsley-required="true"
							class="form-control" id="harga"> <input name="harga"
							type="hidden" class="form-control" id="harga-mask">
					</div>
					<div class="form-group">
						<label for="prodi">Stok:</label> <input name="stock" type="number"
							data-parsley-min="1" required="" class="form-control" id="stock">
					</div>
					<div class="form-group">
						<input type="submit" id="form-barang-action" value="Simpan"
							class="btn btn-primary"> 
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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