<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<spring:url value="/assets/node_modules/jquery/dist/jquery.min.js" var="jq"></spring:url>
<spring:url value="/assets/node_modules/parsleyjs/dist/parsley.min.js" var="parsley"></spring:url>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Entry Kategori</title>
<!-- <link rel= "stylesheet" href="http://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" /> -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel= "stylesheet" href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap4.min.css" />
<link rel= "stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0/css/bootstrap.css" />
<script type="text/javascript" src="${jq}"></script>
<script type="text/javascript" src="${parsley}"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap4.min.js"></script>

<script type="text/javascript">
	$(function() {
		//setup data table employee table
		/* $('#kat-tbl').DataTable({
			pagging: 'true'
		}); */

		
//----------------------------------------------------------------------save---------------------------------------------------
		$('#add').on('click', function() {
			$('#savekat').modal();
		})
		$('#btn-save').on('click', function(evt) {
			evt.preventDefault();
			var kat = {
					name : $('#save-name').val()
			}
			//console.log(kat);
		
		
			$.ajax({
				url : '${pageContext.request.contextPath}/jspfolder/kategori/save',
				type : 'POST',
				contentType : 'application/json',
				data : JSON.stringify(kat),
				success : function(data) {
					//console.log(data);
					alert('save success');
					window.location = '${pageContext.request.contextPath}/jspfolder/kategori';
				},
				error : function() {
					alert('saving failed!');
				}                              
			});
			
		});
		
		
//------------------------------------------------------------------------------edit----------------------------------------------------------------

		$('.update').on('click', function(evt) {
			evt.preventDefault();
			var id = $(this).attr('id');
			//console.log(id)
			$.ajax({
				url : '${pageContext.request.contextPath}/pegawai/get-one/'+id,
				type : 'GET',
				success : function(pgw) {
					setEditEmployee(pgw);
					$('#editpgw').modal();
				},
				error : function() {
					alert('failed getting data');
				},
				dataType : 'json'
			});
		});
		function setEditEmployee(pgw) {
			$('#edit-id').val(pgw.id);
			$('#edit-first').val(pgw.firstName);
			$('#edit-last').val(pgw.lastName);
			$('#edit-email').val(pgw.email);
			$('#edit-phone').val(pgw.phoneNumber);
			$('#edit-hire').val(pgw.hireDate);
			$('#edit-com').val(pgw.commisionPct);
			$('#edit-salary').val(pgw.salary);
			$('#edit-dpt').val(pgw.departement.id);
		}
		$('#btn-update').on('click', function() {
			var emp = {
					id : $('#edit-id').val(),
					firstName : $('#edit-first').val(),
					lastName : $('#edit-last').val(),
					email : $('#edit-email').val(),
					phoneNumber : $('#edit-phone').val(),
					commisionPct : $('#edit-com').val(),
					hireDate : $('#edit-hire').val(),
					salary : $('#edit-salary').val(),
					departement : {
						id : $('#edit-dpt').val()
					}
			}
			
			$.ajax({
				url : '${pageContext.request.contextPath}/pegawai/update',
				type : 'PUT',
				data : JSON.stringify(emp),
				contentType : 'application/json',
				success : function(data) {
					alert('update success!!')
					window.location = '${pageContext.request.contextPath}/pegawai';
				},
				error : function() {
					alert('update failed!!');
				}
			});
			
		});
		
//------------------------------------------------------------------delete--------------------------------------------------------

		$('.delete').on('click', function() {
			var id = $(this).attr('id');
			$('#delete-id').val(id);
			$('#deletepgw').modal();
		})
		$('#btn-delete').on('click', function() {
			var id = $('#delete-id').val();
			
			$.ajax({
				url : '${pageContext.request.contextPath}/pegawai/delete/'+id,
				type : 'GET',
				success : function() {
					alert('delete success')
					window.location = '${pageContext.request.contextPath}/pegawai';
				},
				error : function() {
					alert('delete failed')
				}
				
			})
		})
		
});
</script>
</head>
<body>
<div class="container">
	<h4 style="text-align: left; margin-bottom: 20px">Category</h4>
	<div>
		<input type="text" id="search" placeholder="Search" style="float: left;">
		<a style="float: right; width: 15%; margin-bottom: 20px;" id="add" class="btn btn-info" href="#">Create</a>
	</div>
	<table border="2px" id="kat-tbl" width="100%">
		<thead>
			<tr>
				<th style="width: 25%; text-align: center;">Category Name</th>
				<th style="width: 25%; text-align: center;">Item Stock</th>
				<th style="width: 5%; text-align: center;">#</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items= "${kats}" var="kats">
			 	<tr>
			 		<td>${kats.name}</td>
			 		<td></td>
			 		<td>
			 			<!-- class untuk selektor -->
			 			<%-- <a id="${kats.id}" class="delete btn btn-danger" href="#">Delete</a> |
			 			 --%>
			 			 <a id="${kats.id}" href="#" >view</a>
			 		</td>
			 	</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@ include file="modal/save-kat.jsp" %>
	<%@ include file="modal/edit-kat.jsp" %>
	<%@ include file="modal/delete-kat.jsp" %>
</div>

	
</body>
</html>