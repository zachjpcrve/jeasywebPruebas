<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
	 $(document).ready(function() { 	
	 	$("#optformulario").bind("hover", function (event, message1, message2) {
		 	$(this).css({fontWeight:"bolder",textDecoration:"none", fontFamily:"georgia, serif",background:"url(/ProgramaFinanciero/image/circle.gif)"});
		 	$("#optformulario b").css({left:"-22px", top:"100px",position:"absolute",display:"block",width:"100px", height:"100px",fontSize:"16px", color:"#63352c", background:"#fff"});
		 	$("#optformulario b span").css({display:"block", fontSize:"12px", color:"#888", fontWeight:"normal", marginTop:"15px"});
		});
		$("#optformulario").trigger("hover");
		
		$(".otro").hover(function(){
			$("#optformulario").css({background:"", fontWeight:"bolder" });
			$("#optformulario b").css({display:"none" });
		 	$("#optformulario b span").css({display:"none"});
		});
		$("#optformulario").hover( function () {
		 	$(this).css({fontWeight:"bolder",textDecoration:"none", fontFamily:"georgia, serif",background:"url(/ProgramaFinanciero/image/circle.gif)"});
		 	$("#optformulario b").css({left:"-22px", top:"100px",position:"absolute",display:"block",width:"100px", height:"100px",fontSize:"16px", color:"#63352c", background:"#fff"});
		 	$("#optformulario b span").css({display:"block", fontSize:"12px", color:"#888", fontWeight:"normal", marginTop:"15px"});
		});
		
	 });
	 
</script>
<div class="ui-state-default">

	<div id="circularMenu">
		
		<div class="hc-vertical hc-vertical-right">
			<div class="hc-panel-horizontal hc-panel-horizontal-titulo">
				<h1>Bienvenido al</h1>
				<h1>Sistema</h1>
			</div>
			<div class="hc-panel-horizontal hc-panel-horizontal-cuadro-destacados">						
							
								
						
			</div>
		</div>
		
		<div class="hc-vertical hc-vertical-left">
			
		</div>
		
	</div>

</div>
