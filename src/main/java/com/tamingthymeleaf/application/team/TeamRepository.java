package com.tamingthymeleaf.application.team;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TeamRepository extends CrudRepository<Team, TeamId>, TeamRepositoryCustom {
    @Query("SELECT new com.tamingthymeleaf.application.team.TeamSummary(t.id, t.name, t.coach.id, t.coach.userName) FROM Team t")
    Page<TeamSummary> findAllSummary(Pageable pageable);
}