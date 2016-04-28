package com.cmz.logsys.loginfo;

public class ServerSelInfo extends LogInfoBase {
	public String getGameServer() {
		return gameServer;
	}
	public void setGameServer(String gameServer) {
		this.gameServer = gameServer;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getTs() {
		return ts;
	}
	public void setTs(int ts) {
		this.ts = ts;
	}
	private String gameServer;
	private String serverId;
	private String areaId;
	private String playerId;
	private String roleName;
	private String userName;
	private int ts;
}
