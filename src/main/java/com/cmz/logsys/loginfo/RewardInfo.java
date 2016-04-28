package com.cmz.logsys.loginfo;

/**
 *跟踪获赠元宝和虚拟货币信息
 */
public class RewardInfo  extends LogInfoBase{
	private String serverId;// 游戏服务区
	private int IngotAmount;// 元宝
	private int IngotTotal;// 获赠前元宝总数
	private double virtualCurrencyAmount;// 虚拟币金额
	private String reason;// 赠送虚拟币原因/类型。格式：32个字符内的中文、空格、英文、数字。不要带有任何开发中的转义字符，如斜杠。注意：最多支持100种不同原因。
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

	public int getIngotAmount() {
		return IngotAmount;
	}

	public void setIngotAmount(int ingotAmount) {
		IngotAmount = ingotAmount;
	}

	public int getIngotTotal() {
		return IngotTotal;
	}

	public void setIngotTotal(int ingotTotal) {
		IngotTotal = ingotTotal;
	}

	public double getVirtualCurrencyAmount() {
		return virtualCurrencyAmount;
	}

	public void setVirtualCurrencyAmount(double virtualCurrencyAmount) {
		this.virtualCurrencyAmount = virtualCurrencyAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
		return serverId + "," + IngotAmount + "," + IngotTotal + "," + virtualCurrencyAmount + "," + reason + "," + playerId + ","
				+ roleName + "," + userName + "," + super.getCol1() + "," + super.getCol2() + "," + super.getCol3() + "," + ts;
	}



}
