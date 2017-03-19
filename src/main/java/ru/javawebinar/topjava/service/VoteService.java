package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.model.Vote;
import ru.javawebinar.topjava.repository.jpa.JpaRestaurantRepository;
import ru.javawebinar.topjava.repository.jpa.JpaVoteRepository;
import ru.javawebinar.topjava.to.VoteResultTo;
import ru.javawebinar.topjava.to.VoteTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by r2 on 10.03.2017.
 */
@Service
public class VoteService {
    private final LocalTime LUNCH_TIME = LocalTime.parse("11:00");

    @Autowired
    private JpaVoteRepository voteRepository;
    @Autowired
    private JpaRestaurantRepository restaurantRepository;

    @Transactional
    public Vote save(VoteTo voteTo, int userId) {
        Assert.notNull(voteTo, "Vote must not be null");

        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.get(voteTo.getRestaurantId()), voteTo.getRestaurantId());

        Vote vote = voteRepository.getByDateByUser(userId, voteTo.getData());

        if (vote == null) vote = new Vote();
        else if (LocalTime.now().isAfter(LUNCH_TIME)) throw new IllegalStateException("Lunch finished");

        vote.setData(voteTo.getData());
        vote.setRestaurant(restaurant);

        return voteRepository.save(vote, userId);
    }

    public Vote get(int id, Integer userId) throws NotFoundException {
        return checkNotFoundWithId(voteRepository.get(id, userId), id);
    }

    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    public List<VoteResultTo> getResult(LocalDate data) {
        return voteRepository.getVoteResult(data);
    }

    public void delete(int id) throws NotFoundException {
        if (LocalTime.now().isAfter(LUNCH_TIME)) throw new IllegalStateException("Lunch finished");
        checkNotFoundWithId(voteRepository.delete(id), id);
    }

}
