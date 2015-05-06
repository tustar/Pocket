package com.tustar.pocket.model;

public class Address {

	private String id;
	private String name;
	private String parent_id;
	private int type;
	private String zip;
	private boolean delivery;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public boolean isDelivery() {
		return delivery;
	}

	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", name=" + name + ", parent_id="
				+ parent_id + ", type=" + type + ", zip=" + zip + ", delivery="
				+ delivery + "]";
	}

}
