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

  var markersArray = [];
    
  function incident (id, desc, incident_time, addr, lat, lng){
	  this.id=id;
	  this.desc=desc;
	  this.incident_time=incident_time;
	  this.addr=addr;
	  this.lat=lat;
	  this.lng=lng;
	  	
	  }

  function addMarker (incident, map){
	  var myLatlng = new google.maps.LatLng(incident.lat, incident.lng);
	  
	  var marker = new google.maps.Marker({
	      position: myLatlng,
	      animation: google.maps.Animation.BOUNCE,
	      map: map
	  });
	  marker.setTitle(incident.id.toString());
	  markersArray.push(marker);
	  attachInfo(marker, incident, map);
  }


  function attachInfo(marker, incident, map) {
	 var contentString ='<div id="'+incident.id+'">' +
	 incident.desc + '<br>' +
	 incident.incident_time + '<br>' +
	 incident.addr +
	 '</div>';
	 var infowindow = new google.maps.InfoWindow(
	      { content: contentString,
	        size: new google.maps.Size(50,50)
	      });
	  
	 google.maps.event.addListener(marker, 'mousedown', function() {
		 	if (marker.getAnimation() != null) {
			    marker.setAnimation(null);
			} 
		    infowindow.open(map,marker);
		  });

	 google.maps.event.addListener(marker, 'mouseup', function() {
		    infowindow.close(map,marker);
		  });
	}


  function markerRepeated(marker, json){
	  for (i = 0; i < json.incidents.length; i++){
		if (marker.getTitle() == json.incidents[i].id.toString()){
			return true;
		}
	  }
	  return false;
	}

  function markerAlreadyExists(id){
	  if (markersArray && markersArray.length > 0) {
	      for (i=0; i < markersArray.length; i++) {
		    
	        if (markersArray[i].getTitle()==id) {
		        return true;
	        }
	      }
	   }
	   return false;
  }
  
//Removes the overlays from the map, but keeps them in the array
  function clearOverlays(json) {
    if (markersArray && markersArray.length > 0) {
      for (i =0; i < markersArray.length; i++) {
        if (markerRepeated(markersArray[i], json) == false){
        	markersArray[i].setMap(null);
        	markersArray[i] = null;
            }
      }
      markersArray.compact();
    }
  }


  
  function load() {
      var locCenterPoint = new google.maps.LatLng(${map.lat}, ${map.lng});
      var latLngBound = new google.maps.LatLngBounds(new google.maps.LatLng(${map.viewport_sw_lat}, ${map.viewport_sw_lng}),
    		  new google.maps.LatLng(${map.viewport_ne_lat}, ${map.viewport_ne_lng}));
      var myOptions = {
          zoom: 15,
          center: locCenterPoint,
          mapTypeId: google.maps.MapTypeId.HYBRID
        };

      var map = new google.maps.Map(document.getElementById("map_canvas"),
    	        	myOptions);
  	  map.fitBounds(latLngBound);

  	  var url = "${createLink(controller:'location', action:'incidents')}";
  	  new Ajax.PeriodicalUpdater('', url, {
  	  	 frequency: 20,
  	  	 decays: 0,
  	  	 parameters: {sw_lat:${map.viewport_sw_lat}, sw_lng:${map.viewport_sw_lng},
  	  	  	 ne_lat:${map.viewport_ne_lat}, ne_lng:${map.viewport_ne_lng}},
  	     method: 'get',
  	     onSuccess: function(transport) {
  		    var json = transport.responseText.evalJSON(true);

  		    clearOverlays(json);
  		    
  		    for (i = 0; i < json.incidents.length; i++) {
  	  		   
				if (!markerAlreadyExists(json.incidents[i].id.toString())){
					
  			 		var myIncident = new incident(json.incidents[i].id,
  		  			 	json.incidents[i].description, json.incidents[i].incident_time,
  		  			 	json.incidents[i].formatted_address, json.incidents[i].lat, json.incidents[i].lng);
	  				addMarker(myIncident, map);
				}
  			}
  	     }
  	  });
  	  	  
  }

  function displayMarkers(incidents, map){

	  }
    
    </script>
  </head>
  <body onload="load()" onunload="GUnload()">
  	<div id="map_canvas" style="width:100%; height:100%"></div>
  </body>
</html>
