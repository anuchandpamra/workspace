package incident.feed

class IncidentFeed {

	static main(args) {
		
		if (args.length < 2){
			println 'Please provide city and state';
			exit 1;
		}
		Location loc = new Location();
		loc.city = args[0];
		loc.state = args[1];
		
		GeocoderService gs = new GeocoderService();
		
		Map mapLoc = gs.geocodeLocation(loc);
		
		IncidentFactory lf = new IncidentFactory()
		
		Random rand = new Random();
		
		while (true){
			int nextInt = rand.nextInt(21);
			Thread.sleep((nextInt+10) * 1000);
			Incident incident = lf.generateIncidents (mapLoc)
		}
		
	}

}
