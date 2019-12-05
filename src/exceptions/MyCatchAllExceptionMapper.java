package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyCatchAllExceptionMapper implements ExceptionMapper<MyCatchAllException> {
    @Override
    public Response toResponse(MyCatchAllException e) {
        return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
    }
}
