package com.cmz.logsys.loginfo;

/***
 * 用户登陆信息
 *
 */
public class UserLoginInfo  extends LogInfoBase{
	private String gameServer;
	private String serverId;
	private String areaId;
//	private String roleId;
	private String playerId;
	private String roleName;
	private String userName;
	private int ts;
	
	public UserLoginInfo(String gameServer,String serverId,String areaId,String roleId,String playerId,String roleName,
			String userName,int ts) {
		super();
		this.gameServer = gameServer;
		this.serverId = serverId;
		this.areaId = areaId;
//		this.roleId = roleId;
		this.playerId = playerId;
		this.roleName = roleName;
		this.userName = userName;
		this.ts = ts;
	}


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

//	public String getRoleId() {
//		return roleId;
//	}
//
//	public void setRoleId(String roleId) {
//		this.roleId = roleId;
//	}

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

	@Override
	public String toString() {
		return gameServer + "," + serverId + "," + areaId + "," + playerId + "," + roleName + "," + userName 
				+ "," + super.getCol1() + "," + super.getCol2() + "," + super.getCol3() + "," + ts;
	}



}
