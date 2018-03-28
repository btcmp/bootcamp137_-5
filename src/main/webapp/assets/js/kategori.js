$(function() {
	
		$('#btn-cari').on('click', function() {
			//alert("aaaaa");
			var kata = $('#cari').val();
			//console.log(kata);
			window.location = baseUrl+"kategori/index/src?search="+kata;
		})

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
	
		
	//-----------------------------------------------------------------------------------------------------nonactive-----------------------------------
		
		$('#btn-nonactive').on('click', function() {
			var kat = {
					id : $('#edit-id').val(),
					name : $('#edit-name-kat').val(),
					active : 0
			}
			
			$.ajax({
				url : baseUrl+"kategori/update",
				type : 'PUT',
				data : JSON.stringify(kat),
				contentType : 'application/json',
				success : function(data) {
					alert('kategori nonactive')
					window.location = baseUrl+"kategori/index";
				},
				error : function() {
					alert('update failed!!');
				}
			});
			
		});
		
//------------------------------------------------------------------------------------------------------edit------------------------------------------------
		
		$('#btn-update').on('click', function() {
			var kat = {
					id : $('#edit-id').val(),
					name : $('#edit-name-kat').val(),
					active : 1
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

});