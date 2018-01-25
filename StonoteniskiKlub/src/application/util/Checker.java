package application.util;

public abstract class Checker<T> {
	
	public Checker(String target) {
		this.setTarget(target);
	}
	
	public abstract boolean check(T obj);
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	protected String target;
}
