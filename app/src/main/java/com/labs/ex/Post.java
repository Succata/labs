package com.labs.ex;

import java.io.Serializable;

public class Post implements Serializable {
	public String imageUri;
	Post(String imageUri) {
		this.imageUri = imageUri;
	}
}
