package com.labs.ex.beans;

import java.io.Serializable;

public class Post implements Serializable {
	public String imageUri;
	public String header;
	public String body;
	public String mapAddress;
	public transient String link;

	public Post(){}

	public Post(String imageUri, String header, String body, String mapAddress) {
		this.imageUri = imageUri;
		this.header = header;
		this.body = body;
		this.mapAddress = mapAddress;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
