$(function() {

//----------------------------------------------------------------------save---------------------------------------------------
		$('#add').on('click', function() {
			$('#saveout').modal();
		})
		$('#btn-save').on('click', function(evt) {
			evt.preventDefault();
			var out = {
					name : $('#save-name').val(),
					address : $('#save-address').val(),
					postalCode : $('#save-postalCode').val(),
					phone : $('#save-phone').val(),
					email : $('#save-email').val()
			}
			//console.log(kat);
		
		
			$.ajax({
				url : baseUrl+"outlet/save",
				type : 'POST',
				contentType : 'application/json',
				data : JSON.stringify(out),
				success : function(data) {
					//console.log(data);
					alert('save success');
					window.location = baseUrl+"outlet/index";
				},
				error : function() {
					alert('saving failed!');
				}                              
			});
			
		});
		
		
//---------------------------------------------------------------------------------------------------------list region---------------------------------------------
		
		$('#save-pro').on('change', function() {
			var id = $(this).val();
			console.log(id);
		});		
		
		
//--------------------------------------------------------------------------------------------------------cancel modal kategpri---------------------------------------------------------------------
		
		$('#btn-cancel-save').on('click', function() {
			$('#save-name').val('');
			
		})
		
		
//------------------------------------------------------------------------------edit----------------------------------------------------------------

		$('.editoutlet').on('click', function(evt) {
			evt.preventDefault();
			var id = $(this).attr('id');
			console.log(id);
			$.ajax({
				url : baseUrl+"outlet/get-id/"+id,
				type : 'GET',
				success : function(out) {
					setEditOutlet(out);
					$('#editout').modal();
				},
				error : function() {
					alert('failed getting data');
				},
				dataType : 'json'
			});
		});
		function setEditOutlet(out) {
			$('#edit-id').val(out.id);
			$('#edit-name-out').val(out.name);
			$('#edit-address-out').val(out.address);
			$('#edit-code-out').val(out.postalCode);
			$('#edit-phone-out').val(out.phone);
			$('#edit-email-out').val(out.email);
		}
		$('#btn-update').on('click', function() {
			var outlet = {
					id : $('#edit-id').val(),
					name : $('#edit-name-out').val(),
					address : $('#edit-address-out').val(),
					postalCode : $('#edit-code-out').val(),
					phone : $('#edit-phone-out').val(),
					email : $('#edit-email-out').val(),
					
			}
			
			$.ajax({
				url : baseUrl+"outlet/update",
				type : 'PUT',
				data : JSON.stringify(outlet),
				contentType : 'application/json',
				success : function(data) {
					alert('update success!!')
					window.location = baseUrl+"outlet/index";
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