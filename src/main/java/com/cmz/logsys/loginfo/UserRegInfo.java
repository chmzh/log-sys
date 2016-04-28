package com.cmz.logsys.loginfo;

/**
 *用户注册信息
 */
public class UserRegInfo  extends LogInfoBase{
	private String userType;
	private String gender;
	private int age;
	private String gameServer;
	private String serverId;
	private String areaId;
//	private String roleId;
	private String playerId;
	private String roleName;
	private String userName;
	private int ts;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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
		return userType + "," + gender + "," + age + "," + gameServer + "," + serverId + "," + areaId  + "," + playerId
				+ "," + roleName + "," + userName + "," + super.getCol1() + "," + super.getCol2() + "," + super.getCol3() + "," + ts;
	}



}
