package hw4.prometheus;

import hw4.prometheus.dto.UserDto;
import hw4.prometheus.service.Service;

import java.io.IOException;
import java.time.LocalDateTime;

public class Application {
    public static void main(String[] args) throws Exception {
        Service service = new Service();
        int counter = 1;
        while (true) {
            UserDto userDto = new UserDto();
            userDto.setFirstName("firstName " + counter);
            userDto.setLastName("lastName " + counter);
            userDto.setMiddleName("middleName " + counter);
            UserDto createdUser = service.create(userDto);
            service.get(createdUser.getId());

            userDto.setFirstName("changedFirstName " + counter);
            userDto.setLastName("changedLastName " + counter);
            userDto.setMiddleName("changedMiddleName " + counter);
            service.update(createdUser.getId(), userDto);
            service.delete(createdUser.getId());

            if(counter % 10 == 0) {
                Thread.sleep(2000);
            }

            System.out.println(counter + " " + LocalDateTime.now());

            counter++;
        }
    }
}
