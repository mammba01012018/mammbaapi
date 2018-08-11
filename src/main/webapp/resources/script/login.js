$(document).ready(function() {
	$("#formoid").submit(function(event) {
		console.log('enter here.');
		$.ajax({
            type: "post",
            url: "/login",
            data: {
            	"userEmail": $('#userEmail').val(),
            	"password": $('#password').val()
  
                },
                contentType: "application/x-www-form-urlencoded",
            success: function(responseData, textStatus, jqXHR) {
                alert("data saved")
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });

	});
});