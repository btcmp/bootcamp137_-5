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
    
    $('.deactivate').on('click', function(){
    	$('#modal-deactivate').modal();
    });
    
    $('#btn-save').on('click', function(evt){
    	evt.preventDefault();
    	var account = $('#save-hasAccount').is(':checked') ? true : false;
    	var user = null;
    	
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
       		haveAccount : account,
       		active : true,
       		user : user
        }
    	
    	$.ajax({
    		url : baseUrl+'employee/save-emp',
   			type : 'POST',
   			contentType : 'application/json',
   			data : JSON.stringify(employee),
    		success : function(data){
   				alert('save emp success');
   			},
    		error : function(){
   				alert('save emp failed');
   			}
   		});
    });
    
    /*$('btn-select-outlet').on('click', function(){
    	var listOutlet = [];
    	$.each($('#outlet-table'), function(){
    		
    	})
    });*/
    
    
    
    
    
} );