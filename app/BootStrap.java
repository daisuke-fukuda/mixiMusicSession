import java.io.FileNotFoundException;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class BootStrap extends Job {

	@Override
	public void doJob() throws FileNotFoundException {

		Fixtures.delete();

		Fixtures.loadModels("initial-data.yml");

	}

}
