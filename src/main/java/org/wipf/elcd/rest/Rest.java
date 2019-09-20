package org.wipf.elcd.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.wipf.elcd.model.MPing;
import org.wipf.elcd.model.MTime;
import org.wipf.elcd.model.MWipf;
import org.wipf.elcd.model.M_ELcd_Control;

@RequestScoped
@Path("/")
public class Rest {

//	@Inject
//	private MWipf mWipf;

	@GET
	@Path("/ping/{ip}")
	@Produces("text/plain")
	public String ping(@PathParam("ip") String sIP) {
		return MPing.ping(sIP).toString();
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
		System.out.println(M_ELcd_Control.startElcd());
		return "X";
	}

	@GET
	@Path("r/{bis}/{anzahl}")
	@Produces("text/plain")
	public String zufall(@PathParam("bis") Integer nBis, @PathParam("anzahl") Integer nAnzahl) {
		return MWipf.zufall(nBis, nAnzahl);
	}

}
