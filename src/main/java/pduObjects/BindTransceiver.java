package pduObjects;

public class BindTransceiver {

    private String systemId;
    private String password;
    private String systemType;

    private byte interfaceVersion;
    private byte addrTon;
    private byte addrNpi;

    private String addressRange;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public byte getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(byte interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public byte getAddrTon() {
        return addrTon;
    }

    public void setAddrTon(byte addrTon) {
        this.addrTon = addrTon;
    }

    public byte getAddrNpi() {
        return addrNpi;
    }

    public void setAddrNpi(byte addrNpi) {
        this.addrNpi = addrNpi;
    }

    public String getAddressRange() {
        return addressRange;
    }

    public void setAddressRange(String addressRange) {
        this.addressRange = addressRange;
    }
}