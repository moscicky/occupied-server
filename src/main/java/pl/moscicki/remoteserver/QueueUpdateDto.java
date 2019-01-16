package pl.moscicki.remoteserver;

import java.util.Queue;

public class QueueUpdateDto {
  private Queue<String> queue;
  private boolean occupied;
  private Long secondsLeft;

  public QueueUpdateDto(Queue<String> queue, boolean occupied) {
    this.queue = queue;
    this.occupied = occupied;
  }

  public QueueUpdateDto(Queue<String> queue, boolean occupied, Long secondsLeft) {
    this.queue = queue;
    this.occupied = occupied;
    this.secondsLeft = secondsLeft;
  }

  public Queue<String> getQueue() {
    return queue;
  }

  public void setQueue(Queue<String> queue) {
    this.queue = queue;
  }

  public boolean isOccupied() {
    return occupied;
  }

  public void setOccupied(boolean occupied) {
    this.occupied = occupied;
  }

  public Long getSecondsLeft() {
    return secondsLeft;
  }

  public void setSecondsLeft(long secondsLeft) {
    this.secondsLeft = secondsLeft;
  }
}
