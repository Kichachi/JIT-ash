package pl.jitsolutions.jitash.business.employee.entity;

public enum Status {
	ACTIVE("ACTIVE","Aktywny"),
	INACTIVE("INACTIVE","Nieaktywny"),
	ALL("ALL","Wszyscy");

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private String label;

	Status(String value,String label) {
		this.value=value;
		this.label=label;
	}
}