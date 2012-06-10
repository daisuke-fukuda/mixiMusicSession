import java.io.FileNotFoundException;

import models.Event;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class BootStrap extends Job {

	@Override
	public void doJob() throws FileNotFoundException {

		if(Event.findAll() == null
				|| Event.findAll().size() == 0){
			Fixtures.delete();
			Fixtures.loadModels("initial-data.yml");

		}

	}

}
