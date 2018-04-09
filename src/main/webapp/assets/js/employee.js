$(document).ready(function() {
	
	$('#employee-table').DataTable({
		searching : false,
		paging : false
	});
	
    $('#user-form').hide();
    $('#user-form-edit').hide();
    
    $('#save-hasAccount').on('click',function(){
		if(this.checked){
			$('#user-form').show(500);
		} else {
			$('#user-form').hide(500);
		}
    });
    
    $('#edit-hasAccount').on('click',function(){
		if(this.checked){
			$('#user-form-edit').show(500);
		} else {
			$('#user-form-edit').hide(500);
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
    	$('#btn-save').attr('listOutlet',JSON.stringify(listOutlet));
    	$('#modal-outlet').modal('hide');
    });
    
    $('#btn-save').on('click', function(evt){
    	evt.preventDefault();
    	var formSave = $('#form-save-emp');
    	var validSave = formSave.parsley().validate();
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
    	
    	var duplicateUsername;
    	var duplicateEmail;
    	$.ajax({
    		url : baseUrl+'employee/get-all-username',
   			type : 'GET',
   			contentType : 'application/json',
   			success : function(listUsername){
   				duplicateUsername = 0;
   				$.each(listUsername, function(index, username){
   					if($('#save-username').val() == username){
   						duplicateUsername = 1;
   					}
   				});
   				
   				if(duplicateUsername == 1){
   					alert('username has been used');
   				} else {
   			    	$.ajax({
   			    		url : baseUrl+'employee/get-all-email',
   			   			type : 'GET',
   			   			contentType : 'application/json',
   			   			success : function(listEmail){
   			   				duplicateEmail = 0;
   			   				$.each(listEmail, function(index, email){
   			   					if($('#save-email').val() == email){
   			   						duplicateEmail = 1;
   			   					}
   			   				});
   			   				
	   			   			if(duplicateEmail == 1){
	   	   						alert('email has been used');
	   	   					} else {
	   	   						if(validSave==true){
	   	   			  		   		$.ajax({
	   	   			  		       		url : baseUrl+'employee/save-emp',
	   	   			  	      			type : 'POST',
	   	   			  	      			contentType : 'application/json',
	   	   			  	     			data : JSON.stringify(employee),
	   	   			  		       		success : function(data){
	   	   			  	    				alert('save emp success');
	   	   			  	       				window.location = baseUrl+'employee';
	   	   			  	       			},
	   	   			  		        	error : function(){
	   	   			  		       			alert('save emp failed');
	   	   			  		       		}
	   	   			  		       	});
	   	   			  		    }
	   	   					}
   			   			},
   			   			error : function(){
   			   				alert('checking email failed');
   			   			}
   			    	});
   				}
   			},
   			error : function(){
   				alert('checking username failed');
   			}
    	});
    	
    });
    
    $('.deactivate').on('click', function(evt){
    	evt.preventDefault();
    	var id = $(this).attr('id');
    	console.log(id);
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
				window.location = baseUrl+'employee';
			},
			error : function(){
				alert('deactivate failed');
			}
    	});
    })
    
    $('.edit').on('click', function(evt){
    	evt.preventDefault();
    	var id = $(this).attr('id');
    	$.ajax({
    		url : baseUrl+"employee/get-employee/"+id,
    		type : 'GET',
    		contentType : 'application/json',
    		success : function(selectedEmployee) {
    			$('#modal-edit').modal();
    			fillEditField(selectedEmployee);
			},
			error : function(){
				alert('error getting data');
			}
    	});
    	
    });
    
    function fillEditField(selectedEmployee){
    	clearEditForm();
    	$('#edit-id').val(selectedEmployee.id);
    	$('#edit-fname').val(selectedEmployee.firstName);
    	$('#edit-lname').val(selectedEmployee.lastName);
    	$('#edit-email').val(selectedEmployee.email);
    	$('#edit-title').val(selectedEmployee.title);
    	
    	if(selectedEmployee.haveAccount==true){
        	$('#edit-hasAccount').prop('checked',true);
    	} else {
    		$('#edit-hasAccount').prop('checked',false);
    	}
    	
    	if(selectedEmployee.user != null){
    		$('#edit-role').val(selectedEmployee.user.role.id);
    		$('#edit-user-id').val(selectedEmployee.user.id);
        	$('#edit-username').val(selectedEmployee.user.username);
        	$('#edit-password').val(selectedEmployee.user.password);
        	$('#btn-exec-edit').attr('prevUsername',selectedEmployee.user.username);
    	}
    	
    	$.each(selectedEmployee.listOutlet, function(index, outlet){
    		$.each($('#list-edit-outlet > tr > td > input[type="checkbox"]'), function(){
    			if($(this).attr('id') == outlet.id){
    				$(this).prop('checked', true);
    			}
    		});
    	});
    	
    	var listOutlet=[];
    	$('#list-edit-outlet').find('input[type="checkbox"]:checked').each(function(){
    		listOutlet.push({id : $(this).attr('id')});
    	});
    	$('#btn-exec-edit').attr('listOutlet',JSON.stringify(listOutlet));
    	$('#btn-exec-edit').attr('prevEmail',selectedEmployee.email);
    	
    }
    
    function clearEditForm(){
    	$('.cancelable').val('');
    	$.each($('#list-edit-outlet > tr > td > input[type="checkbox"]'), function(){
			$(this).prop('checked', false);
		});
    }
    
    $('#btn-assign-outlet-edit').on('click', function(){
    	$('#modal-edit-outlet').modal();
    	$('#modal-edit').modal('hide');
    });
    
    $('#btn-edit-outlet').on('click', function(){
    	var listOutlet=[];
    	$('#list-edit-outlet').find('input[type="checkbox"]:checked').each(function(){
    		listOutlet.push({id : $(this).attr('id')});
    	});
    	$('#btn-exec-edit').attr('listOutlet',JSON.stringify(listOutlet));
    	$('#modal-outlet').modal('hide');
    	
    	$('#modal-edit').modal();
    	$('#modal-edit-outlet').modal('hide');
    });
    
    $('#btn-exec-edit').on('click', function(evt){
    	evt.preventDefault();
    	var formEdit = $('#form-edit-emp');
    	var validSave = formEdit.parsley().validate();
    	var account = $('#edit-hasAccount').is(':checked') ? true : false;
    	var user = null;
    	var prevEmail = $(this).attr('prevEmail');
    	var prevUsername = $(this).attr('prevUsername');
    	
    	try{
    		var listOutlet = JSON.parse($(this).attr('listOutlet'));
    	} catch (ex){
    		console.error(ex);
    	}
    	
    	if(account==true){
    		user = {
    			username : $('#edit-username').val(),
    			password : $('#edit-password').val(),
    			role : {
    				id : $('#edit-role').val()
    			},
    			active : true
    		}
    	}
    	
    	var employee = {
    		id : $('#edit-id').val(),
        	firstName : $('#edit-fname').val(),
        	lastName : $('#edit-lname').val(),
       		email : $('#edit-email').val(),
       		title : $('#edit-title').val(),
       		listOutlet : listOutlet,
       		haveAccount : account,
       		active : true,
       		user : user
        }
    	
    	var duplicateUsername;
    	var duplicateEmail;
    	$.ajax({
    		url : baseUrl+'employee/get-all-username',
   			type : 'GET',
   			contentType : 'application/json',
   			success : function(listUsername){
   				duplicateUsername = 0;
   				$.each(listUsername, function(index, username){
   					if($('#edit-username').val() == username){
   						if($('#edit-username').val() != prevUsername){
   							duplicateUsername = 1;
   						}
   					}
   				});
   				if(duplicateUsername == 1){
   					alert('username has been used');
   				} else {
   			    	$.ajax({
   			    		url : baseUrl+'employee/get-all-email',
   			   			type : 'GET',
   			   			contentType : 'application/json',
   			   			success : function(listEmail){
   			   				duplicateEmail = 0;
   			   				$.each(listEmail, function(index, email){
   			   					if($('#edit-email').val() == email){
   			   						if($('#edit-email').val() != prevEmail){
   			   							duplicateEmail = 1;
   			   						}
   			   					}
   			   				});
	   			   			if(duplicateEmail == 1){
	   	   						alert('email has been used');
	   	   					} else {
	   	   						if(validSave==true){
	   	   							$.ajax({
	   	   								url : baseUrl+'employee/edit-emp',
	   	   								type : 'PUT',
	   	   								contentType : 'application/json',
	   	   								data : JSON.stringify(employee),
	   	   								success : function(data){
	   	   									alert('edit emp success');
	   	   									window.location = baseUrl+'employee';
	   	   								},
	   	   								error : function(){
	   	   									alert('edit emp failed');
	   	   								}
	   	   							});
	   	   			  		    }
	   	   					}
   			   			},
   			   			error : function(){
   			   				alert('checking email failed');
   			   			}
   			    	});
   				}
   			},
   			error : function(){
   				alert('checking username failed');
   			}
    	});
    });
    
} );