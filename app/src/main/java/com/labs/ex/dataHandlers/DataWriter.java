package com.labs.ex.dataHandlers;

public interface DataWriter {
	void read();
	void write();
	void writeAll();
	void delete(int pos);
	void redact(int pos, String... args);
	void loadFromVK();
}
