package application.util;

import application.model.dto.ZaposleniDTO;

public class ZaposleniImeChecker extends Checker<ZaposleniDTO>{
	public ZaposleniImeChecker(String target) {
		super(target);
	}
	@Override
	public boolean check(ZaposleniDTO obj) {
		return obj.getIme().contains(target);
	}
	@Override
	public String toString() {
		return "Ime";
	}
}
