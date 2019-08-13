package com.idowran.serizlizer.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.idowran.serizlizer.ObjectSerializer;

public class HessianSerializer implements ObjectSerializer {

	public byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianOutput ho = new HessianOutput(os);
		try {
			ho.writeObject(obj);
			ho.flush();
			return os.toByteArray();
		} finally {
			ho.close();
		}
	}

	public void serialize(Object obj, OutputStream os) throws IOException {
		HessianOutput ho = new HessianOutput(os);
		ho.writeObject(obj);
		ho.flush();
	}

	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		return this.deserialize(is, clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T deserialize(InputStream is, Class<T> clazz) throws IOException, ClassNotFoundException {
		HessianInput hi = new HessianInput(is);
		return (T) hi.readObject();
	}

	public Object deserialize(InputStream is) throws IOException, ClassNotFoundException {
		HessianInput hi = new HessianInput(is);
		return hi.readObject();
	}

	public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		return this.deserialize(is);
	}

	@SuppressWarnings("unchecked")
	public <T> T deserialize(byte[] bytes, T co) throws IOException, ClassNotFoundException {
		return (T) deserialize(bytes, co.getClass());
	}
}
