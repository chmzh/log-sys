package com.cmz.logsys.loginfo;

/**
 * 跟踪游戏消费点统计信息
 */
public class ConsumeInfo extends LogInfoBase {
	private String serverId;// 游戏服务区
	private String item;// 某个消费点的名称，最多32个字符
	private int number;// 消费数量
	private int IngotTotal;// 消费前元宝总数
	private int IngotPrice;// 元宝单价
	private double virtualCurrencyPrice;// 虚拟币单价
	private String missionId;// 任务、关卡或副本的编号，最多32个字符。
	private int roleLevel;// 角色等级
	// private String roleId;// 角色ID
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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getIngotTotal() {
		return IngotTotal;
	}

	public void setIngotTotal(int ingotTotal) {
		IngotTotal = ingotTotal;
	}

	public int getIngotPrice() {
		return IngotPrice;
	}

	public void setIngotPrice(int ingotPrice) {
		IngotPrice = ingotPrice;
	}

	public double getVirtualCurrencyPrice() {
		return virtualCurrencyPrice;
	}

	public void setVirtualCurrencyPrice(double virtualCurrencyPrice) {
		this.virtualCurrencyPrice = virtualCurrencyPrice;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
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
		return serverId + "," + item + "," + number + "," + IngotTotal + "," + IngotPrice + "," + virtualCurrencyPrice + "," + missionId
				+ "," + roleLevel + "," + playerId + "," + roleName + "," + userName + "," + super.getCol1() + "," + super.getCol2() + ","
				+ super.getCol3() + "," + ts;
	}

}
