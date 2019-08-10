package wipf.elcd;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test")
public class rest {

	@GET
	@Path("/go")
	public Response responseMsg() {

		String response = "Hello from: ";

		return Response.status(200).entity(response).build();
	}

}
