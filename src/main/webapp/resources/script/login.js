$(document).ready(function() {
	$("#submitBtn").on("click",function(event) {
		console.log('enter here.');
		$.ajax({
            type: "post",
            url: "/login",
            data: {
            	"userEmail": $('#userEmail').val(),
            	"password": $('#password').val()
  
                },
                contentType: "application/x-www-form-urlencoded",
            success: function(responseData, textStatus, jqXHR){
                if (responseData === 'success') {
                	$("#result").text('Login Successful');
                } else {
                	$("#result").text('Login Failed.');
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });

	});
});