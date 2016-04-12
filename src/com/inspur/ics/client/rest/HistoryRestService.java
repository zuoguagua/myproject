package com.inspur.ics.client.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.inspur.ics.pojo.monitor.HistoryMonitorInfo;

/**
 * @author kangzhx
 */
@Path("/")
public interface HistoryRestService {
	@POST
	@Path("history_getVmCpuPerfsAmongTime")
	String getVmCpuPerfsAmongTime(@FormParam("token") String token, @FormParam("vmUUID") String vmUuid,
			@FormParam("fromTime") String fromTime, @FormParam("toTime") String toTime);

	@POST
	@Path("history_getVmMemPerfsAmongTime")
	String getVmMemPerfsAmongTime(@FormParam("token") String token, @FormParam("vmUUID") String vmUuid,
			@FormParam("fromTime") String fromTime, @FormParam("toTime") String toTime);

	@Deprecated
	@POST
	@Path("history_getVmPerfInfo")
	String getVmPerfInfo(@FormParam("token") String token, @FormParam("vmUUID") String vmUUID,
			@FormParam("type") String type, @FormParam("fromTime") String fromTime, @FormParam("toTime") String toTime,
			@FormParam("resolution") String resolution);

	@POST
	@Path("history_getHostPerfInfo")
	String getHostPerfInfo(@FormParam("token") String token, @FormParam("hostUUID") String hostUUID,
			@FormParam("type") String type, @FormParam("fromTime") String fromTime, @FormParam("toTime") String toTime,
			@FormParam("resolution") String resolution);
}
