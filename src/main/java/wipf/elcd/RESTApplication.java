package wipf.elcd;

import java.util.HashSet;
import java.util.Set;

public class RESTApplication extends javax.ws.rs.core.Application {

	private Set<Object> singletons = new HashSet<Object>();

	public RESTApplication() {
		singletons.add(new rest());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	// org.jboss.resteasy.core.AsynchronousDispatcher

}
