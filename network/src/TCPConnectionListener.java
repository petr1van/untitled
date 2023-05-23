public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection tcpConnection);
    void onReceieveString(TCPConnection tcpConnection, String value);
    void onDisconnect(TCPConnection tcpConnection);
    void onException(TCPConnection tcpConnection, Exception e);

}
