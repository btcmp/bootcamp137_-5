$(function() {
	
	$('#outlet-list').DataTable({
		searching : false
	});

//-------------------------------------------------------------------------------------------------------Search----------------------------------------------
		
		$('#btn-cari').on('click', function() {
			var  word =  $('#cari').val();
			//console.log(word);
			window.location = baseUrl+"outlet/index/src?search="+word;
		});
	
//--------------------------------------------------------------------------------------------------------------cancel save-------------------------------------
		
		$('#btn-cancel-save').on('click', function() {
			$('#save-name').val('');
			$('#save-address').val('');
			$('#save-code').val('');
			$('#save-phone').val('');
			$('#save-email').val('');
			$('#save-pro').val('');
			$('#save-reg').val('');
			$('#save-dis').val('');
			
		})
		

//----------------------------------------------------------------------save---------------------------------------------------
		$('#add').on('click', function() {
			$('#saveout').modal();
		})
		$('#save-phone').inputmask({
			"mask": "+##-###-####-####",
		})
		$('#save-phone').on('change', function () {
			var value = $('#save-phone').val();
	        var numDecimal = value.replace(/[^0-9]+/g,'');
	        $('#save-phone-database').val(numDecimal);
	        $('#save-phone-database').trigger('change');
		});
		
		$('#btn-save').on('click', function(evt) {
			evt.preventDefault();
			var formsave = $('#form-save');
			var validsave = formsave.parsley().validate();
			var out = {
					name : $('#save-name').val(),
					address : $('#save-address').val(),
					provinsi :{
						id : $('#save-pro').val()
					},
					region : {
						id : $('#save-reg').val()
					},
					district : {
						id : $('#save-dis').val()
					},
					postalCode : $('#save-code').val(),
					phone : $('#save-phone-database').val(),
					email : $('#save-email').val(),
					active : 1
			}
		var dupname;
		var dupemail;
		var dupphone;
			$.ajax({
				url : baseUrl+"outlet/get-all-name",
				type : 'GET',
				contentType : 'application/json',
				success : function(listname) {
					dupname = 0;
					$.each(listname , function(index,nama) {
						if($('#save-name').val().toLowerCase() == nama.toLowerCase()){
							dupname = 1;
						}
					})
					if(dupname == 1){
						alert('outlate name has been used');
					}
					else{
						$.ajax({
							url : baseUrl+"outlet/get-all-email",
							type : 'GET',
							contentType : 'application/json',
							success : function(listemail) {
								dupemail = 0;
								$.each(listemail , function(index,email) {
									if($('#save-email').val().toLowerCase() == email.toLowerCase()){
										dupemail = 1;
									}
								});
								if(dupemail == 1){
									alert("email has been used");
								}
								else{
									$.ajax({
										url : baseUrl+"outlet/get-all-phone",
										tye : 'GET',
										contentType : 'application/json',
										success : function(listphone) {
											dupphone = 0;
											$.each(listphone, function(index, phone) {
												if (phone == $('#save-phone-database').val()) {
													dupphone = 1;
												}
											})
											if (dupphone==1) {
												alert('phone number has been used')
											}
											else{
												if(validsave == true){
													$.ajax({
														url : baseUrl+"outlet/save",
														type : 'POST',
														contentType : 'application/json',
														data : JSON.stringify(out),
														success : function(data) {
															//console.log(data);
															alert('save success');
															window.location = baseUrl+"outlet/index";
														},//succesajaxsave
														error : function() {
															alert('saving failed!');
														}//errorajaxname             
													});//ajaxname
												}
											}
										},
										error : function() {
											alert('error getting listphone');
										}
									})
								}
							},
							error : function() {
								alert('error getting email');
							}
						})
					};//elsedupname
				},//ajaxsuccessgetallname
				error : function() {
					alert('error getting list name');
				}
			})//ajaxgetallname
			
		
			
		});
		
		
//---------------------------------------------------------------------------------------------------------list region---------------------------------------------
		
		$('#save-pro').on('change', function() {
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
							
							$('#save-reg').html(region);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});		
		
		
							
//-------------------------------------------------------------------------------------------list district-------------------------------------------------
							
		$('#save-reg').on('change', function() {
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
							
							$('#save-dis').html(district);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});
				
		
//------------------------------------------------------------------------------edit----------------------------------------------------------------

		$('.editoutlet').on('click', function(evt) {
			evt.preventDefault();
			var id = $(this).attr('id');
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
			var idProv = out.provinsi.id;
			getRegionByProvinsi(idProv,out.region.id);
			var idReg = out.region.id;
			getDistrictByRegion(idReg,out.district.id);
			$('#edit-id').val(out.id);
			$('#edit-name-out').val(out.name);
			$('#edit-address-out').val(out.address);
			$('#edit-prov-out').val(out.provinsi.id);
			$('#edit-code-out').val(out.postalCode);
			$('#edit-createdOn-out').val(out.createdOn);
			$('#edit-createdBy-out').val(out.createdBy.id);
			$('#edit-phone-out').val(out.phone);
			$('#edit-phone-out-database').val(out.phone);
			$('#edit-email-out').val(out.email);
			$('#edit-active-out').val(out.active);
		};
		
		function getRegionByProvinsi(idProv,regid) {
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
					
					$('#edit-reg-out').html(region);
					$('#edit-reg-out').val(regid);
				},
				error : function() {
					alert("failed");
				}
			});
		};
		
		
		function getDistrictByRegion(idReg,disid) {
			$.ajax({
				url : baseUrl+"/kecamatan/get-kecamatan?id="+idReg,
				type : "GET",
				success : function(kecamatan) {
					var district = [];
					var dist = '<option value=/"/">District</option>';
					district.push(dist);
					$(kecamatan).each(function(index, data) {
						dist = "<option value=\""+data.id+"\">"+data.name+"</option>";
						district.push(dist);
					})
					$('#edit-dis-out').html(district);
					$('#edit-dis-out').val(disid);
					
					
				},
				error : function() {
					
				}
			})
		}
		
