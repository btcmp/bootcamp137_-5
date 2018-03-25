$(document).ready(function() {
	
	$('#employee-table').DataTable({
		searching : false,
		paging : false
	});
	
    $('#user-form').hide();
    
    $('#save-hasAccount').on('click',function(){
		if(this.checked){
			$('#user-form').show(500);
		} else {
			$('#user-form').hide(500);
		}	
    });
    
    $('#btn-assign-outlet').on('click', function(){
    	$('#modal-outlet').modal();
    });
    
    $('#btn-cancel').on('click', function(){
    	$('.cancelable').val('');
    });
    
    $('#btn-select-outlet').on('click', function(){
    	var listOutlet=[];
    	$('#list-outlet').find('input[type="checkbox"]:checked').each(function(){
    		listOutlet.push({id : $(this).attr('id')});
    	});
    	console.log(JSON.stringify(listOutlet));
    	$('#btn-save').attr('listOutlet',JSON.stringify(listOutlet));
    	$('#modal-outlet').modal('hide');
    });
    
    $('#btn-save').on('click', function(evt){
    	evt.preventDefault();
    	var account = $('#save-hasAccount').is(':checked') ? true : false;
    	var user = null;
    	try{
    		var listOutlet = JSON.parse($(this).attr('listOutlet'));
    	} catch (ex){
    		console.error(ex);
    	}
    	
    	if(account==true){
    		user = {
    			username : $('#save-username').val(),
    			password : $('#save-password').val(),
    			role : {
    				id : $('#save-role').val()
    			},
    			active : true
    		}
    	}
    	
    	var employee = {
        	firstName : $('#save-fname').val(),
        	lastName : $('#save-lname').val(),
       		email : $('#save-email').val(),
       		title : $('#save-title').val(),
       		listOutlet : listOutlet,
       		haveAccount : account,
       		active : true,
       		user : user
        }
    	console.log(employee);
    	$.ajax({
    		url : baseUrl+'employee/save-emp',
   			type : 'POST',
   			contentType : 'application/json',
   			data : JSON.stringify(employee),
    		success : function(data){
   				alert('save emp success');
   				console.log(employee);
   			},
    		error : function(){
   				alert('save emp failed');
   			}
   		});
    });
    
    $('.deactivate').on('click', function(evt){
    	evt.preventDefault();
    	var id = $(this).attr('id');
    	$.ajax({
    		url : baseUrl+'employee/get-employee/'+id,
   			type : 'GET',
   			contentType : 'application/json',
    		success : function(selectedEmployee){
    			$('#modal-deactivate').modal();
    			$('#btn-exec-deactivate').attr('sel',JSON.stringify(selectedEmployee));
   			},
    		error : function(){
   				alert('error getting data');
   			}
   		});
    });
    
    $('#btn-exec-deactivate').on('click', function(evt){
    	evt.preventDefault();
    	var e = $(this).attr('sel');
    	$.ajax({
    		url : baseUrl+'employee/deactivate',
    		type : 'PUT',
    		contentType : 'application/json',
    		data : e,
    		success : function(data){
				alert('deactivate success');
			},
			error : function(){
				alert('deactivate failed');
			}
    	});
    })
    
    
} );