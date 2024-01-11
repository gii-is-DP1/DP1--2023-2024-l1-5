package org.springframework.samples.petclinic.friendship;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest()
public class FriendshipRepositoryTest {

    private static final int TEST_PLAYER_ID = 1;

    @Autowired
    FriendshipRepository fr;

    @Test
    public void findFriendshipWaitingRequestsByNullPlayerIdTest() {
        List<Friendship> actualResult = fr.findFriendshipRequests(null);
        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void findFriendshipWaitingRequestsByPlayerIdTest() {
        List<Friendship> actualResult = fr.findFriendshipRequests(TEST_PLAYER_ID);
        assertTrue(actualResult.size()==2);
    }
    
    @Test
    public void findFriendshipAllRequestByPlayerId() {
        List<Friendship> actualResult = fr.findFriendshipRequestByPlayerId(TEST_PLAYER_ID);
        assertTrue(actualResult.size()==2);
    }

    @Test
    public void findAcceptedFriendshipsByPlayerIdTest() {
        List<Friendship> actualResult = fr.findAcceptedFriendshipsByPlayerId(TEST_PLAYER_ID);
        assertTrue(actualResult.size()==2);
    }

    @Test
    public void findAcceptedFriendshipsByNullPlayerIdTest() {
        List<Friendship> actualResult = fr.findFriendshipRequests(null);
        assertTrue(actualResult.isEmpty());
    }
}