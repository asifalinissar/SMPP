package org.example;


import pduObjects.PduHeader;
import pduObjects.PduHeaderReader;

public class Main {

    public  static void main(String[] args){
        byte[] data = new byte[]{0, 0, 0, 23, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 1};
        PduHeaderReader pduHeaderReader = new PduHeaderReader();
        PduHeader pduHeader = pduHeaderReader.readHeader(data);

        System.out.println(pduHeader.getCommandLength());
    }
}