$(function() {
	

//----------------------------------------------------------------------save---------------------------------------------------
		$('#new').on('click', function() {
			$('#savesup').modal();
		})
		$('#btn-save-sup').on('click', function(evt) {
			evt.preventDefault();
			var out = {
					name : $('#save-name-sup').val(),
					address : $('#save-address-sup').val(),
					provinsi :{
						id : $('#save-pro-sup').val()
					},
					region : {
						id : $('#save-reg-sup').val()
					},
					district : {
						id : $('#save-dis-sup').val()
					},
					postalCode : $('#save-code-sup').val(),
					phone : $('#save-phone-sup').val(),
					email : $('#save-email-sup').val(),
					active : 1
			}
			//console.log(out);
		
		
			$.ajax({
				url : baseUrl+"supplier/save",
				type : 'POST',
				contentType : 'application/json',
				data : JSON.stringify(out),
				success : function(data) {
					//console.log(data);
					alert('save success');
					window.location = baseUrl+"supplier/index";
				},
				error : function() {
					alert('saving failed!');
				}                              
			});
			
		});
		
		
//---------------------------------------------------------------------------------------------------------list region---------------------------------------------
		
		$('#save-pro-sup').on('change', function() {
			var id = $(this).val();
			//console.log(id);
			if (id!==""){
				$.ajax({
					url : baseUrl+"region/get-region?id="+id,
					type : 'GET',
					success : function(regionss) {
						var region = [];
							var reg = '<option value=/"/">Region</option>';
							region.push(reg);
							$(regionss).each(function(index, data2) {
								reg = "<option value=\""+data2.id+"\">"+data2.name+"</option>";
								region.push(reg);
							})
							
							$('#save-reg-sup').html(region);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});		
		
		
							
//-------------------------------------------------------------------------------------------list district-------------------------------------------------
							
		$('#save-reg-sup').on('change', function() {
			var id = $(this).val();
			//console.log(id);
			if (id!=""){
				$.ajax({
					url : baseUrl+"kecamatan/get-kecamatan?id="+id,
					type : 'GET',
					success : function(districtsss) {
						var district = [];
							var dis = '<option value=/"/">District</option>';
							district.push(dis);
							$(districtsss).each(function(index, data2) {
								dis = "<option value=\""+data2.id+"\">"+data2.name+"</option>";
								district.push(dis);
							})
							
							$('#save-dis-sup').html(district);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});
				
		
//------------------------------------------------------------------------------edit----------------------------------------------------------------

		$('.editsupplier').on('click', function(evt) {
			evt.preventDefault();
			var id = $(this).attr('id');
			//console.log(id);
			$.ajax({
				url : baseUrl+"supplier/get-id/"+id,
				type : 'GET',
				success : function(sup) {
					setEditSupplier(sup);
					$('#editsup').modal();
				},
				error : function() {
					alert('failed getting data');
				},
				dataType : 'json'
			});
		});
		function setEditSupplier(sup) {
			$('#edit-id-sup').val(sup.id);
			$('#edit-name-sup').val(sup.name);
			$('#edit-address-sup').val(sup.address);
			/*$('#edit-prov-sup').val(sup.provinsi.id);
			$('#edit-reg-sup').val(sup.region.id);
			$('#edit-dis-sup').val(sup.district.id);*/
			$('#edit-code-sup').val(sup.postalCode);
			$('#edit-phone-sup').val(sup.phone);
			$('#edit-email-sup').val(sup.email);
		};
		
//----------------------------------------------------------------------------------------------------nonactive-------------------
		$('#btn-nonactive').on('click', function() {
			var supplier = {
					id : $('#edit-id-sup').val(),
					name : $('#edit-name-sup').val(),
					address : $('#edit-address-sup').val(),
					provinsi : {
						id : $('#edit-prov-sup').val()
					},
					region : {
						id : $('#edit-reg-sup').val()
					},
					district : {
						id : $('#edit-dis-sup').val()
					},
					postalCode : $('#edit-code-sup').val(),
					phone : $('#edit-phone-sup').val(),
					email : $('#edit-email-sup').val(),
					active : 0
					
			}
			//console.log(supplier);
			$.ajax({
				url : baseUrl+"supplier/update",
				type : 'PUT',
				data : JSON.stringify(supplier),
				contentType : 'application/json',
				success : function(data) {
					alert('Supplier nonactive')
					window.location = baseUrl+"supplier/index";
				},
				error : function() {
					alert('update failed!!');
				}
			});
			
		});
		
//-------------------------------------------------------------------------------------------------------------update----------
		
		$('#btn-update-sup').on('click', function() {
			var supplier = {
					id : $('#edit-id-sup').val(),
					name : $('#edit-name-sup').val(),
					address : $('#edit-address-sup').val(),
					provinsi : {
						id : $('#edit-prov-sup').val()
					},
					region : {
						id : $('#edit-reg-sup').val()
					},
					district : {
						id : $('#edit-dis-sup').val()
					},
					postalCode : $('#edit-code-sup').val(),
					phone : $('#edit-phone-sup').val(),
					email : $('#edit-email-sup').val(),
					active : 1
			}
			//console.log(supplier);
			
			$.ajax({
				url : baseUrl+"supplier/update",
				type : 'PUT',
				data : JSON.stringify(supplier),
				contentType : 'application/json',
				success : function(data) {
					alert('update success!!')
					window.location = baseUrl+"supplier/index";
				},
				error : function() {
					alert('update failed!!');
				}
			});
			
		});
		
//---------------------------------------------------------------edit list region---------------------------------------------
		
		$('#edit-prov-sup').on('change', function() {
			var id = $(this).val();
			//console.log(id);
			if (id != ""){
				$.ajax({
					url : baseUrl+"region/get-region?id="+id,
					type : 'GET',
					success : function(regionss) {
						var region = [];
							var reg = '<option value=/"/">Region</option>';
							region.push(reg);
							$(regionss).each(function(index, data2) {
								var reg = "<option value=\""+data2.id+"\">"+data2.name+"</option>";
								region.push(reg);
							})
							
							$('#edit-reg-sup').html(region);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});
		
//----------------------------------------------------------------------edit list district----------------------------------
		
		$('#edit-reg-sup').on('change', function() {
			var id = $(this).val();
			//console.log(id);
			if (id !="" ){
				$.ajax({
					url : baseUrl+"kecamatan/get-kecamatan?id="+id,
					type : 'GET',
					success : function(districtsss) {
						var district = [];
							var dis = '<option value=/"/">District</option>';
							district.push(dis);
							$(districtsss).each(function(index, data2) {
								dis = "<option value=\""+data2.id+"\">"+data2.name+"</option>";
								district.push(dis);
							})
							
							$('#edit-dis-sup').html(district);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});
		
//-----------------------------------------------------------------------------Search---------------------------------------
		
		$("#btn-carri").on('click', function() {
			var kata  = $("#carri").val();
			//console.log(kata);
			window.location =  baseUrl+"supplier/index/src?search="+ kata;
		});
				
});