package com.labs.ex;

import java.io.Serializable;

public class Post implements Serializable {
	public String imageUri;
	public String header;
	public String body;
	Post(String imageUri, String header, String body) {
		this.imageUri = imageUri;
		this.header = header;
		this.body = body;
	}
}
