package wipf.elcd;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/wipf")
//@Consumes({ "application/json" })
//@Produces({ "application/json" })
public class rest {

	@GET
	@Path("/go")
	public Response responseMsg() {

		String response = "Hello from: go";

		return Response.status(200).entity(response).build();
	}

	@GET
	@Path("/wipf")
	public String responseMsg2() {

		return "Hello from: wipf";
	}

}
