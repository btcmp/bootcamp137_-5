$(function() {
	
		$('#kategori-list').DataTable({
			searching : false
		});
	
		$('#btn-cari').on('click', function() {
			var kata = $('#cari').val();
			window.location = baseUrl+"kategori/index/src?search="+kata;
		})

//----------------------------------------------------------------------save---------------------------------------------------
		$('#add').on('click', function() {
			$('#savekat').modal();
		})
		$('#btn-save').on('click', function(evt) {
			evt.preventDefault();
			var formsave = $('#form-save');
			var validsave = formsave.parsley().validate();
			var kat = {
					name : $('#save-name').val(),
					active : 1
			}
			var dupname;
			$.ajax({
				url : baseUrl+"kategori/get-all-name",
				type : 'GET',
				contentType : 'application/json',
				success : function(listname) {
					dupname = 0;
					$.each(listname , function(index,name) {
						if($('#save-name').val().toLowerCase() == name.toLowerCase()){
							dupname = 1;
						}
					});
					if(dupname == 1){
						alert('kategori name has been used');
					}
					else{
						if (validsave == true){
							$.ajax({
								url : baseUrl+"kategori/save",
								type : 'POST',
								contentType : 'application/json',
								data : JSON.stringify(kat),
								success : function(data) {
									alert('save success');
									window.location = baseUrl+"kategori/index";
								},
								error : function() {
									alert('saving failed!');
								}                              
							});
						}
					}
				},
				error : function() {
					alert('error getting all name')
				}
			})
		});
		
		
//--------------------------------------------------------------------------------------------------------cancel modal kategpri---------------------------------------------------------------------
		
		$('#btn-cancel-save').on('click', function() {
			$('#save-name').val('');
			
		})
		
		
//------------------------------------------------------------------------------edit----------------------------------------------------------------

		$('.editkategori').on('click', function(evt) {
			evt.preventDefault();
			var id = $(this).attr('id');
			$.ajax({
				url : baseUrl+"kategori/get-id/"+id,
				type : 'GET',
				success : function(kat) {
					setEditKategori(kat);
					console.log(kat);
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
			$('#edit-createdon').val(kat.createdOn);
			$('#edit-createdby').val(kat.createdBy.id);
		}
	
		
	//-----------------------------------------------------------------------------------------------------nonactive-----------------------------------
		
		$('#btn-nonactive').on('click', function() {
			var formedit = $('#form-edit');
			var validedit = formedit.parsley().validate();
			var kat = {
					id : $('#edit-id').val(),
					name : $('#edit-name-kat').val(),
					createdOn : $('#edit-createdon').val(),
					createdBy : {
						id : $('#edit-createdby').val(),
					},
					active : 0
			}
			console.log(kat);
			
			if (validedit == true){
				$.ajax({
					url : baseUrl+"kategori/deactive",
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
			}
			
		});
		
//------------------------------------------------------------------------------------------------------edit------------------------------------------------
		
		$('#btn-update').on('click', function() {
			var formedit = $('#form-edit');
			var validedit = formedit.parsley().validate();
			var kat = {
					id : $('#edit-id').val(),
					name : $('#edit-name-kat').val(),
					createdOn : $('#edit-createdon').val(),
					createdBy : {
						id : $('#edit-createdby').val(),
					},
					active : 1
			}
			var dupnameedit;
			$.ajax({
				url : baseUrl+"kategori/get-all",
				type : 'GET',
				contentType : 'application/json',
				success : function(kategori) {
					dupnameedit = 0;
					$.each(function(index, kategori) {
						if ($('#edit-id').val() !== kategori.id) {
							if ($('#edit-name-kat').val() == kategori.name) {
								dupnameedit=1;
							}
						}
					});
					if (dupnameedit == 1) {
						alert('kategori name has been used')
					}
					else {
						if(validedit == true){
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
						}
					}
				},
				error : function() {
					alert('error getting data')
				}
			})
		});
});