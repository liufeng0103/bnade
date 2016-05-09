package com.bnade.wow.rs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bnade.wow.po.WowToken;
import com.bnade.wow.service.WowTokenService;
import com.bnade.wow.service.impl.WowTokenServiceImpl;

@Path("/ah")
public class WowTokenResource {
	
	private WowTokenService wowTokenService;
	
	public WowTokenResource() {
		wowTokenService = new WowTokenServiceImpl();
	}

	@GET
	@Path("/wowtokens")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getWowTokens() {
		try {
			List<Object[]> tokenList = new ArrayList<>();
			List<WowToken> tokens = wowTokenService.getAll();				
			for (WowToken token : tokens) {
				Object[] tokenArray = new Object[2];
				tokenArray[0] = token.getUpdated();
				tokenArray[1] = token.getBuy();				
				tokenList.add(tokenArray);
			}			
			return tokenList;
		} catch (SQLException e) {
			return Response.status(404).entity("数据找不到").type(MediaType.TEXT_PLAIN).build();
		}		
	}
}
