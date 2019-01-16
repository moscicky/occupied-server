package pl.moscicki.remoteserver;

public class UserExistsDto {
  private boolean exists;

  public UserExistsDto(boolean exists) {
    this.exists = exists;
  }

  public boolean isExists() {
    return exists;
  }

  public void setExists(boolean exists) {
    this.exists = exists;
  }
}
