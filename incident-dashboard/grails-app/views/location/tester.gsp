<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title>Incident Dashboard</title>
    <g:javascript library="prototype" />
    <style type="text/css">
  	html { height: 100% }
  	body { height: 100%; margin: 0px; padding: 0px }
  	#map_canvas { height: 100% }
	</style>
    <script type="text/javascript"
    	src="http://maps.google.com/maps/api/js?sensor=true">
	</script>
    
    <script type="text/javascript">


  function incident (id, desc, incident_time, addr, lat, lng){
	  this.id=id;
	  this.desc=desc;
	  this.incident_time=incident_time;
	  this.addr=addr;
	  this.lat=lat;
	  this.lng=lng;
	  	
	  }

  function addMarker (incident){
	$('map_canvas').insert ('<div>' + incident.id + ', ' + incident.desc + ', ' + incident.incident_time + ', '+incident.addr+', '+incident.lat+', '+incident.lng +'</div>');
	  }
  
  function load() {
  	var url = "${createLink(controller:'location', action:'incidents')}";
  	new Ajax.Request(url, {
  	     method: 'get',
  	     onSuccess: function(transport) {
  		    var json = transport.responseText.evalJSON(true);
  		    for (i = 0; i < json.incidents.length; i++) {
  			 	var myIncident = new incident(json.incidents[i].id,
  		  			 	json.incidents[i].description, json.incidents[i].incident_time,
  		  			 	json.incidents[i].formatted_address, json.incidents[i].lat, json.incidents[i].lng);
	  			addMarker(myIncident);
  			}
  	     }
  	  });
  	  	  
  }
    
    </script>
  </head>
  <body onload="load()">
  	<div id="map_canvas" style="width:100%; height:100%">
  	</div>
  </body>
</html>
