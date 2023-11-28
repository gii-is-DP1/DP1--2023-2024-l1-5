package org.springframework.samples.petclinic.friendship;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.exceptions.FriendshipExistsException;
import org.springframework.samples.petclinic.player.PlayerRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = FriendshipService.class))
public class FrienshipServiceTest {
    @Autowired
    FriendshipService fs;
    @Autowired
    PlayerRepository pr;

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
        f.setUser_dst(pr.findById(18).get());
        assertThrows(FriendshipExistsException.class, () -> fs.saveFriendship(f,"POST"));
    }

    private Friendship createValidFriendship(){
        Friendship friendship = new Friendship();
        friendship.setId(20);
        friendship.setUser_source(pr.findById(19).get());
        friendship.setUser_dst(pr.findById(14).get());
        return friendship;
    }
    
}
