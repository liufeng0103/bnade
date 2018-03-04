package com.bnade.wow.rs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.bnade.util.DBUtil;

/**
 * 插件相关
 *
 * Created by liufeng0103 on 2016/8/16.
 */
@Path("/addon")
public class AddonResource {

    private QueryRunner run;

    public AddonResource() {
        run = new QueryRunner(DBUtil.getDataSource());
    }

    @GET
    @Path("/version")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getVersion() throws SQLException, IOException {
//        String version = run.query("select version from t_addon limit 1", new ScalarHandler<String>());
    	String version = Files.readAllLines(Paths.get("/var/lib/tomcat/webapps/ROOT/data_version")).get(0);
        return "{\"version\":\"" + version + "\"}";
    }
    
    @GET
    @Path("/tsm/{realmId}/version")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getTSMRealmVersion(@PathParam("realmId")int realmId) throws SQLException {
        String version = run.query("select version from t_tsm_realm_data_version where realmId=?", new ScalarHandler<String>(), realmId);
        return "{\"version\":\"" + version + "\"}";
    }

}
