package com.cmz.logsys.loginfo;

public class RoleCancelInfo extends LogInfoBase {
	private String serverId;// 游戏服务区
	private int roleLevel;// 角色等级
	private String playerId;// 玩家ID
	private String roleName;// 角色名
	private String userName;// 在帐户有显性名时，可用于设定帐户名，最多支持64个字符。
	private int ts;// 操作时间(10位)



	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
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

	@Override
	public String toString() {
		return serverId + "," + roleLevel + "," + playerId + "," + roleName + "," + userName + "," + super.getCol1() + "," + super.getCol2()
				+ "," + super.getCol3() + "," + ts;
	}



}
