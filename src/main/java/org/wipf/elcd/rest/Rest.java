package org.wipf.elcd.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.wipf.elcd.model.MTime;
import org.wipf.elcd.model.MWipf;
import org.wipf.elcd.model.M_ELcd_Control;

@RequestScoped
@Path("/")
public class Rest {

//	@Inject
//	private MWipf mWipf;

	@GET
	@Path("/t")
	@Produces("text/plain")
	public String testRest() {
		return MWipf.testRest();
	}

	@GET
	@Path("/date")
	@Produces("text/plain")
	public String date() {
		return MTime.date();
	}

	// Start Senden
	@GET
	@Path("s")
	@Produces("text/plain")
	public String startLcd() {
		M_ELcd_Control.startElcd();
		System.out.println("Send to LCD");
		return "RUN";
	}

	@GET
	@Path("r/{bis}/{anzahl}")
	@Produces("text/plain")
	public String zufall(@PathParam("bis") Integer nBis, @PathParam("anzahl") Integer nAnzahl) {
		return MWipf.zufall(nBis, nAnzahl);
	}

}
