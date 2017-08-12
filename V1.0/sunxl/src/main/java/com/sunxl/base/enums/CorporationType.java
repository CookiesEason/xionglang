package com.sunxl.base.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 熊浪
 * @Email xiongl@sunline.cn
 * @Time 2017年5月12日
 * @此类作用：
 */
public enum CorporationType {
	CORPORATION("企业", 1), BANK("银行", 2), GUARANTEE("担保", 2), SECURITY("证券", 2), INSURANCE("保险", 2), ENTRUST("信托", 2), FINANCING_LEASE("融资租赁", 2), FUNDS_COMPANY("基金公司", 2), LOAN_COMPANY("小贷公司", 2), OTHER_FINANCE("其他金融机构", 2), LAW("法律", 3), FINANCIAL_AFFAIRS("财会", 3), MANAGE_ONSULT("管理/咨询", 3), MARKETING("营销", 3), OTHER_INTERMEDIARY_AGENT("其他中介机构", 4), INVESTMENT_ORGANIZATION("投资机构", 4), EXPERT("专家", 5), INDIVIDUAL("金科专员", 6), ENTERPRISE("企业会员", 7), ORGANIZATION_BANK("银行", 8), ORGANIZATION_FINANCING("小贷公司", 8), RENTAL_FINANCING("租贷公司", 8), FINANCING_GUARANTEE("担保公司", 8), INTERNET_BANKING("互联网金融", 8), PAWN_FINANCING("典当公司", 8), INVEST("投资机构", 9), INCUBATOR("企业孵化器", 9), FINANCIAL("财务服务", 10), PROPERTY("知识产权服务", 10), HUMAN("人力资源服务", 10), LAWS("法律服务", 10), SCIENCE_SUPPORT("科技支撑单位", 10), OTHET_SERVICE(
			"其他服务机构", 10);

	private String text;
	private int type;

	public static final int COMPANY = 1;
	public static final int FINANCE = 2;
	public static final int INTERMEDIARY = 3;
	public static final int INVESTMENT = 4;
	public static final int OTHER = 5;
	public static final int PERSONAL = 6;
	public static final int COM = 7;
	public static final int INVER = 8;
	public static final int GUQUAN = 9;
	public static final int FUWU = 10;

	private CorporationType(String text, int type) {
		this.text = text;
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public int getType() {
		return type;
	}

	public static List<CorporationType> findByType(int type) {
		List<CorporationType> types = new ArrayList<CorporationType>();
		for (CorporationType corporationType : CorporationType.values()) {
			if (corporationType.getType() == type) {
				types.add(corporationType);
			}
		}
		return types;
	}

	public static Object[] split() {
		Object[] map = new Object[10];
		List<CorporationType> company = new ArrayList<CorporationType>();
		List<CorporationType> finance = new ArrayList<CorporationType>();
		List<CorporationType> intermediary = new ArrayList<CorporationType>();
		List<CorporationType> investment = new ArrayList<CorporationType>();
		List<CorporationType> other = new ArrayList<CorporationType>();
		List<CorporationType> personal = new ArrayList<CorporationType>();
		List<CorporationType> com = new ArrayList<CorporationType>();
		List<CorporationType> inver = new ArrayList<CorporationType>();
		List<CorporationType> guquan = new ArrayList<CorporationType>();
		List<CorporationType> fuwu = new ArrayList<CorporationType>();
		for (CorporationType corporationType : CorporationType.values()) {
			if (corporationType.getType() == COMPANY) {
				company.add(corporationType);
			} else if (corporationType.getType() == FINANCE) {
				finance.add(corporationType);
			} else if (corporationType.getType() == INTERMEDIARY) {
				intermediary.add(corporationType);
			} else if (corporationType.getType() == INVESTMENT) {
				investment.add(corporationType);
			} else if (corporationType.getType() == OTHER) {
				other.add(corporationType);
			} else if (corporationType.getType() == PERSONAL) {
				personal.add(corporationType);
			} else if (corporationType.getType() == COM) {
				com.add(corporationType);
			} else if (corporationType.getType() == INVER) {
				inver.add(corporationType);
			} else if (corporationType.getType() == GUQUAN) {
				guquan.add(corporationType);
			} else if (corporationType.getType() == FUWU) {
				fuwu.add(corporationType);
			}
		}
		map[0] = company;
		map[1] = finance;
		map[2] = intermediary;
		map[3] = investment;
		map[4] = other;
		map[5] = personal;
		map[6] = com;
		map[7] = inver;
		map[8] = guquan;
		map[9] = fuwu;
		return map;
	}
}
