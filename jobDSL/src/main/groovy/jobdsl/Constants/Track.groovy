package jobdsl.Constants

enum Track {

	QUICK('quick'),
	FULL('full')
	
	public final String track
	
	Track(String track) {
		this.track = track
	}
	
	def track() {
		return this.track
	}
}
