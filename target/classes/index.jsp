<html>
<head>
<link rel="stylesheet" href="/resources/js/jquery-2.1.4.js"
	type="text/css" />
<script type="text/javascript">

$(document).ready(function() {
    $.ajax({
    	method: "GET",
        url: "/rzrq/selectRecent"
    }).then(function(data) {
       $("#txt").append(data.length);
    });
});
</script>
</head>
<body>
	<h2 id='txt'>Hello World!</h2>

</body>
</html>
