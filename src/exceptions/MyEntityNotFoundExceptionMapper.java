package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyEntityNotFoundExceptionMapper implements ExceptionMapper<MyEntityNotFoundException> {

    @Override
    public Response toResponse(MyEntityNotFoundException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
