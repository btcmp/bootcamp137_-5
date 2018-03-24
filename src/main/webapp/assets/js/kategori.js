$(function() {

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
				url : baseUrl+"kategori/save",
				type : 'POST',
				contentType : 'application/json',
				data : JSON.stringify(kat),
				success : function(data) {
					//console.log(data);
					alert('save success');
					window.location = baseUrl+"kategori/index";
				},
				error : function() {
					alert('saving failed!');
				}                              
			});
			
		});
		
		
//--------------------------------------------------------------------------------------------------------cancel modal kategpri---------------------------------------------------------------------
		
		$('#btn-cancel-save').on('click', function() {
			$('#save-name').val('');
			
		})
		
		
//------------------------------------------------------------------------------edit----------------------------------------------------------------

		$('.editkategori').on('click', function(evt) {
			evt.preventDefault();
			var id = $(this).attr('id');
			//console.log(id)
			$.ajax({
				url : baseUrl+"kategori/get-id/"+id,
				type : 'GET',
				success : function(kat) {
					setEditKategori(kat);
					$('#editkat').modal();
				},
				error : function() {
					alert('failed getting data');
				},
				dataType : 'json'
			});
		});
		function setEditKategori(kat) {
			$('#edit-id').val(kat.id);
			$('#edit-name-kat').val(kat.name);
		}
		$('#btn-update').on('click', function() {
			var kat = {
					id : $('#edit-id').val(),
					name : $('#edit-name-kat').val(),
					
			}
			
			$.ajax({
				url : baseUrl+"kategori/update",
				type : 'PUT',
				data : JSON.stringify(kat),
				contentType : 'application/json',
				success : function(data) {
					alert('update success!!')
					window.location = baseUrl+"kategori/index";
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