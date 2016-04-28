package com.cmz.logsys.loginfo;

/**
 * 角色升级统计信息
 */
public class RoleLvUpInfo  extends LogInfoBase{
	private String serverId;// 游戏服务区
	private int oldlevel;// 升级前等级
	private int newlevel;// 升级后等级
	private int sucess;// 1：成功，0失败
	private String params;// 消耗金币和道具情况（没有可以不记录）
	// private String roleId;// 角色标示号
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

	public int getOldlevel() {
		return oldlevel;
	}

	public void setOldlevel(int oldlevel) {
		this.oldlevel = oldlevel;
	}

	public int getNewlevel() {
		return newlevel;
	}

	public void setNewlevel(int newlevel) {
		this.newlevel = newlevel;
	}

	public int getSucess() {
		return sucess;
	}

	public void setSucess(int sucess) {
		this.sucess = sucess;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	// public String getRoleId() {
	// return roleId;
	// }
	//
	// public void setRoleId(String roleId) {
	// this.roleId = roleId;
	// }

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
		return serverId + "," + oldlevel + "," + newlevel + "," + sucess + "," + params + "," + playerId + "," + roleName + "," + userName
				+ "," + super.getCol1() + "," + super.getCol2() + "," + super.getCol3() + "," + ts;
	}



}
