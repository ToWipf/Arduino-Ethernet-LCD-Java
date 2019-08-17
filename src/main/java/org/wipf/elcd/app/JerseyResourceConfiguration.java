package org.wipf.elcd.app;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.wipf.elcd.rest.Rest;

public class JerseyResourceConfiguration extends ResourceConfig {

	public JerseyResourceConfiguration() {

		// where the TestResource class is
		packages("com.example.rest.resources");

		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(new Rest()).to(Rest.class);
			}
		});
	}
}
