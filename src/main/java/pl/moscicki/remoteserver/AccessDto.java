package pl.moscicki.remoteserver;

class AccessDto {
  private Integer[] uuid;
  private AccessStatus status;

  AccessDto(Integer[] uuid, AccessStatus status) {
    this.uuid = uuid;
    this.status = status;
  }

  public Integer[] getUuid() {
    return uuid;
  }

  public void setUuid(Integer[] uuid) {
    this.uuid = uuid;
  }

  public AccessStatus getStatus() {
    return status;
  }

  public void setStatus(AccessStatus status) {
    this.status = status;
  }
}

