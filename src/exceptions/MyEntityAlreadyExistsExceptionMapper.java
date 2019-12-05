package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyEntityAlreadyExistsExceptionMapper implements ExceptionMapper<MyEntityAlreadyExistsException> {
    @Override
    public Response toResponse(MyEntityAlreadyExistsException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
