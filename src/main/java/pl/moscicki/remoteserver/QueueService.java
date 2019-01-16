package pl.moscicki.remoteserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Queue;

@Component
class QueueService {

  private String frontendUrl;
  private final Db database;
  private final RestTemplate restTemplate;

  QueueService(Db database) {
    this.database = database;
    restTemplate = new RestTemplate();
  }

  Queue<String> getQueue() {
    return database.getQueue();
  }

  QueueUpdateDto getQueueStatus() {
    if (database.isToiletOccupied()) {
      return new QueueUpdateDto(getQueue(), database.isToiletOccupied(), null);
    } else {
      if (database.getQueue().isEmpty()) {
        return new QueueUpdateDto(getQueue(), database.isToiletOccupied(), null);
      } else {
        long timeUsed = System.currentTimeMillis() - database.getTimeAsFirst();
        long timeLeft = 30 - (timeUsed / 1000);
        return new QueueUpdateDto(getQueue(), database.isToiletOccupied(), timeLeft);
      }
    }
  }

  String addToQueue(String person) {
    if (database.getUsers().containsKey(person)) {
      if (database.getQueue().contains(person)) {
        return String.format("%s already in the queue", person);
      } else {
          countTimeAsFirst();
          database.getQueue().add(person);
      }
      return String.format("%s added to queue successfully", person);
    } else {
      return "No such user registered";
    }
  }

  synchronized AccessDto scan(Integer[] uuid) {
    //If user with scanned uuid is first in thr queue
    if (Arrays.equals(database.getUsers().get(getQueue().peek()), uuid)) {
      //If the toilet is occupied
      if (database.isToiletOccupied()) {
       //Remove user from the top of the queue and free the toilet, revoke the access
       getQueue().poll();
       database.setToiletOccupied(false);
       database.setTimeAsFirst(System.currentTimeMillis());
        return new AccessDto(uuid, AccessStatus.REVOKED);
      } else {
        //Set toilet as occupied, grant the access
        database.setToiletOccupied(true);
        return new AccessDto(uuid, AccessStatus.GRANTED);
      }
    } else {
      //Deny the access
      return new AccessDto(uuid, AccessStatus.DENIED);
    }
  }

  private void updateFrontend(Queue<String> queue, boolean isOccupied) {
    HttpEntity<QueueUpdateDto> request = new HttpEntity<>(new QueueUpdateDto(queue, isOccupied));
    restTemplate.postForEntity(frontendUrl, request, String.class);
  }

  private void countTimeAsFirst() {
    if (getQueue().size() == 0) {
      database.setTimeAsFirst(System.currentTimeMillis());
    }
  }

  //Invoke every 10s
  @Scheduled(fixedRate = 2000)
  public void freeFirstPlace() {
    //If queue is not empty and toilet is not occupied
    if (!database.getQueue().isEmpty() && !database.isToiletOccupied()) {
      //Remove user from top of the queue if he hasn't entered the toiled after more than
      //30 second from being first
      if (System.currentTimeMillis() - database.getTimeAsFirst() > 30000) {
        database.getQueue().poll();
        database.setTimeAsFirst(System.currentTimeMillis());
      }
    }
  }



}
