package io.github.profilr.web.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;


import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

public class CharacterLimitExceededExceptionMapper implements ExceptionMapper<MysqlDataTruncation> {

	@Override
	public Response toResponse(MysqlDataTruncation exception) {
		return Response.status(Status.REQUEST_ENTITY_TOO_LARGE)
					   .build();
	}

}
