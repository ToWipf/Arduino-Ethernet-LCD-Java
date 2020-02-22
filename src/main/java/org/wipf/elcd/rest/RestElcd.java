package org.wipf.elcd.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wipf.elcd.app.Startup;
import org.wipf.elcd.model.base.MWipf;
import org.wipf.elcd.model.elcd.MelcdRun;

@Path("/elcd")
public class RestElcd {

	@Inject
	MelcdRun melcdRun;

	// Start Send to
	@GET
	@Path("/s")
	@Produces(MediaType.TEXT_PLAIN)
	public String startLcd() {
		System.out.println("s");
		return melcdRun.startElcd();
	}

	@PUT
	@Path("/msg/{msg}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendMsg(@PathParam("msg") String sMsg) {
		Boolean bStatus = melcdRun.sendMsg(sMsg);
		return MWipf.genResponse(bStatus.toString());
	}

	@PUT
	@Path("/cls")
	@Produces(MediaType.TEXT_PLAIN)
	public Response cls() {
		Boolean bStatus = melcdRun.clear();
		return MWipf.genResponse(bStatus.toString());
	}

	@OPTIONS
	@Path("/cls")
	public Response clsOptions() {
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
	}

	@OPTIONS
	@Path("/msg/{msg}")
	public Response msgOptions() {
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
	}

	@GET
	@Path("/status")
	@Produces(MediaType.TEXT_PLAIN)
	public Response status() {
		return MWipf.genResponse(Startup.RunLock.toString());
	}

}
