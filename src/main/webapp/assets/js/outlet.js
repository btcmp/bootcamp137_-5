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
	

//----------------------------------------------------------------------save---------------------------------------------------
		$('#add').on('click', function() {
			$('#saveout').modal();
		})
		$('#btn-save').on('click', function(evt) {
			evt.preventDefault();
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
					phone : $('#save-phone').val(),
					email : $('#save-email').val(),
					active : 1
			}
			//console.log(out);
		
		
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
			//console.log(id);
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
			$('#edit-phone-out').val(out.phone);
			$('#edit-email-out').val(out.email);
		};
		
		function getRegionByProvinsi(idProv,regid) {
			$.ajax({
				url : baseUrl+"/region/get-region?id",idProv,
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
		
		$('#btn-nonactive').on('click', function() {
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
					postalCode : $('#edit-code-out').val(),
					phone : $('#edit-phone-out').val(),
					email : $('#edit-email-out').val(),
					active : 0
			}
			
			$.ajax({
				url : baseUrl+"outlet/update",
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
		});
		
//------------------------------------------------------------------------------------------------update----------------------------------------------
		
		$('#btn-update').on('click', function() {
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
					postalCode : $('#edit-code-out').val(),
					phone : $('#edit-phone-out').val(),
					email : $('#edit-email-out').val(),
					active : 1
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