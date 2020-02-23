package org.wipf.wipfapp.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.wipf.wipfapp.model.base.MBlowfish;
import org.wipf.wipfapp.model.base.MWipf;
import org.wipf.wipfapp.model.base.Wipfapp;
import org.wipf.wipfapp.model.telegram.apps.MOthers;

@Path("/wipf")
public class RestWipf {

	private static final Logger LOGGER = Logger.getLogger("RestWipf");

	@GET
	@Path("/ping/{ip}")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping(@PathParam("ip") String sIP) {
		return MWipf.ping(sIP).toString();
	}

	@GET
	@Path("/date")
	@Produces(MediaType.TEXT_PLAIN)
	public Response date() {
		return MWipf.genResponse(MWipf.date());
	}

	@GET
	@Path("/r/{bis}/{anzahl}")
	@Produces(MediaType.TEXT_PLAIN)
	public String zufall(@PathParam("bis") Integer nBis, @PathParam("anzahl") Integer nAnzahl) {
		return MOthers.zufall(nBis, nAnzahl);
	}

	// Blowfish
	@GET
	@Path("/cr/{txt}")
	@Produces(MediaType.TEXT_PLAIN)
	public String cr(@PathParam("txt") String sIn) throws Exception {
		return MBlowfish.encrypt(sIn, Wipfapp.sKey);
	}

	@GET
	@Path("/dc/{txt}")
	@Produces(MediaType.TEXT_PLAIN)
	public String dc(@PathParam("txt") String sIn) throws Exception {
		return MBlowfish.decrypt(sIn, Wipfapp.sKey);
	}

	@PUT
	@Path("/gc")
	@Produces(MediaType.TEXT_PLAIN)
	public String gc() throws Exception {
		MWipf.runGc();
		return "get";
	}

	// System
	@DELETE
	@Path("/sysHalt")
	public void sysHalt() {
		LOGGER.info("SysHalt");
		System.exit(0);
	}

}
