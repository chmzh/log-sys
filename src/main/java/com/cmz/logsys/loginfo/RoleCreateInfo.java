package com.cmz.logsys.loginfo;

/***
 * 角色创建统计信息
 */
public class RoleCreateInfo  extends LogInfoBase{
	private String serverId;// 游戏服务区
	private String roleType;// 角色职业 勇士、剑客等
	private String roleGender;// 设定角色性别：
	private int roleAge;// 设定角色年龄，范围为0-120。
	private int roleLevel;// 设定玩家当前的级别，未设定过等级的玩家默认的初始等级为“1”，支持的最大级别为1000。
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

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleGender() {
		return roleGender;
	}

	public void setRoleGender(String roleGender) {
		this.roleGender = roleGender;
	}

	public int getRoleAge() {
		return roleAge;
	}

	public void setRoleAge(int roleAge) {
		this.roleAge = roleAge;
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
		return serverId + "," + roleType + "," + roleGender + "," + roleAge + "," + roleLevel + "," + playerId + "," + roleName + ","
				+ userName + "," + super.getCol1() + "," + super.getCol2() + "," + super.getCol3() + "," + ts;
	}



}
