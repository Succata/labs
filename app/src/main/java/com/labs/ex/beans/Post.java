package com.labs.ex.beans;

import java.io.Serializable;

public class Post implements Serializable {
	public String imageUri;
	public String header;
	public String body;
	public transient String link;

	public Post(){}

	public Post(String imageUri, String header, String body) {
		this.imageUri = imageUri;
		this.header = header;
		this.body = body;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
