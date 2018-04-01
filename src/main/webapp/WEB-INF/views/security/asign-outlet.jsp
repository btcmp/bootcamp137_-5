<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="baseUrl" value="${pageContext.request.contextPath}/"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | Log in</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="${baseUrl }assets/node_modules/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${baseUrl }assets/node_modules/font-awesome/css/font-awesome.min.css">

	 <!-- Ionicons -->
  <link rel="stylesheet" href="${baseUrl }assets/node_modules/ionicons/dist/css/ionicons.min.css">
  
  <style type="text/css">
input.parsley-success,
select.parsley-success,
textarea.parsley-success {
  color: #468847;
  background-color: #DFF0D8;
  border: 1px solid #D6E9C6;
}

input.parsley-error,
select.parsley-error,
textarea.parsley-error {
  color: #B94A48;
  background-color: #F2DEDE;
  border: 1px solid #EED3D7;
}

.parsley-errors-list {
  margin: 2px 0 3px;
  padding: 0;
  list-style-type: none;
  font-size: 0.9em;
  line-height: 0.9em;
  opacity: 0;

  transition: all .3s ease-in;
  -o-transition: all .3s ease-in;
  -moz-transition: all .3s ease-in;
  -webkit-transition: all .3s ease-in;
}

.parsley-errors-list.filled {
  opacity: 1;
}
.easy-autocomplete{
  width:100% !important
}

.easy-autocomplete input{
  width: 100%;
}

.form-wrapper{
  width: 500px;
}
</style>
  
  <!-- Theme style -->
  <link rel="stylesheet" href="${baseUrl }assets/dist/css/AdminLTE.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="../../index2.html"><b>Admin</b>LTE</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">Please Select Outlet</p>

    <form action="<c:url value='welcome' />" id="select-outlet" data-parsley-validate="" method="post">
      <div class="form-group has-feedback">
      	<c:forEach items="${employee.listOutlet}" var ="outlet">
			<div class="radio">
			  <label><input type="radio" value="${outlet.id}" name="outlet" required>${outlet.name}</label>
			</div>
		</c:forEach>
		<input type="hidden" name="id" value="${employee.user.id}"/>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
             </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Select</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 3 -->
<script src="${baseUrl }assets/node_modules/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${baseUrl }assets/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- ParsleyJS -->
<script src="${baseUrl }assets/node_modules/parsleyjs/dist/parsley.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var ok = false;
	$('#select-outlet').parsley().on('field:validated', function() {
	    ok = $('.parsley-error').length === 0;
	    
	  })
	  .on('form:submit', function() {
	    if(ok){
	    	return true;
	    }
		  return false; // Don't submit form for this demo
	  });
});
</script>
</body>
</html>
