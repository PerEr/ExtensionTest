package api.services;

public interface UsageReport {

    void reportLogin(String user);
    void reportLogout(String user);
    void reportGameStarted(String user, String game);

}
