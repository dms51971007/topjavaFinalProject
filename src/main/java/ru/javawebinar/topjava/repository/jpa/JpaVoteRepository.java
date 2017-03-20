package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.Vote;
import ru.javawebinar.topjava.to.VoteResultTo;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by r2 on 10.03.2017.
 */
@Repository
@Transactional(readOnly = true)
public class JpaVoteRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Vote save(Vote vote, int userId) {

        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        User user = em.find(User.class, userId);

        if (user == null) return null;
        else {
            vote.setUser(user);
        }

        if (vote.isNew()) {
            em.persist(vote);
            return vote;
        } else {
            return em.merge(vote);
        }
    }

    public Vote get(int id, int userId) {
        Vote vote = em.find(Vote.class, id);
        return vote != null && vote.getUser().getId() == userId ? vote : null;
    }

    public List<Vote> getAll() {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class).getResultList();
    }

    public List<VoteResultTo> getVoteResult(LocalDate data) {
        return em.createNamedQuery(Vote.GET_RESULT, VoteResultTo.class).setParameter("data", data).getResultList();
    }

    public boolean delete(int id) {
        return em.createNamedQuery(Vote.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    public Vote getByDateByUser(int userId, LocalDate data) {
        try {
            return em.createNamedQuery(Vote.GET_USER_DATE, Vote.class).setParameter("user_id", userId).setParameter("data", data).getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

}
