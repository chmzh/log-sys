package com.cmz.logsys.loginfo;

/**
 * 任务、关卡或副本信息 
 */
public class MissionInfo extends LogInfoBase {
	private String serverId; // 游戏服务区
	private String missionId; // 任务、关卡或副本的编号，最多32个字符。


	private String mainTaskId;     //主任务ID
	private String subTaskId;      //子任务ID
	private int type; // 1:任务 2:关卡 3:副本 4: 其它操作日志
	private int status; // 1:开始 2:结束 3:失败
	private String cause; // 操作描述
	private String playerId; // 玩家ID
	private String roleName; // 角色名
	private String userName; // 在帐户有显性名时，可用于设定帐户名，最多支持64个字符。
	private String playerStatus; // 玩家状态例如: 金币|元宝|职业|工会…(由游戏提供格式说明)
	private int Ingot; // 当前元宝
	private double virtualCurrency; // 当前虚拟币
	private String scenceId; // 场景编号
	private int roleLevel; // 角色等级
	private int ts; // 操作时间(10位)

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
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

	public String getPlayerStatus() {
		return playerStatus;
	}

	public void setPlayerStatus(String playerStatus) {
		this.playerStatus = playerStatus;
	}

	public int getIngot() {
		return Ingot;
	}

	public void setIngot(int ingot) {
		Ingot = ingot;
	}

	public double getVirtualCurrency() {
		return virtualCurrency;
	}

	public void setVirtualCurrency(double virtualCurrency) {
		this.virtualCurrency = virtualCurrency;
	}

	public String getScenceId() {
		return scenceId;
	}

	public void setScenceId(String scenceId) {
		this.scenceId = scenceId;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public int getTs() {
		return ts;
	}

	public void setTs(int ts) {
		this.ts = ts;
	}

	public String getMainTaskId() {
		return mainTaskId;
	}

	public void setMainTaskId(String mainTaskId) {
		this.mainTaskId = mainTaskId;
	}

	public String getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskId(String subTaskId) {
		this.subTaskId = subTaskId;
	}
	
	@Override
	public String toString() {
		return serverId + "," + missionId + "," + type + "," + status + "," + cause + "," + playerId + "," + roleName + "," + userName + ","
				+ playerStatus + "," + Ingot + "," + virtualCurrency + "," + scenceId + "," + roleLevel + "," + super.getCol1() + ","
				+ super.getCol2() + "," + super.getCol3() + "," + ts;
	}
}
