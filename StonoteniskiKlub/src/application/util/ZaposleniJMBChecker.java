package application.util;

import application.model.dto.ZaposleniDTO;

public class ZaposleniJMBChecker extends Checker<ZaposleniDTO>{

	public ZaposleniJMBChecker(String target) {
		super(target);
	}

	@Override
	public boolean check(ZaposleniDTO obj) {
		return obj.getJmb().contains(target);
	}
	@Override
	public String toString() {
		return "JMB";
	}
}
