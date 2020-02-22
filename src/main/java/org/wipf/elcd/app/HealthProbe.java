package org.wipf.elcd.app;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@SuppressWarnings("deprecation")
@Health
@ApplicationScoped
public class HealthProbe implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.builder().name("wipfapp").up().build();
	}

}