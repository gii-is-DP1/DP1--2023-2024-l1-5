package org.springframework.samples.petclinic.friendship;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.exceptions.FriendshipExistsException;
import org.springframework.samples.petclinic.player.PlayerService;

@SpringBootTest
public class FrienshipServiceTest {
    @Autowired
    PlayerService playerService;

    @Autowired
    FriendshipService fs;

    @Test
    public void saveFriendship(){
        Friendship f = createValidFriendship();
        try {
            fs.saveFriendship(f,"POST");
        } catch (Exception e) {
            fail("No exception should be thrown: "+ e.getMessage());
        }
    }

    @Test
    public void saveFriendshipExistent(){
        Friendship f = createValidFriendship();
        f.setUser_dst(playerService.getPlayerById(12).get());
        assertThrows(FriendshipExistsException.class, () -> fs.saveFriendship(f,"POST"));
    }

    private Friendship createValidFriendship(){
        Friendship friendship = new Friendship();
        friendship.setId(31);
        friendship.setUser_source(playerService.getPlayerById(12).get());
        friendship.setUser_dst(playerService.getPlayerById(11).get());
        friendship.setStatus(FriendshipStatus.WAITING);
        return friendship;
    }
    
}
