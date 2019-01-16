package pl.moscicki.remoteserver;

class UserDto {
  private String name;
  private Integer[] uuid;

  UserDto(String name, Integer[] uuid) {
    this.name = name;
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  public Integer[] getUuid() {
    return uuid;
  }

  void setUuid(Integer[] uuid) {
    this.uuid = uuid;
  }
}
