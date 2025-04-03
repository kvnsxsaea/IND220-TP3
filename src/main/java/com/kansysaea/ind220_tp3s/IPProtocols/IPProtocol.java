package com.kansysaea.ind220_tp3s.IPProtocols;

public interface IPProtocol {
    public void sendData(String data);
    public String receiveData();
    public void close();
}
