package org.wipf.wipfapp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wipf.wipfapp.model.base.MWipf;
import org.wipf.wipfapp.model.telegram.apps.MTodoList;
import org.wipf.wipfapp.model.telegram.system.MTelegram;

/**
 * @author wipf
 *
 */
@Path("/telegram")
public class RestTelegram {

	@POST
	@Path("/setbot/{bot}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response setbot(@PathParam("bot") String sBot) {
		return MWipf.genResponse(MTelegram.setbot(sBot));
	}

	@GET
	@Path("/telelog")
	@Produces(MediaType.TEXT_PLAIN)
	public Response telelog() {
		return MWipf.genResponse(MTelegram.getTelegramLog(null));
	}

	@GET
	@Path("/telelogtf")
	@Produces(MediaType.TEXT_PLAIN)
	public Response telelogtf() {
		return MWipf.genResponse(MTelegram.getTelegramLog("798200105"));
	}

	@GET
	@Path("/todolist")
	@Produces(MediaType.TEXT_PLAIN)
	public Response todolist() {
		return MWipf.genResponse(MTodoList.getAllFull());
	}

}
