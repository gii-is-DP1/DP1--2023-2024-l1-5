package org.springframework.samples.petclinic.invitation;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Integer>{

    List<Invitation> findAll() throws DataAccessException;

    @Query("SELECT i FROM Invitation i WHERE (i.source_user = :playerUsername ) AND i.invitation_state = 'PENDING'")
    List<Invitation> findPendigInvitation(String playerUsername) throws DataAccessException;

    @Query("SELECT i FROM Invitation i WHERE (i.destination_user = :playerUsername ) AND i.invitation_state = 'PENDING'")
    List<Invitation> findAlreadyPendigInvitation(String playerUsername) throws DataAccessException;

    @Query("SELECT i FROM Invitation i WHERE (i.destination_user = :playerUsername ) AND i.invitation_state = 'ACCEPTED'")
    List<Invitation> findAcceptedInvitation(String playerUsername) throws DataAccessException;


} 
