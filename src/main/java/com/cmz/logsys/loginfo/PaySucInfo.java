package com.cmz.logsys.loginfo;

public class PaySucInfo extends LogInfoBase{
	private String serverId;// 游戏服务区
	private String orderId;// 订单ID，最多64个字符。用于唯一标识一次交易。
	private int roleLevel;// 角色等级
	private int IngotAmount;// 元宝
	private int IngotTotal;// 充值成功前元宝总数
	private String iapId;// 充值包ID，最多32个字符。例如：VIP3礼包、500元10000宝石包。
	private double currencyAmount;// 现金金额或现金等价物的额度。
	private String currencyType;// 请使用国际标准组织ISO 4217中规范的3位字母代码标记货币类型。点击查看参考
								// 例：人民币CNY；美元USD；欧元EUR（如果您使用其他自定义等价物作为现金，亦可使用ISO
								// 217中没有的3位字母组合传入货币类型，我们会在报表页面中提供汇率设定功能）
	private double virtualCurrencyAmount;// 虚拟币金额
	private String paymentType;// 支付的途径，最多16个字符。例如：“支付宝”“苹果官方”“XX支付SDK”
	private String payPlatform;// 支付平台，例如qq空间等。
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
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

	public String getIapId() {
		return iapId;
	}

	public void setIapId(String iapId) {
		this.iapId = iapId;
	}

	public double getCurrencyAmount() {
		return currencyAmount;
	}

	public void setCurrencyAmount(double currencyAmount) {
		this.currencyAmount = currencyAmount;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public double getVirtualCurrencyAmount() {
		return virtualCurrencyAmount;
	}

	public void setVirtualCurrencyAmount(double virtualCurrencyAmount) {
		this.virtualCurrencyAmount = virtualCurrencyAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
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
		return serverId + "," + orderId + "," + roleLevel + "," + IngotAmount + "," + IngotTotal + "," + iapId + "," + currencyAmount + ","
				+ currencyType + "," + virtualCurrencyAmount + "," + paymentType + "," + payPlatform + "," + playerId + "," + roleName + ","
				+ userName + "," + super.getCol1() + "," + super.getCol2() + "," + super.getCol3() + "," + ts;
	}



}
