package org.wipf.elcd.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wipf.elcd.app.MainApp;
import org.wipf.elcd.model.base.MBlowfish;
import org.wipf.elcd.model.base.MLogger;
import org.wipf.elcd.model.base.MWipf;
import org.wipf.elcd.model.elcd.M_Run;
import org.wipf.elcd.model.elcd.MelcdConnect;
import org.wipf.elcd.model.telegram.apps.MOthers;
import org.wipf.elcd.model.telegram.apps.MTodoList;
import org.wipf.elcd.model.telegram.system.MTelegram;

@Path("/")
public class Rest {

//	@Inject
//	private MWipf mWipf;

	@GET
	@Path("/ping/{ip}")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping(@PathParam("ip") String sIP) {
		return MWipf.ping(sIP).toString();
	}

	@POST
	@Path("/setbot/{bot}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response setbot(@PathParam("bot") String sBot) {
		return MWipf.genResponse(MTelegram.setbot(sBot));
	}

	@GET
	@Path("/date")
	@Produces(MediaType.TEXT_PLAIN)
	public Response date() {
		return MWipf.genResponse(MWipf.date());
	}

	@GET
	@Path("r/{bis}/{anzahl}")
	@Produces(MediaType.TEXT_PLAIN)
	public String zufall(@PathParam("bis") Integer nBis, @PathParam("anzahl") Integer nAnzahl) {
		return MOthers.zufall(nBis, nAnzahl);
	}

	// Blowfish
	@GET
	@Path("/cr/{txt}")
	@Produces(MediaType.TEXT_PLAIN)
	public String cr(@PathParam("txt") String sIn) throws Exception {
		return MBlowfish.encrypt(sIn);
	}

	@GET
	@Path("/dc/{txt}")
	@Produces(MediaType.TEXT_PLAIN)
	public String dc(@PathParam("txt") String sIn) throws Exception {
		return MBlowfish.decrypt(sIn);
	}

	// Start Send to
	@GET
	@Path("s")
	@Produces(MediaType.TEXT_PLAIN)
	public String startLcd() {
		return M_Run.startElcd();
	}

	@GET
	@Path("status")
	@Produces(MediaType.TEXT_PLAIN)
	public Response status() {
		return MWipf.genResponse(MainApp.RunLock.toString());
	}

	@GET
	@Path("telelog")
	@Produces(MediaType.TEXT_PLAIN)
	public Response telelog() {
		return MWipf.genResponse(MTelegram.getTelegramLog(null));
	}

	@GET
	@Path("telelogtf")
	@Produces(MediaType.TEXT_PLAIN)
	public Response telelogtf() {
		return MWipf.genResponse(MTelegram.getTelegramLog("798200105"));
	}

	@GET
	@Path("todolist")
	@Produces(MediaType.TEXT_PLAIN)
	public Response todolist() {
		return MWipf.genResponse(MTodoList.getAllFull());
	}

	@PUT
	@Path("cls")
	@Produces(MediaType.TEXT_PLAIN)
	public Response cls() {
		Boolean bStatus = MelcdConnect.clear();
		return MWipf.genResponse(bStatus.toString());
	}

	@PUT
	@Path("msg/{msg}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendMsg(@PathParam("msg") String sMsg) {
		Boolean bStatus = M_Run.sendMsg(sMsg);
		return MWipf.genResponse(bStatus.toString());
	}

	@OPTIONS
	@Path("cls")
	public Response clsOptions() {
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
	}

	@OPTIONS
	@Path("msg/{msg}")
	public Response msgOptions() {
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
	}

	// System
	@DELETE
	@Path("sysHalt")
	public void sysHalt() {
		MLogger.info("SysHalt");
		System.exit(0);
	}

}
