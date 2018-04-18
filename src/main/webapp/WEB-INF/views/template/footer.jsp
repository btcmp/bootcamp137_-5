<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 2.4.0
    </div>
    <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
    reserved.
  </footer>

<!-- ./wrapper -->
<!-- jQuery 3 -->
<script src="${baseUrl }assets/node_modules/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${baseUrl }assets/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- DataTables -->
<script src="${baseUrl }assets/node_modules/datatables.net/js/jquery.dataTables.js"></script>
<script src="${baseUrl }assets/node_modules/datatables.net-bs/js/dataTables.bootstrap.js"></script>
<!-- ParsleyJS -->
<script src="${baseUrl }assets/node_modules/parsleyjs/dist/parsley.min.js"></script>

<script src="${baseUrl }assets/node_modules/easy-autocomplete/dist/jquery.easy-autocomplete.min.js"></script>

<script src="${baseUrl }assets/node_modules/jquery.inputmask/dist/jquery.inputmask.bundle.js"></script>
<script src="${baseUrl }assets/node_modules/pnotify/dist/iife/PNotify.js"></script>
<script src="${baseUrl }assets/node_modules/daterangepicker/moment.min.js"></script>
<script src="${baseUrl }assets/node_modules/daterangepicker/daterangepicker.js"></script>
<script src="${baseUrl }assets/node_modules/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="${baseUrl }assets/node_modules/highcharts/js/highcharts.js"></script>
<script src="${baseUrl }assets/node_modules/highcharts/modules/exporting.js"></script>
<script src="${baseUrl }assets/node_modules/highcharts/modules/export-data.js"></script>

<!-- AdminLTE App -->
<script src="${baseUrl }assets/dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${baseUrl }assets/dist/js/demo.js"></script>
<script type="text/javascript">
function displayNotif(keterangan, status){
	var notice = new PNotify({
		  target: document.body,
		  data: {
			  	title: status,
				text: keterangan,
				type: status,
				styling:'bootstrap3'
		  }
		});
	notice.on('click', function() {
	    notice.close();
	});
}
$(function () {
	  ${js}
})
</script>
<c:forEach items="${asset}" var="aset">
	<script src="${baseUrl }assets/js/${aset}.js"></script>
</c:forEach>
</body>
</html>
