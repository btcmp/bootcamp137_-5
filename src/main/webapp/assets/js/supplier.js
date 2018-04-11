$(function() {

	
		$('#supplier-list').DataTable({
			searching : false
		});

//----------------------------------------------------------------------save---------------------------------------------------
		$('#new').on('click', function() {
			$('#savesup').modal();
		})
		$('#btn-save-sup').on('click', function(evt) {
			evt.preventDefault();
			var formsave = $('#form-save');
			var validsave = formsave.parsley().validate();
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
			var dupname;
			var dupemail;
			$.ajax({
				url : baseUrl+"supplier/get-all-name",
				type : 'GET',
				contentType : 'application/json',
				success : function(listname) {
					dupname=0;
					$.each(listname, function(index, name) {
						if($('#save-name-sup').val() == name){
							dupname=1;
						}
					});
					if(dupname==1){
						alert('name has been used');
					}
					else{
						$.ajax({
							url : baseUrl+"supplier/get-all-email",
							type : 'GET',
							contentType : 'application/json',
							success : function(listemail) {
								dupemail=0;
								$.each(listemail, function(index, email) {
									if ($('#save-email-sup').val() == email) {
										dupemail=1;
									}
								})
								if (dupemail==1) {
									alert('email has been used')
								}
								else{
									if(validsave==true){
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
									}
								}
							},
							error : function() {
								alert('error getting data')
							}
						})
					}
				},
				error : function() {
					alert('error getting data');
				}
			})
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
			$.ajax({
				url : baseUrl+"supplier/get-id/"+id,
				type : 'GET',
				success : function(sup) {
					//console.log(sup);
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
			var idprov = sup.provinsi.id;
			getRegionByProvinsi(idprov,sup.region.id);
			var idreg = sup.region.id;
			getDistrictByRegion(idreg,sup.district.id);
			$('#edit-id-sup').val(sup.id);
			$('#edit-name-sup').val(sup.name);
			$('#edit-address-sup').val(sup.address);
			$('#edit-prov-sup').val(sup.provinsi.id);
			$('#edit-code-sup').val(sup.postalCode);
			$('#edit-phone-sup').val(sup.phone);
			$('#edit-email-sup').val(sup.email);
		};
		
		function getRegionByProvinsi(idProv,regionid) {
			$.ajax({
				url : baseUrl+"/region/get-region?id="+idProv,
				type : "GET",
				success : function(regionss) {
					var region = [];
					var reg = '<option value=/"/">Region</option>';
					region.push(reg);
					$(regionss).each(function(index, data2) {
						var reg = "<option value=\""+data2.id+"\">"+data2.name+"</option>";
						region.push(reg);
					})
					
					$('#edit-reg-sup').html(region);
					$('#edit-reg-sup').val(regionid);
				},
				error : function() {
					alert("failed");
				}
			})
		}
		
		function getDistrictByRegion(idReg,disid) {
			$.ajax({
				url : baseUrl+"/kecamatan/get-kecamatan?idreg="+idReg,
				type : "GET",
				success : function(districtssss) {
					var district = [];
					var dis = '<option value=/"/">District</option>';
					district.push(dis);
					$(districtssss).each(function(index, data2) {
						dis = "<option value=\""+data2.id+"\">"+data2.name+"</option>";
						district.push(dis);
					})
					
					$('#edit-dis-sup').html(district);
					$('#edit-dis-sup').val(disid);
					
				},
				error : function() {
					alert("failed");
				}
			})
		}
		
//----------------------------------------------------------------------------------------------------nonactive-------------------
		$('#btn-nonactive').on('click', function() {
			var formedit = $('#form-edit');
			var validedit = formedit.parsley().validate();
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
			
			if(validedit==true){
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
			}
			
		});
		
//-------------------------------------------------------------------------------------------------------------update----------
		
		$('#btn-update-sup').on('click', function() {
			var formedit = $('#form-edit');
			var validedit = formedit.parsley().validate();
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
			var dupnameedit;
			var dupemailedit;
			$.ajax({
				url : baseUrl+"supplier/get-all",
				type : 'GET',
				contentType : 'application/json',
				success : function(listsupplier) {
					dupnameedit=0;
					$.each(listsupplier, function(index, supplier) {
						if($('#edit-id-sup').val() !== supplier.id){
							if ($('#edit-name-sup').val() == supplier.name) {
								dupnameedit=1;
							}
							else if ($('#edit-email-sup').val() == supplier.email){
								dupemailedit=1;
							}
						}
					})
					if (dupnameedit==1) {
						alert('name has been used')
					}
					else if (dupemailedit==1){
						alert('email has been used')
					}
					else{
						if(validedit==true){
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
						}
					}
				},
				error : function() {
					alert('error getting list')
				}
			})	
		});
		
//----------------------------------------------------------------------------------------edit region--------------------
		
		$('#edit-prov-sup').on('change', function() {
			var id = $(this).val();
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
							
							$('#edit-reg-sup').html(region);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});
		
//--------------------------------------------------------------------------------------------edit district---------------
		
		$('#edit-reg-sup').on('change', function() {
			var id = $(this).val();
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
			window.location =  baseUrl+"supplier/index/src?search="+ kata;
		});
		
//-------------------------------------------------------------------------------print-----------------------------------------
		
		$('#print-supp').on('click', function() {
			window.location =baseUrl+"generate/suplier";
		})
				
});