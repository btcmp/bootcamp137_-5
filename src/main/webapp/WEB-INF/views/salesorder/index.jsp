<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/WEB-INF/views/template/header.jsp"%>
<%
	Breadcrumb bc = new Breadcrumb((String) pageContext.getAttribute("baseUrl"),
			(String) request.getAttribute("title"));
	request.setAttribute("bc", bc);
	List<String> asset = new ArrayList();
	asset.add("salesorder"); //js
	request.setAttribute("asset", asset);
%>
<%@ include file="/WEB-INF/views/template/menu.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3>Sales Order</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
				<div>
					<input type="text" id="nama-item" class="form-control" placeholder="Search">
					<a id="cari-item" class="btn btn-primary" href="#" style="margin-bottom: 20px">Search</a>
					<table id="table-item" class="table table-stripped table-bordered table-hover">
					
					</table>
				</div>
					<a href="#" id="customer" class="form-control btn btn-primary">Choose Customer</a>
					<table id="saleorder" class="table table-stripped table-bordered table-hover">
						<thead>
							<tr>
								<th style="width: 25%; text-align: center;">Item</th>
								<th style="width: 25%; text-align: center;">Qty</th>
								<th style="width: 25%; text-align: center;">Subtotal</th>
								<th style="width: 5%; text-align: center;">#</th>
							</tr>
						</thead>
						<tbody style="text-align: center;">
							<c:forEach items= "${sos}" var="sos">
							 	<tr>
							 		<td>${sos.salesOrderDetails.itemVariant.name }</td>
							 		<td>${sos.salesOrderDetails.qty}</td>
							 		<td>${sods.salesOrderDetails.subTotal}</td>
							 		<td>
							 			<!-- class untuk selektor -->
							 			 <a id="${sos.id}" class="btn-cancel btn btn-danger" href="#" >X</a>
							 		</td>
							 	</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>TOTAL : </th>
							</tr>
						</tfoot>
					</table>
					<a href="#" class="clear-sod btn btn-primary">Clear Sale</a>
					<a href="#" class="bayar-sod btn btn-primary">Charge Rp</a>
					

				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</section>

<div class="modal fade" id="choose-cust" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Input Customer</h5>
			</div>
			<div class="modal-body">
				<form action="#">
					<div class="form-group">
						<input type="text" class="form-control" id="name-cust" aria-describedby="emailHelp" placeholder="Search Customer" />
						<a id="cari-customer" class="btn btn-primary" href="#">Search</a>
						<a id="add-new" href="#" class="btn btn-primary">Add Customer</a>
					</div>
					<div>
						<table  id="table-customer" class="table table-stripped table-bordered table-hover">
							<thead>
								<tr>
									<th>Name</th>
									<th>Email</th>
									<th>phone</th>
									<th>#</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
					</div>
				</form>
			<div class="modal-footer">
				<button style="float: left;" type="button" id="btn-cancel-save" class="btn btn-primary">CANCEL</button>
				<button style="float: right;" type="button" id="btn-save" class="btn btn-primary">SAVE</button>
			</div>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="new-cust" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">New Customer</h5>
			</div>
			<div class="modal-body">
				<form action="#">
					<div class="form-group">
						<label>PROFILE</label>
						<input type="text" class="form-control" id="save-name-cust" aria-describedby="emailHelp" placeholder="Customer Name" />
						<input type="email" class="form-control" id="save-email-cust" aria-describedby="emailHelp" placeholder="Email" />
						<input type="text" class="form-control" id="save-phone-cust" aria-describedby="emailHelp" placeholder="Phone" />
					</div>
					
					<div class="form-group">
						<label>Day Of Birth</label>
						<input type="date" max="2018-03-27" class="form-control" id="save-dob-cust" aria-describedby="emailHelp" placeholder="Day Of Birth" />
					</div>
					
					<div class="form-group">
						<label>Address</label>
						<input type="text" class="form-control" id="save-address-cust" aria-describedby="emailHelp" placeholder="address" />
							<select class="form-control" id="save-pro-cust">
								<option value="">Provinsi</option>
								<c:forEach var="prov" items="${prs}">
									<option id="sprov" value="${prov.id}"> ${prov.name}</option>
								</c:forEach>
							</select>
							<select class="form-control" name="save-reg" id="save-reg-cust">
								<option value=""> Region</option>
							</select>
							<select class="form-control" name="save-dis" id="save-dis-cust">
								<option value="">District</option>
							</select>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button style="float: left;" type="button" id="btn-cancel-save" class="btn btn-primary">CANCEL</button>
				<button style="float: right;" type="button" id="btn-simpan" class="btn btn-primary">SAVE</button>
			</div>
		</div>
	</div>
</div>




<%@ include file="/WEB-INF/views/template/footer.jsp"%>