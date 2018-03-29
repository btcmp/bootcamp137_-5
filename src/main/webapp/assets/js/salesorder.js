var customer = {};
$(function() {

//-------------------------------------------------------------------------------search item---------------------------------------------
	
	var options = {
			url : baseUrl+"items/get-all-variant",
			getValue : function(response) {
				return  response.variantId.itemId.name+" - "+response.variantId.name;
			},
			list :{
				match :{
					enabled : true
				},
				onClickEvent : function() {
					var value = $('#nama-item').getSelectedItemData();
					$('#table-item').append(
							"<tr><td>"+value.variantId.itemId.name+" - "+value.variantId.name+"</td><td>"+value.variantId.price+"</td></tr>"
					);
					$('#nama-item').val('');
				}
			}
			
			
	};
	$('#nama-item').easyAutocomplete(options);
	
//-------------------------------------------------------------------------------search cust----------------------------------------
	
	$('#cari-customer').on('click', function() {
		//alert("aa");
		var kata = $('#name-cust').val();
		//console.log(kata);
		$.ajax({
			url : baseUrl+"salesorder/index/src-customer?search="+kata,
			type : 'GET',
			success : function(cust) {
				createTableCustomer(cust);
			},
			error : function(nf) {
				alert("customer not found!");
				notFound(nf);
			},
			dataType : 'json',
		});
	});
	
	function createTableCustomer(cust) {
		var newTable = $('#table-customer');
		var renderData = "";
		$.each(cust, function(index, custom) {
			//console.log(index, custom);
			renderData += "<tr>";
			renderData += "<td>";
			renderData += custom.name;
			renderData += "</td>";
			renderData += "<td>";
			renderData += custom.email;
			renderData += "</td>";
			renderData += "<td>";
			renderData += custom.phone;
			renderData += "</td>";
			renderData += "<td>";
			renderData += "<a href='#' class='pilih-customer btn btn-primary'>pilih</a>"
			renderData += "</tr>";
		});
		var tbody = newTable.find("tbody");
		tbody.empty();
		tbody.append(renderData);
		//console.log(tbody);
	};
	
	function notFound(nf) {
		var newTablenf = $('#table-customer');
		var tbody = newTablenf.find("tbody");
		tbody.empty();
	}
		$(document).on('click', '.pilih-customer', function() {
			//alert("aa");
			var element = $(this).parent().parent();
			//console.log(element);
			var td = element.find("td").eq(0).text();
			//console.log(td);
			$('#customer').text(td);
			customer = {
					name : element.find("td").eq(0).text(),
					email : element.find("td").eq(1).text(),
					phone : element.find("td").eq(2).text()
			}
			//console.log(customer);
			
		});
	
	
	
//------------------------------------------------------------------------------choose------------------------------------------------	
	$('#customer').on('click', function() {
		$('#choose-cust').modal();
	})

//----------------------------------------------------------------------save---------------------------------------------------
		$('#add-new').on('click', function() {
			$('#new-cust').modal();
		})
		$('#btn-simpan').on('click', function(evt) {
			evt.preventDefault();
			var customer = {
					name : $('#save-name-cust').val(),
					email : $('#save-email-cust').val(),
					phone : $('#save-phone-cust').val(),
					dob : $('#save-dob-cust').val(),
					address : $('#save-address-cust').val(),
					provinsi : {
						id : $('#save-pro-cust').val()
					},
					region : {
						id : $('#save-reg-cust').val()
					},
					district : {
						id : $('#save-dis-cust').val()
					},
					active : 1
			}
			console.log(customer);
		
		
			$.ajax({
				url : baseUrl+"customer/save",
				type : 'POST',
				contentType : 'application/json',
				data : JSON.stringify(customer),
				success : function(data) {
					//console.log(data);
					alert('save success');
					window.location = baseUrl+"salesorder/index";
				},
				error : function() {
					alert('saving failed!');
				}                              
			});
			
		});
		
		
//---------------------------------------------------------------------------------------------------------list region---------------------------------------------
		
		$('#save-pro-cust').on('change', function() {
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
							
							$('#save-reg-cust').html(region);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});		
		
		
							
//-------------------------------------------------------------------------------------------list district-------------------------------------------------
							
		$('#save-reg-cust').on('change', function() {
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
							
							$('#save-dis-cust').html(district);
					}, error : function(){
						alert('get failed');
					}
				});
			}
		});
				
		
//------------------------------------------------------------------------------pilih cust----------------------------------------------------------------

		/*$('.pilih-customer').on('click', function() {
			//alert("terpilih");
		});*/

});