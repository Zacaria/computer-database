<%@ taglib prefix="cdb" tagdir="/WEB-INF/tags"%>
<cdb:default_layout>
	<jsp:attribute name="body_area">

	<section id="main">
		<div class="container">
			<div class="jumbotron">
				<h1>Error 500</h1>
				${errors.printStackTrace()}
			</div>
		</div>
	</section>
	</jsp:attribute>
</cdb:default_layout>