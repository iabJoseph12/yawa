package com.bacante.yawa.service.impl;

import com.bacante.yawa.domain.Session;
import com.bacante.yawa.repository.SessionRepository;
import com.bacante.yawa.service.SessionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Session}.
 */
@Service
public class SessionServiceImpl implements SessionService {

    private final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session save(Session session) {
        log.debug("Request to save Session : {}", session);
        return sessionRepository.save(session);
    }

    @Override
    public Session update(Session session) {
        log.debug("Request to update Session : {}", session);
        return sessionRepository.save(session);
    }

    @Override
    public Optional<Session> partialUpdate(Session session) {
        log.debug("Request to partially update Session : {}", session);

        return sessionRepository
            .findById(session.getId())
            .map(existingSession -> {
                if (session.getSessionNotes() != null) {
                    existingSession.setSessionNotes(session.getSessionNotes());
                }

                return existingSession;
            })
            .map(sessionRepository::save);
    }

    @Override
    public Page<Session> findAll(Pageable pageable) {
        log.debug("Request to get all Sessions");
        return sessionRepository.findAll(pageable);
    }

    @Override
    public Optional<Session> findOne(String id) {
        log.debug("Request to get Session : {}", id);
        return sessionRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Session : {}", id);
        sessionRepository.deleteById(id);
    }
}