//------------------------------------------------------------------------------------------------------nonactive-------------------------------------------
		
		$('#edit-phone-out').inputmask({
			"mask": "+##-###-####-####",
		})
		$('#edit-phone-out').trigger('change');

	    $('#edit-phone-out').on('change', function () {
	        var value = $(this).val();
	        var numDecimal = value.replace(/[^0-9]+/g,'');
	        $('#edit-phone-out-database').val(numDecimal);
	        $('#edit-phone-out-database').trigger('change');
	    });
		
		$('#btn-nonactive').on('click', function() {
			var formedit = $('#form-edit');
			var validedit = formedit.parsley().validate();
			var outlet = {
					id : $('#edit-id').val(),
					name : $('#edit-name-out').val(),
					address : $('#edit-address-out').val(),
					provinsi : {
						id : $('#edit-prov-out').val()
					},
					region : {
						id : $('#edit-reg-out').val()
					},
					district : {
						id : $('#edit-dis-out').val()
					},
					createdOn : $('#edit-createdOn-out').val(),
					createdBy : {
						id : $('#edit-createdBy-out').val()
					},
					postalCode : $('#edit-code-out').val(),
					phone : $('#edit-phone-out-database').val(),
					email : $('#edit-email-out').val(),
					active : $('#edit-active-out').val(),
			}
			console.log(outlet);
			if(validedit == true){
				$.ajax({
					url : baseUrl+"outlet/deactive",
					type : 'PUT',
					data : JSON.stringify(outlet),
					contentType : 'application/json',
					success : function(data) {
						alert('Outlet non-active');
						window.location = baseUrl+"outlet/index";
					},
					error : function() {
						alert('update failed!!');
					}
				});
			}
		});
		
//------------------------------------------------------------------------------------------------update----------------------------------------------
		
		$('#btn-update').on('click', function() {
			var formedit = $('#form-edit');
			var validedit = formedit.parsley().validate();
			var outlet = {
					id : $('#edit-id').val(),
					name : $('#edit-name-out').val(),
					address : $('#edit-address-out').val(),
					provinsi : {
						id : $('#edit-prov-out').val()
					},
					region : {
						id : $('#edit-reg-out').val()
					},
					district : {
						id : $('#edit-dis-out').val()
					},
					createdOn : $('#edit-createdOn-out').val(),
					createdBy : {
						id : $('#edit-createdBy-out').val()
					},
					postalCode : $('#edit-code-out').val(),
					phone : $('#edit-phone-out-database').val(),
					email : $('#edit-email-out').val(),
					active : $('#edit-active-out').val(),
			}
			console.log(outlet);
			var dupnameedeit;
			var dupemailedit;
			var dupphoneedit;
			$.ajax({
				url : baseUrl+"outlet/get-all",
				type : 'GET',
				contentType : 'application/json',
				success : function(listoutlet) {
					dupnameedit = 0;
					$.each(listoutlet, function(index, outlet) {
						if($('#edit-id').val() != outlet.id){
							if ($('#edit-name-out').val() == outlet.name){
								dupnameedeit = 1;
							}
							else if ($('#edit-email-out').val() == outlet.email){
								dupemailedit = 1;
							}
							else if ($('#edit-phone-out-database').val() == outlet.phone){
								dupphoneedit = 1;
							}
						}
					});
					if (dupnameedeit==1){
						alert('name has been used');
					}
					else if(dupemailedit==1){
						alert('email has been used');
					}
					else if(dupphoneedit==1){
						alert('phone number has been used');
					}
					else{
						if(validedit == true){
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
						}
					}
				},
				error : function() {
					alert('error getting data outlet');
				}
			})
			
		});
		
//----------------------------------------------------------------------------------------------edit region------------------------------------------
		
		$('#edit-prov-out').on('change', function() {
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
							
							$('#edit-reg-out').html(region);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});
		
//-------------------------------------------------------------------------------------------list district-------------------------------------------------
		
		$('#edit-reg-out').on('change', function() {
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
							
							$('#edit-dis-out').html(district);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});
		
});