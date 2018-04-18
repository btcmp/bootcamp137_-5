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
				<div class="col-lg-6">
					<input type="text" id="nama-item" class="form-control" placeholder="Search">
					<table id="table-item" class="table table-stripped table-bordered table-hover">
						
					</table>
				</div>
				<div class="col-lg-6">
					<a href="#" class="customer form-control btn btn-primary">Choose Customer</a>
					<table id="salesorder" class="table table-stripped table-bordered table-hover">
						<thead>
							<tr>
								<th style="width: 25%; text-align: center;">Item</th>
								<th style="width: 25%; text-align: center;">Qty</th>
								<th style="width: 25%; text-align: center;">Subtotal</th>
								<th style="width: 25%; text-align: center; display: none;">Harga</th>
								<th style="width: 5%; text-align: center;">#</th>
							</tr>
						</thead>
						<tbody id="list-item-pilih">
							
						</tbody>
						<tfoot id="total-harga">
							<tr>
								<th colspan="3">TOTAL : </th>
								<td colspan="1" id="total-harga-fix">Rp.0</td>
							</tr>
						</tfoot>
					</table>
						<a href="#" class="clear-sod btn btn-primary">Clear Sale</a>
						<button id="pay-sods" class="bayar-sod btn btn-primary" disabled>Charge Rp.0</button>
					</div>

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
				<div class="row	">
					<div class="col-lg-6 form-group">
						<input type="text" class="form-control" id="name-cust" placeholder="Search Customer" />
						</div>
					<div class="col-lg-3">
						<a id="cari-customer" class="col-lg-12 btn btn-primary" href="#">Search</a>
					</div>
					<div class="col-lg-3">
						<a id="add-new" href="#" class="col-lg-12 btn btn-primary">Add Customer</a>
					</div>
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
				<form id="form-save" action="#">
					<div class="form-group">
						<label>PROFILE</label>
						<input required data-parsley-length="[4,50]" type="text" class="form-control" id="save-name-cust" placeholder="Customer Name" />
						<input required type="email" class="form-control" id="save-email-cust" aria-describedby="emailHelp" placeholder="Email" />
						<input type="text" class="form-control" id="save-phone-cust" aria-describedby="emailHelp" placeholder="Phone number" />
						<input style="display: none;" type="text" class="form-control" id="save-phone-cust-database" placeholder="Phone number" />
					</div>
					
					<div class="form-group">
						<label>Day Of Birth</label>
						<input type="text" class="form-control" id="save-dob-cust" placeholder="Day Of Birth" />
					</div>
					
					<div class="form-group">
						<label>Address</label>
						<textarea data-parsley-minlength="4" style="height: 80px;" type="text" class="form-control" id="save-address-cust" placeholder="Address"></textarea>
					</div>
					<div class="row">
						<div class="col-lg-4">
							<select class="form-control" id="save-pro-cust" required>
									<option value=""> Provinsi</option>
									<c:forEach var="prov" items="${prs}">
										<option id="sprov" value="${prov.id}"> ${prov.name}</option>
									</c:forEach>
							</select>
						</div>
						<div class="col-lg-4">
								<select class="form-control" id="save-reg-cust" required>
									<option value=""> Region</option>
								</select>
						</div>
						<div class="col-lg-4">
								<select class="form-control" id="save-dis-cust" required>
									<option value="">District</option>
								</select>
						</div>
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

<div class="modal fade" id="pembayaran" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Payment</h5>
			</div>
			<div class="modal-body">
				<form action="#">
					<div class="col-lg-2">
						<label>Cash</label>
					</div>
					<div class="col-lg-8 form-group">
						<input type="text" value="0" class="form-control" id="id-cash" aria-describedby="emailHelp"/>
						<input name="cash-price" type="hidden" data-parsley-required="true" id="cash-price">
					</div>
					
				</form>
			</div>
			<div class="modal-footer">
				<button style="float: right;" type="button" id="btn-payment" class="btn btn-primary">DONE</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="finish" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel"></h5>
			</div>
			<div class="modal-body">
				<form action="#">
					<div class="form-group"  style="text-align: center; margin-left: 25%;  margin-right: 25%;">
						<output style="height: 80px; font-size: 30px;" type="number" class="form-control" id="id-finish"/>
					</div>
					<div class="form-group" style="text-align: center; margin-left: 25%; margin-right: 25%;">
						<output type="number" class="form-control" id="id-pay"></output>
					</div>
					<div style="text-align: center;">
						<label>How do you want to receive your receipts?</label>
					</div>
					<div class="row form-group" style="margin-bottom: 20px;">
						<div  class="col-lg-8">
							<output type="number" class="form-control email-cust"></output>
						</div>
						<div class="col-lg-4">
							<button type="button" id="btn-send" class="form-control btn btn-primary">Send</button>
						</div>
					</div> 
					
				</form>
			</div>
			<div class="modal-footer">
				<div>
					<button type="button" id="btn-print" class="form-control btn btn-primary">Print Receipt</button>
				</div>
				<div>
					<button type="button" id="btn-done" class="form-control btn btn-danger">No Thanks, Done</button>
				</div>
			</div>
		</div>
	</div>
</div>


<%@ include file="/WEB-INF/views/template/footer.jsp"%>